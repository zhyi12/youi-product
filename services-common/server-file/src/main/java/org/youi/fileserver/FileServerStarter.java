/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.fileserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.youi.fileserver.filestore.entity.FileStore;
import org.youi.fileserver.filestore.service.FileStoreManager;
import org.youi.framework.context.ModulesRunnerBuilder;
import org.youi.framework.core.Constants;
import org.youi.framework.core.dataobj.Message;
import org.youi.framework.services.config.ServiceConfig;
import org.youi.framework.services.data.ResContext;
import org.youi.framework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@SpringBootApplication
@RestController
@EnableIntegration
@EnableEurekaClient
@EnableOAuth2Sso
public class FileServerStarter {

    private  static final Log logger = LogFactory.getLog(FileServerStarter.class);

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private FileStoreManager fileStoreManager;

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    @RequestMapping(value = "/upload.json",method= RequestMethod.POST)
    public @ResponseBody
    String upload(@RequestParam("file")MultipartFile multipartFile) {
        //todo 通过上传文件的后缀校验文件类型
        boolean isVerifyFile = FileTypeUtils.verifyFileType(multipartFile);

        if (!isVerifyFile) {
            return "{\"message\":{\"info\":\"请上传标准格式文件！\",\"code\":\"999999\"}}";
        }
        FileStore fileStore = this.storeUploadFile(multipartFile,"");
        //文件的文件头校验
        File file = fileStoreManager.getFile("file"+ File.separator +fileStore.getFilePath());
        boolean isVerifyFile2  = FileTypeUtils.verifyFileTypeName(FileTypeUtils.getFileType(file));
        if (!isVerifyFile2) {
            return "{\"message\":{\"info\":\"请上传标准格式文件！\",\"code\":\"999999\"}}";
        }
        String filePath = fileStore!=null?fileStore.getFilePath():"";
        return "{\"message\":{\"info\":\""+filePath+"\",\"code\":\"000000\"}}";
    }

    @RequestMapping(value = "/upload/{fileType}.json",method= RequestMethod.POST)
    public @ResponseBody
    FileStore uploadByType(
            @PathVariable("fileType") String fileType,
            @RequestParam("uploadName")MultipartFile multipartFile) {
        return this.storeUploadFile(multipartFile,fileType);
    }

    @RequestMapping(value = "/images/{width}/{height}/{fileName}.{postfix}")
    public ResContext showImage(HttpServletRequest request,
                                   HttpServletResponse response,
                                    @PathVariable("width") String width,
                                    @PathVariable("height") String height,
                                    @PathVariable("fileName") String fileName,
                                    @PathVariable("postfix") String postfix) {
        String filePath = fileName+"."+postfix;
        File file = fileStoreManager.getFile(filePath);
        String errorMessage = "";
        if (file!=null&&file.exists()) {
            //
            response.setContentType("image/png");
            try {
                ImgUtils.compress(file, response.getOutputStream(),
                        Integer.parseInt(width), Integer.parseInt(height));
                return null;
            } catch (NumberFormatException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            }
        }

        if(StringUtils.isEmpty(errorMessage)){
            errorMessage = "文件" + filePath+"下载错误";
        }

        ResContext resContext = new ResContext();
        Message message = new Message(Constants.ERROR_DEFAULT_CODE,errorMessage);
        resContext.setMessage(message);
        return resContext;
    }

    /**
     * 文件下载
     * @return
     */
    @RequestMapping(value = "/download/{subfolder}/{fileName}.{postfix}")
    public ResContext downloadFile(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @PathVariable("subfolder") String subfolder,
                                   @PathVariable("fileName") String fileName,
                                   @PathVariable("postfix") String postfix) {
        String relPath = subfolder + File.separator + fileName+"."+postfix;
        System.out.println(relPath);
        File file = fileStoreManager.getFile(relPath);
//        DispatcherServlet
        String errorMessage = "";

        if(file!=null&&file.exists()){
            try(InputStream in = new FileInputStream(file)){

                response.setCharacterEncoding("utf-8");
                String exportName = request.getParameter("exportName");
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                String filenameDisplay = StringUtils.isEmpty(exportName)?fileName:exportName;
                try {
                    filenameDisplay = URLEncoder.encode(filenameDisplay, "UTF-8")+"."+postfix;
                } catch (UnsupportedEncodingException e) {
                    // e.printStackTrace();
                }
                //attachment;filename*=utf-8'zh_cn'
                response.addHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'"
                        + filenameDisplay);

                FileCopyUtils.copy(in, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (FileNotFoundException e) {
                errorMessage = "未找到文件" + relPath;
            } catch (IOException e) {
                errorMessage = "IO异常";
            }
            //没有异常信息,执行文件下载
            if(StringUtils.isEmpty(errorMessage)){
                return null;
            }
        }else{
            errorMessage = "未找到文件!";
        }

        ResContext resContext = new ResContext();
        Message message = new Message(Constants.ERROR_DEFAULT_CODE,errorMessage);
        resContext.setMessage(message);

        return resContext;
    }

    /**
     *
     * @param multipartFile
     * @param fileType
     * @return
     */
    private FileStore storeUploadFile(MultipartFile multipartFile,String fileType){
        String fileName = multipartFile.getOriginalFilename();
        String userId = "";

        try(InputStream in = multipartFile.getInputStream()){
            return fileStoreManager.storeFile(fileName,in,userId,fileType);
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        new ModulesRunnerBuilder(ServiceConfig.class,
                FileServerStarter.class).run(args);
    }

}

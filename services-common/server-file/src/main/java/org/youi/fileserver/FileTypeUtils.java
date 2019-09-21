package org.youi.fileserver;

import org.springframework.web.multipart.MultipartFile;
import org.youi.framework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileTypeUtils {

    private static final String[] supportedSuffix = {"doc","docx","xls","xlsx","csv","ppt","pptx","txt","pdf","jpg", "png", "gif", "bmp","jpg", "png", "jpeg", "gif", "bmp","xml", "rar", "zip"};


    public static boolean verifyFileType(MultipartFile multipartFile){
        boolean isVerify = true;
        String fileName = multipartFile.getOriginalFilename().replaceAll("\\s*", "");

        if (StringUtils.isNotEmpty(fileName)) {
            String fileSuffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!verifyFileTypeName(fileSuffixName)) {
                isVerify = false;
            }

        } else {
            isVerify = false;
        }
        return isVerify;
    }

    public static boolean verifyFileTypeName(String fileType){
        List<String> fileTypeList = Arrays.asList(supportedSuffix);
        return fileTypeList.contains(fileType);
    }

    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
    static {
        // images
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("FFD8FFE0", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
        //
        mFileTypes.put("41433130", "dwg"); // CAD
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("7B5C727466", "rtf"); // 日记本
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("3C3F786D", "xml");
        mFileTypes.put("3C646570", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("D0CF11E0", "xls");//excel2003版本文件
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("25504446", "pdf");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("504B0304", "xlsx");//excel2007以上版本文件
        mFileTypes.put("504B0304", "zip");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("1F8B08", "gz");
    }

    /**
     * @author guoxk
     *
     * 方法描述：根据文件路径获取文件头信息
     * @param file 文件
     * @return 文件头信息
     */
    public static String getFileType(File file) {
        return mFileTypes.get(getFileHeader(file));
    }

    /**
     * @author guoxk
     *
     * 方法描述：根据文件路径获取文件头信息
     * @param file 文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(File file) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(file);
            byte[] b = new byte[4];
            /*
             * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
             * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
             */
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

    /**
     * @author guoxk
     *
     * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
     * @param src 要读取文件头信息的文件的byte数组
     * @return  文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

}

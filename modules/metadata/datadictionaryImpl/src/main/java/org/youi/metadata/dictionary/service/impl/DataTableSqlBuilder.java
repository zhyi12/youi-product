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
package org.youi.metadata.dictionary.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.youi.metadata.dictionary.entity.MetaDataItem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class DataTableSqlBuilder {

    private  static final Log logger = LogFactory.getLog(DataTableSqlBuilder.class);

    @Autowired
    private MetaDataItemFormatConverter metaDataItemFormatConverter;

    @Value("classpath:META-INF/config/oracle.sql.xml")
    private Resource oracleResource;

    /**
     *
     * @param tableColumnMap
     * @return
     */
    public String buildCreateSql(Map<String,List<MetaDataItem>> tableColumnMap){
        if(CollectionUtils.isEmpty(tableColumnMap)){
            return "";
        }
        Document doc = buildMapping(tableColumnMap);
        return writeCreateSql(doc);
    }

    /**
     *
     * @param doc
     * @return
     */
    private String writeCreateSql(Document doc ){
        ServiceRegistry registry;
        try {
            File tmpFile = new File(FileUtils.getTempDirectory(),UUID.randomUUID()+".xml");

            try(FileOutputStream outputStream = new FileOutputStream(tmpFile)){
                FileCopyUtils.copy(oracleResource.getInputStream(),outputStream);
            }

            registry = new StandardServiceRegistryBuilder()
                    .configure(tmpFile)
                    .build();
            logger.info("hibernate-configuration load complete");
            FileUtils.deleteQuietly(tmpFile);//删除临时文件
        } catch (IOException e) {
            logger.error("配置文件加载失败:"+e.getMessage());
            e.printStackTrace();
            return "";
        }

        File tempSqlFile = new File(FileUtils.getTempDirectory(),UUID.randomUUID().toString()+".sql");
        ByteArrayInputStream xmlInput = new ByteArrayInputStream(doc.asXML().getBytes());

        Metadata metadata = new MetadataSources(registry).addInputStream(xmlInput).buildMetadata();

        SchemaExport export = new SchemaExport();
        export.setFormat(true);
        export.setDelimiter(";");
        export.setOutputFile(tempSqlFile.getAbsolutePath());
        export.create(EnumSet.of(TargetType.SCRIPT),metadata);

        try {
           return new String(FileCopyUtils.copyToByteArray(tempSqlFile));
        } catch (IOException e) {
            //e.printStackTrace();
        }finally {
            FileUtils.deleteQuietly(tempSqlFile);//删除临时文件
        }
        return "";
    }

    /**
     *
     * @param tableColumnMap
     * @return
     */
    public Document buildMapping(Map<String,List<MetaDataItem>> tableColumnMap){
        Document document = DocumentFactory.getInstance().createDocument();

        Element mappingElement = document.addElement("hibernate-mapping");

        if(!CollectionUtils.isEmpty(tableColumnMap)){
            tableColumnMap.forEach((tableName,metaDataItems)->{

                if(CollectionUtils.isEmpty(metaDataItems)){
                    return;
                }

                Element clazzElement = mappingElement.addElement("class");
                clazzElement.addAttribute("name",tableName.toUpperCase());
                //临时使用第一项为主键字段
                MetaDataItem first = metaDataItems.remove(0);
                //ID
                addIdElement(clazzElement,first);
                //属性字段
                metaDataItems.forEach(metaDataItem -> addPropertyElement(clazzElement,metaDataItem));
            });
        }

        return document;
    }

    /**
     *
     * @param clazzElement
     * @param metaDataItem
     */
    private void addPropertyElement(Element clazzElement, MetaDataItem metaDataItem) {
        Element idElement = clazzElement.addElement("property");
        idElement.addAttribute("name",metaDataItem.getName().toUpperCase());
        idElement.addAttribute("type",metaDataItemFormatConverter.convert(metaDataItem.getDataFormat()));

        //识别长度 //N..22,1//C..50 //YYYYMMDDhhmmss
        int length = 0;

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(metaDataItem.getDataFormat());
        if(matcher.find()){
            length = Integer.parseInt(matcher.group());
        }
        if(length>0){
            idElement.addAttribute("length",Integer.toString(length));
        }
    }

    /**
     *
     * @param clazzElement
     * @param idMetaDataItem
     */
    private void addIdElement(Element clazzElement,MetaDataItem idMetaDataItem){
        Element idElement = clazzElement.addElement("id");
        idElement.addAttribute("name",idMetaDataItem.getName().toUpperCase());
        idElement.addAttribute("type",metaDataItemFormatConverter.convert(idMetaDataItem.getDataFormat()));
    }

}

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
package org.youi.tools.indexing.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Item;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class IndexingBuilder {

    public static final Analyzer DEFAULT_ANALYZER = new SmartChineseAnalyzer();

    private File indexingFileDir;//索引存放的文件夹

    private Analyzer analyzer;//分词器

    private IndexWriter indexWriter;//索引写入者

    private Directory indexingDir;//

    public IndexingBuilder(){
        this(DEFAULT_ANALYZER);
    }

    /**
     * 使用自定义的分词器构建索引
     * @param analyzer
     */
    public IndexingBuilder(Analyzer analyzer){
        this.analyzer = analyzer;
        this.indexingFileDir = new File(FileUtils.getTempDirectory(),".indexing"+File.separator+ UUID.randomUUID().toString());
    }

    /**
     * 打开
     */
    public void open() {
        try {
            FileUtils.forceMkdir(indexingFileDir);
            indexingDir  = FSDirectory.open(FileSystems.getDefault().getPath(indexingFileDir.getAbsolutePath()));
            //构建索引配置对象
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);//追加模式
            indexWriter = new IndexWriter(indexingDir, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加索引
     * @param domain
     * @param function
     */
    public void appendDomain(Domain domain, Function<Domain,List<TextField>> function) {
        List<TextField> fields = function.apply(domain);
        if(CollectionUtils.isNotEmpty(fields)){
            Document document = new Document();
            for(TextField field:fields){
                document.add(field);
            }
            try {
                indexWriter.addDocument(document);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
    /**
     * 关闭
     */
    public void close(){
        IOUtils.closeQuietly(indexWriter);
        IOUtils.closeQuietly(indexingDir);
    }

    public File getIndexingFileDir(){
        return indexingFileDir;
    }

    /**
     * 消费索引文件，并输出压缩文件流
     * @param output
     */
    public void consumeAndZipIndexingFile(OutputStream output) {
        try(OutputStream bos = new BufferedOutputStream(output);
            ArchiveOutputStream zipOutput = new ZipArchiveOutputStream(bos)) {
            Path dirPath  = Paths.get(indexingFileDir.getAbsolutePath());
            Files.walkFileTree(dirPath,new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    ArchiveEntry entry = new ZipArchiveEntry(dir.toFile(), dirPath.relativize(dir).toString());
                    zipOutput.putArchiveEntry(entry);
                    zipOutput.closeArchiveEntry();
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    ArchiveEntry entry = new ZipArchiveEntry(
                            file.toFile(), dirPath.relativize(file).toString());
                    zipOutput.putArchiveEntry(entry);
                    IOUtils.copy(new FileInputStream(file.toFile()), zipOutput);
                    zipOutput.closeArchiveEntry();
                    return super.visitFile(file, attrs);
                }
            });
            FileUtils.deleteQuietly(indexingFileDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        IndexingBuilder indexingBuilder = new IndexingBuilder();
        indexingBuilder.open();
        for(int i=0;i<100;i++){
            indexingBuilder.appendDomain(new Item("id_"+i,"文本"+i),(item)->{
                List<TextField> fields = new ArrayList<>();
                fields.add(new TextField("id",((Item)item).getId(), Field.Store.YES));
                fields.add(new TextField("text",((Item)item).getText(), Field.Store.YES));
                fields.add(new TextField("fullText",((Item)item).getText(), Field.Store.YES));
                fields.add(new TextField("group","group1", Field.Store.YES));
                return fields;
            });
        }
        indexingBuilder.close();

        try(FileOutputStream outputStream = new FileOutputStream("/Users/zhouyi/test.zip")){
            indexingBuilder.consumeAndZipIndexingFile(outputStream);
        }catch (Exception e){

        }
    }
}

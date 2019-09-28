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

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.youi.tools.indexing.Constants;
import org.youi.tools.indexing.entity.IndexResult;
import org.youi.tools.indexing.util.IndexingUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class IndexingZipSearcher {

    private Analyzer analyzer;

    private IndexSearcher indexSearcher;

    private File zipFile;

    private Directory directory;

    private IndexReader indexReader;

    public IndexingZipSearcher(File zipFile){
        this(IndexingBuilder.DEFAULT_ANALYZER,zipFile);
    }

    public IndexingZipSearcher(Analyzer analyzer, File zipFile){
        this.zipFile = zipFile;
        this.analyzer = analyzer;
    }

    public void open(){
        File indexingFileDir = new File(FileUtils.getTempDirectory(), ".indexing" + File.separator + UUID.randomUUID().toString());
        //解压文件
        try(InputStream inputStream = new FileInputStream(zipFile)){
            //解压文件
            unzipIndexingFile(inputStream,indexingFileDir);

            directory = FSDirectory.open(Paths.get(indexingFileDir.getAbsolutePath()));
            indexReader = DirectoryReader.open(directory);
            this.indexSearcher = new IndexSearcher(indexReader);//

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        IOUtils.closeQuietly(indexReader);
        IOUtils.closeQuietly(directory);
    }

    /**
     * 从压缩文件查询
     * @param term
     * @param group
     * @return
     */
    public List<IndexResult> itemSearch(String term, String group) {
        List<IndexResult> searchedResults = new ArrayList<>();

        if(indexReader==null){
            return searchedResults;
        }
        try {
            //打开索引文件
            //构建fullText查询条件和group分组过滤条件
            BooleanQuery.Builder builder = new BooleanQuery.Builder();

            QueryParser textParser = new QueryParser(Constants.INDEXING_FIELD_FULL_TEXT, this.analyzer);
            Query textQuery = textParser.parse(IndexingUtils.fixSearchTerm(term));
            builder.add(textQuery, BooleanClause.Occur.MUST);

            QueryParser groupParser = new QueryParser(Constants.INDEXING_FIELD_GROUP, this.analyzer);
            Query groupQuery = groupParser.parse(group);
            builder.add(groupQuery, BooleanClause.Occur.MUST);

            TopDocs topDocs = indexSearcher.search(builder.build(), 5);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                IndexResult indexResult = IndexingUtils.convert2IndexResult(indexSearcher, scoreDoc);
                if(group.equals(indexResult.getGroup())){
                    searchedResults.add(indexResult);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchedResults;
    }

    /**
     *
     * @param zipInput
     * @param indexingFileDir
     */
    private void unzipIndexingFile(InputStream zipInput,File indexingFileDir){
        if (!indexingFileDir.exists()) {
            indexingFileDir.mkdirs();
        }

        try (InputStream bufferedInputStream = new BufferedInputStream(zipInput);
             ArchiveInputStream zipArcInput = new ZipArchiveInputStream(bufferedInputStream)
        ) {
            ArchiveEntry entry;
            while (Objects.nonNull(entry = zipArcInput.getNextEntry())) {
                if (!zipArcInput.canReadEntryData(entry)) {
                    continue;
                }
                File file = new File(indexingFileDir, entry.getName());
                if (entry.isDirectory()) {
                    if (!file.isDirectory() && !file.mkdirs()) {
                        file.mkdirs();
                    }
                } else {
                    try (OutputStream o = Files.newOutputStream(file.toPath())) {
                        IOUtils.copy(zipArcInput, o);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        IndexingZipSearcher indexingZipSearcher = new IndexingZipSearcher(new File("/Users/zhouyi/doc/test.zip"));

        indexingZipSearcher.open();

        System.out.println(indexingZipSearcher.itemSearch("软件产品行业合计","S2018SoftCategory1"));
        System.out.println(indexingZipSearcher.itemSearch("文本6","group2"));

        indexingZipSearcher.close();

    }
}

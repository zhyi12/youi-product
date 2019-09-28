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
package org.youi.tools.indexing.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.util.StringUtils;
import org.youi.tools.indexing.Constants;
import org.youi.tools.indexing.entity.IndexResult;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class IndexingUtils {

    private final static Log logger = LogFactory.getLog(IndexingUtils.class);
    /**
     * 私有构造函数
     */
    private IndexingUtils(){
        //ignore
    }

    /**
     * 构建用于检索的item集合
     *
     * @param id item id
     * @param text item文本，用于检索的文本
     * @return 用于检索的item集合
     */
    public static List<Item> buildIndexingItems(String id, String text){
        List<Item> items = new ArrayList<>();
        items.add(new Item(id,text));
        return items;
    }

    /**
     *
     * @param analyzer
     */
    public static void buildIndexing(String indexingBaseDir,
                                     String indexName,
                                     Analyzer analyzer,
                                     List<? extends Domain> domains,
                                     Function<Domain,List<TextField>> function,
                                     boolean rebuild){
        Directory indexingDir = null;
        IndexWriter writer = null;

        try {
            //索引存储的文件夹
            File indexingFileDir = new File(indexingBaseDir + File.separator + indexName);
            if (!indexingFileDir.exists()) {
                indexingFileDir.mkdirs();
            }
            //构建索引配置对象
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

            if (!rebuild) {
                //设置追加模式
                indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            }
            //打开索引
            indexingDir = FSDirectory.open(FileSystems.getDefault().getPath(indexingFileDir.getAbsolutePath()));
            writer = new IndexWriter(indexingDir, indexWriterConfig);

            if (rebuild) {
                //删除文件重构索引
                writer.deleteAll();
            }

            for(Domain domain:domains){
                List<TextField> fields = function.apply(domain);
                if(fields!=null){
                    Document document = new Document();
                    for(TextField field:fields){
                        document.add(field);
                    }
                    writer.addDocument(document);
                }
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(indexingDir);
        }
    }

    /**
     *
     * @param term
     * @return
     */
    public static List<IndexResult> searchIndex(String indexingBaseDir,
                                           String indexName,
                                           Analyzer analyzer,
                                           String term){

        List<IndexResult> searchedResults = new ArrayList<IndexResult>();

        Directory indexingDir = null;
        IndexReader indexReader = null;

        //打开索引
        try {
            indexingDir = FSDirectory.open(FileSystems.getDefault().getPath(indexingBaseDir + File.separator + indexName));
            //
            indexReader = DirectoryReader.open(indexingDir);
            IndexSearcher searcher = new IndexSearcher(indexReader);

            //构建查询分析器
            QueryParser parser = new QueryParser(Constants.INDEXING_FIELD_FULL_TEXT, analyzer);
            Query termQuery = parser.parse(fixSearchTerm(term));//TODO term特殊字符处理
            //查询最相似的5条记录
            TopDocs topDocs = searcher.search(termQuery,10);

            for(ScoreDoc scoreDoc:topDocs.scoreDocs){
                searchedResults.add(convert2IndexResult(searcher,scoreDoc));
            }

        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }finally {
            IOUtils.closeQuietly(indexingDir);
            IOUtils.closeQuietly(indexReader);
        }

        return searchedResults;
    }

    /**
     * scoreDoc 对象转 IndexResult对象
     *
     * @param searcher
     * @param scoreDoc
     * @return
     * @throws IOException
     */
    public static IndexResult convert2IndexResult(IndexSearcher searcher,
                                                  ScoreDoc scoreDoc) throws IOException {
        IndexResult indexResult = new IndexResult();
        Document document = searcher.doc(scoreDoc.doc);

        indexResult.setId(document.getField(Constants.INDEXING_FIELD_ID).stringValue());
        indexResult.setText(document.getField(Constants.INDEXING_FIELD_TEXT).stringValue());
        indexResult.setFullText(document.getField(Constants.INDEXING_FIELD_FULL_TEXT).stringValue());
        if(document.getField(Constants.INDEXING_FIELD_GROUP)!=null){
            indexResult.setGroup(document.getField(Constants.INDEXING_FIELD_GROUP).stringValue());
        }
        IndexableField indexName = null;
        //  把索引名放入结果中，查询多种类别对象时可能会用到
        if((indexName = document.getField("indexName"))!=null)
            indexResult.setIndexName(document.getField("indexName").stringValue());
        indexResult.setScore(scoreDoc.score);
        return indexResult;
    }
    
    
    /**
    *
    * @param id
    * @param text
    * @param fullText
    * @return
    */
   public static List<TextField> buildFields(String id,String text,String fullText){
       List<TextField> fields = new ArrayList<TextField>();
       fields.add(new TextField(Constants.INDEXING_FIELD_ID,id, Field.Store.YES));
       fields.add(new TextField(Constants.INDEXING_FIELD_TEXT,text, Field.Store.YES));
       fields.add(new TextField(Constants.INDEXING_FIELD_FULL_TEXT, fullText, Field.Store.YES));
       return fields;
   }

   public static String fixSearchTerm(String term){
       //替换特殊字符
       return term.replaceAll("\\*|\\/|\\|","");
   }

}

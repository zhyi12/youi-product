/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.datacenter.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.RowMapper;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.jpa.hibernate.BaseDaoHibernate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@Configuration
@EnableBatchProcessing //spring batch 配置
@EnableJpaRepositories(basePackages = "org.youi.datacenter.batch", repositoryBaseClass= BaseDaoHibernate.class)
public class ModuleConfig {

    @Resource
    private DataSource dataSource;

    @Resource
    private JobBuilderFactory jobBuilderFactory;    //用于构建JOB

    @Resource
    private StepBuilderFactory stepBuilderFactory;  //用于构建Step

    @Value("classpath:output.txt")
    private org.springframework.core.io.Resource resource;

    @Bean
    public JdbcPagingItemReader itemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("active", "1");

        return new JdbcPagingItemReaderBuilder<Record>()
                .name("userReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider().getObject())
                .parameterValues(parameterValues)
//                .rowMapper(recordMapper())
                .pageSize(1000)
                .build();
    }

//    @Bean
//    public RowMapper<Record> recordMapper() {
//        RowMapper<Record> recordRowMapper = new RowMapper<Record>() {
//            @Override
//            public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Record record = new Record();
//                for(int i=0;i<rowNum;i++){
//                    String columnName = rs.getMetaData().getColumnName(rowNum);
//                    record.put(columnName,rs.getString(columnName));
//                }
//                return record;
//            }
//        };
//        return recordRowMapper;
//    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider() {
        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
        provider.setDataSource(dataSource);
        provider.setSelectClause("select USER_ID, AGENCY_ID, USER_CAPTION");
        provider.setFromClause("from YOUI_USER");
        provider.setWhereClause("where USER_ACTIVE=:active");
        provider.setSortKey("USER_ID");//分页查询必须排序
        return provider;
    }

    @Bean
    public Job footballJob() throws Exception {
        return this.jobBuilderFactory.get("footballJob")
                .start(playerLoad())
                .build();
    }

    @Bean
    public Step playerLoad() throws Exception {
        return this.stepBuilderFactory.get("playerLoad")
                .<String, String>chunk(1000)
                .reader(itemReader())
                .writer(flatFileItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemWriter flatFileItemWriter(){
        FlatFileItemWriter flatFileItemWriter = new FlatFileItemWriter();
        flatFileItemWriter.setLineAggregator(lineAggregator());
        flatFileItemWriter.setResource(resource);
        return flatFileItemWriter;
    }

    @Bean
    public LineAggregator lineAggregator() {
        return  new LineAggregator() {
            @Override
            public String aggregate(Object o) {
                //Record 对象
                System.out.println(o.toString());
                return o.toString();
            }
        };
    }
}

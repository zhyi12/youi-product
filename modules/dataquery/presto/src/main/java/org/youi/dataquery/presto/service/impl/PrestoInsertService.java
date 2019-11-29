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
package org.youi.dataquery.presto.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.dataquery.engine.service.IInsertService;
import org.youi.dataquery.presto.dao.PrestoSqlDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("prestoTableService")
public class PrestoInsertService implements IInsertService {

    protected final Log logger = LogFactory.getLog(getClass());

    private final static Pattern pattern = Pattern.compile("insert\\s+into.+select.+from.+");

    @Autowired(required = false)
    private PrestoSqlDao prestoSqlDao;

    /**
     *
     * @param insertIntoSql
     */
    public void doInsertInto(String insertIntoSql){
        //TODO 检查是否为insert into的sql语句
        if(pattern.matcher(insertIntoSql.toLowerCase().replaceAll("\n","")).find()){
            prestoSqlDao.execSql(insertIntoSql);
        }else{
            logger.warn("不正确的insert into select 语句:"+insertIntoSql);
        }
    }

    public static void main(String[] args){

    }
}

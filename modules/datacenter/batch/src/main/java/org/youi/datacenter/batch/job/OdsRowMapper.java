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
package org.youi.datacenter.batch.job;

import org.springframework.jdbc.core.RowMapper;
import org.youi.framework.core.dataobj.Record;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class OdsRowMapper implements RowMapper<Record> {

    /**
     * 转换
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
        System.out.println(rowNum);
        Record record = new Record();
        for(int i=0;i<rs.getMetaData().getColumnCount();i++){
            String columnName = rs.getMetaData().getColumnName(i+1);
            record.put(columnName,rs.getString(columnName));
        }
        return record;
    }
}

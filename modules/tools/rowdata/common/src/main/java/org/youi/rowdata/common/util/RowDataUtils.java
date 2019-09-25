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
package org.youi.rowdata.common.util;

import org.youi.framework.util.BeanUtils;
import org.youi.framework.util.PropertyUtils;
import org.youi.framework.util.StringUtils;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class RowDataUtils {

    /**
     * 私有构造函数
     */
    private RowDataUtils(){
        //ignore
    }

    public static <T> T mapObject(String[] properties, String[] rowData, Class<T> metaDataItemClass) {
        T obj = BeanUtils.instantiateClass(metaDataItemClass);
        int index = 0;
        for(String property:properties){
            String value = fixValue(rowData,index++);
            if(StringUtils.isNotEmpty(property)){
                PropertyUtils.setPropertyValue(obj,property,value);
            }
        }
        return obj;
    }

    public static String fixValue(String[] rowData, int index) {
        if(rowData.length>index && rowData[index]!=null){
            return rowData[index];
        }
        return "";
    }
}

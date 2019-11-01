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
package org.youi.chart.engine.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.NumberUtils;

import java.io.IOException;

/**
 *  输出 string,number,function 三种类型的json属性
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class MultiTypePropertySerialize extends JsonSerializer {

    private final static String FUNC_START = "function(";
    private final static String PERCENT_END = "%";//

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value!=null){
            String strValue = value.toString().trim();
            if(strValue.endsWith(PERCENT_END)){//字符
                gen.writeString(strValue);
            }else if(strValue.startsWith(FUNC_START)){//函数
                gen.writeRaw(":"+strValue);
            }else{//数字
                try {
                    NumberUtils.parseNumber(strValue,Number.class);
                    gen.writeNumber(strValue);
                } catch (IllegalArgumentException e) {
                    gen.writeString(strValue);
                }
            }
        }
    }
}

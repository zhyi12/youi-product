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
package org.youi.dataquery.engine.model;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.orm.Condition;

/**
 * 过滤条件对象
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class QueryCondition implements Condition,Domain{

    private static final long serialVersionUID = 1669737049925714254L;

    private String property;//属性

    private String operator;//操作符

    private String group;//条件分组

    private Object value;//过滤值

    public void setProperty(String property) {
        this.property = property;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryCondition that = (QueryCondition) o;

        if (property != null ? !property.equals(that.property) : that.property != null) return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = property != null ? property.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}

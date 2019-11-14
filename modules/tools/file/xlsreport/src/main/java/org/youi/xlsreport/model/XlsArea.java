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
package org.youi.xlsreport.model;

import org.youi.framework.core.dataobj.cube.Area;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class XlsArea extends Area{

    private String mergeId;

    private String mergeText;//合并后的显示文本

    public String getMergeId() {
        return mergeId;
    }

    public void setMergeId(String mergeId) {
        this.mergeId = mergeId;
    }

    public String getMergeText() {
        return mergeText;
    }

    public void setMergeText(String mergeText) {
        this.mergeText = mergeText;
    }
}

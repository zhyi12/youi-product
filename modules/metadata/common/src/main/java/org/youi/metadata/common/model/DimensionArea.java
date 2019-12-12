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
package org.youi.metadata.common.model;

import org.youi.framework.core.dataobj.cube.Dimension;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class DimensionArea extends Dimension {

    private static final long serialVersionUID = -867079589999187381L;

    private int startCol;
    private int endCol;
    private int startRow;
    private int endRow;
    private int rows;
    private int cols;

    public int getStartCol() {
        return startCol;
    }

    public DimensionArea setStartCol(int startCol) {
        this.startCol = startCol;
        return this;
    }

    public int getEndCol() {
        return endCol;
    }

    public DimensionArea setEndCol(int endCol) {
        this.endCol = endCol;
        return this;
    }

    public int getStartRow() {
        return startRow;
    }

    public DimensionArea setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public int getEndRow() {
        return endRow;
    }

    public DimensionArea setEndRow(int endRow) {
        this.endRow = endRow;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public DimensionArea setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getCols() {
        return cols;
    }

    public DimensionArea setCols(int cols) {
        this.cols = cols;
        return this;
    }
}

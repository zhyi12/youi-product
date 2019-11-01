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
package org.youi.chart.engine.model.mark;

import org.youi.chart.engine.model.Label;
import org.youi.chart.engine.model.style.Emphasis;
import org.youi.chart.engine.model.style.ItemStyle;
import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class AbstractMark implements Domain{

    private String symbol;
    private Integer symbolSize;
    private Integer symbolRotate;
    private Boolean symbolKeepAspect;
    private Integer[] symbolOffset;
    private Boolean silent;
    private Label label;
    private ItemStyle itemStyle;
    private Emphasis emphasis;
    //private String data: [{...}];
    private Boolean animation;
    private Integer animationThreshold;
    private Integer animationDuration;
    private String animationEasing;
    private Integer animationDelay;
    private Integer animationDurationUpdate;
    private String animationEasingUpdate;
    private Integer animationDelayUpdate;

    public String getSymbol() {
        return symbol;
    }

    public AbstractMark setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public AbstractMark setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
        return this;
    }

    public Integer getSymbolRotate() {
        return symbolRotate;
    }

    public AbstractMark setSymbolRotate(Integer symbolRotate) {
        this.symbolRotate = symbolRotate;
        return this;
    }

    public Boolean getSymbolKeepAspect() {
        return symbolKeepAspect;
    }

    public AbstractMark setSymbolKeepAspect(Boolean symbolKeepAspect) {
        this.symbolKeepAspect = symbolKeepAspect;
        return this;
    }

    public Integer[] getSymbolOffset() {
        return symbolOffset;
    }

    public AbstractMark setSymbolOffset(Integer[] symbolOffset) {
        this.symbolOffset = symbolOffset;
        return this;
    }

    public Boolean getSilent() {
        return silent;
    }

    public AbstractMark setSilent(Boolean silent) {
        this.silent = silent;
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public AbstractMark setLabel(Label label) {
        this.label = label;
        return this;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public AbstractMark setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
        return this;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public AbstractMark setEmphasis(Emphasis emphasis) {
        this.emphasis = emphasis;
        return this;
    }

    public Boolean getAnimation() {
        return animation;
    }

    public AbstractMark setAnimation(Boolean animation) {
        this.animation = animation;
        return this;
    }

    public Integer getAnimationThreshold() {
        return animationThreshold;
    }

    public AbstractMark setAnimationThreshold(Integer animationThreshold) {
        this.animationThreshold = animationThreshold;
        return this;
    }

    public Integer getAnimationDuration() {
        return animationDuration;
    }

    public AbstractMark setAnimationDuration(Integer animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    public String getAnimationEasing() {
        return animationEasing;
    }

    public AbstractMark setAnimationEasing(String animationEasing) {
        this.animationEasing = animationEasing;
        return this;
    }

    public Integer getAnimationDelay() {
        return animationDelay;
    }

    public AbstractMark setAnimationDelay(Integer animationDelay) {
        this.animationDelay = animationDelay;
        return this;
    }

    public Integer getAnimationDurationUpdate() {
        return animationDurationUpdate;
    }

    public AbstractMark setAnimationDurationUpdate(Integer animationDurationUpdate) {
        this.animationDurationUpdate = animationDurationUpdate;
        return this;
    }

    public String getAnimationEasingUpdate() {
        return animationEasingUpdate;
    }

    public AbstractMark setAnimationEasingUpdate(String animationEasingUpdate) {
        this.animationEasingUpdate = animationEasingUpdate;
        return this;
    }

    public Integer getAnimationDelayUpdate() {
        return animationDelayUpdate;
    }

    public AbstractMark setAnimationDelayUpdate(Integer animationDelayUpdate) {
        this.animationDelayUpdate = animationDelayUpdate;
        return this;
    }
}

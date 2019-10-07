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
package org.youi.tools.flow;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.youi.framework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class NodeElementRender implements IElementRender{
    @Override
    public boolean supports(String name) {
        return "node".equals(name);
    }

    @Override
    public String render(Element element) {
        StringBuilder htmls = new StringBuilder();
        String group = element.attributeValue(FlowConstant.FLOW_NODE_ATTR_GROUP);
        group = group==null ?"entity":group;
        htmls.append("<div class=\"node "+group+"\" id=\""+element.attributeValue(FlowConstant.FLOW_ATTR_ID)+"\"")
                .append(buildDataAttributes(element))
                .append(" style=\"").append(buildStyleValue(element)).append("\"")
                .append(">")
                .append(element.getTextTrim())
                .append("</div>");

        return htmls.toString();
    }

    /**
     *
     * @param element
     * @return
     */
    private String buildDataAttributes(Element element) {
        StringBuilder htmls = new StringBuilder();
        for(Object attribute:element.attributes()){
            if(attribute instanceof Attribute){
                String attrName = ((Attribute) attribute).getName();
                if(attrName.startsWith("data-")){
                    htmls.append(attrName+"=\""+((Attribute) attribute).getText()+"\"");
                }
            }
        }
        return htmls.toString();
    }

    /**
     *
     * @param element
     * @return
     */
    private String buildStyleValue(Element element){
        String width = element.attributeValue(FlowConstant.FLOW_NODE_ATTR_WIDTH);
        String height = element.attributeValue(FlowConstant.FLOW_NODE_ATTR_HEIGHT);
        String left = element.attributeValue(FlowConstant.FLOW_NODE_ATTR_LEFT);
        String top = element.attributeValue(FlowConstant.FLOW_NODE_ATTR_TOP);
        String format = "left:{0}px;top:{1}px;";
        if(StringUtils.isNotEmpty(width) && StringUtils.isNotEmpty(height)){
            format = format+"width:{2}px;height:{3}px;";
        }
        return MessageFormat.format(format,left,top,width,height);
    }
}

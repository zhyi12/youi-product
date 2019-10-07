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

import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class FlowVisitor extends VisitorSupport {

    private List<IElementRender> elementRenders = new ArrayList<>();

    private StringBuilder htmlBuilder = new StringBuilder();

    public FlowVisitor(){
        elementRenders.add(new NodeElementRender());
        elementRenders.add(new TransitionElementRender());
    }

    @Override
    public void visit(Element element) {
        for(IElementRender elementRender:elementRenders){
            if(elementRender.supports(element.getName())){
                String html = elementRender.render(element);
                htmlBuilder.append(html==null?"":html);
            }
        }
    }

    /**
     *
     * @return
     */
    public String getRenderHtml(){
        return htmlBuilder.toString();
    }
}

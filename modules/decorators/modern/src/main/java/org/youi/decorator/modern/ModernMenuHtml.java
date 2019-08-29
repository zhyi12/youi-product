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
package org.youi.decorator.modern;

import org.springframework.stereotype.Component;
import org.youi.framework.core.dataobj.tree.MenuTreeNode;
import org.youi.framework.web.IMenuHtmlAdapter;

/**
 * 现代风格的菜单
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class ModernMenuHtml implements IMenuHtmlAdapter{
    @Override
    public boolean supports(String modern) {
        return "modern".equals(modern);
    }

    @Override
    public String buildMenuHtml(MenuTreeNode menuTreeNode) {
        StringBuilder menuHtmls = new StringBuilder("<ul class=\"menu-container-root\">");
        for(MenuTreeNode children:menuTreeNode.getChildren()){
            menuHtmls.append(children.toString()
                    .replaceAll("<ul>","<ul class=\"dropdown-menu\">")
                    .replaceAll("expandable","expandable dropdown")
                    .replaceAll("<span>","<span class=\"dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"true\">")
            );
        }
        menuHtmls.append("</ul>");
        return menuHtmls.toString();
    }

}

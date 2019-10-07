package org.youi.tools.flow;

import org.dom4j.Element;

/**
 * Created by zhouyi on 2019/10/7.
 */
public interface IElementRender {

    /**
     *
     * @param name
     * @return
     */
    boolean supports(String name);

    /**
     *
     * @param element
     * @return
     */
    String render(Element element);

}

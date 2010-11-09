/*
 * Copyright 2008-2009 the original author or authors.
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
package org.more.hypha.annotation.support;
import org.more.core.xml.XmlElementHook;
import org.more.core.xml.XmlStackDecorator;
import org.more.core.xml.stream.EndElementEvent;
import org.more.core.xml.stream.StartElementEvent;
import org.more.hypha.annotation.assembler.TagListener;
import org.more.hypha.configuration.DefineResourceImpl;
import org.more.hypha.configuration.Tag_Abstract;
import org.more.hypha.event.EndBuildEvent;
/**
 * 用于解析anno标签，负责注册{@link TagListener}类型对象。
 * @version 2010-9-22
 * @author 赵永春 (zyc@byshell.org)
 */
public class TagAnno_Anno extends Tag_Abstract implements XmlElementHook {
    /**创建{@link TagAnno_Anno}对象*/
    public TagAnno_Anno(DefineResourceImpl configuration) {
        super(configuration);
    }
    public void beginElement(XmlStackDecorator context, String xpath, StartElementEvent event) {
        String packageText = event.getAttributeValue("package");
        TagListener annoListener = new TagListener(packageText);
        this.getConfiguration().getEventManager().addEventListener(EndBuildEvent.class, annoListener);
    }
    public void endElement(XmlStackDecorator context, String xpath, EndElementEvent event) {}
}
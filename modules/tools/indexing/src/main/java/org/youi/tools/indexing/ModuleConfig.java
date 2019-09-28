/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package org.youi.tools.indexing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.youi.framework.context.annotation.Module;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  zhouyi</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 2017-06-12</p>
 */

@Module(
	name="tools.indexing",
	caption = "全文检索"
)
@Configuration
@ComponentScan(basePackages = {"org.youi.tools.indexing.service.impl"})
public class ModuleConfig {
	
}

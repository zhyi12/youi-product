/*
 * YOUI框架
 * Copyright 2018 the original author or authors.
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
package org.youi.periodfile.service;

import java.util.List;
import java.util.Collection;

import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.periodfile.entity.PeriodFile;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface PeriodFileManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="8005080101",caption="主键查询固定期文件")
    List<PeriodFile> getPeriodFiles(
        @ConditionCollection(domainClazz=PeriodFile.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8005080102",caption="主键查询固定期文件")
    PeriodFile getPeriodFile(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="8005080103",caption="分页查询固定期文件")
    PagerRecords getPagerPeriodFiles(Pager pager,//分页条件
    @ConditionCollection(domainClazz=PeriodFile.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="8005080104",caption="保存固定期文件")
    PeriodFile savePeriodFile(PeriodFile o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="8005080105",caption="主键删除固定期文件")
    void removePeriodFile(@ServiceParam(name="id") String id);

    /**
     * 上传文件
     * @param id
     * @param filePath
     */
    @EsbServiceMapping(trancode="8005080106",caption="主键删除固定期文件")
    void uploadFile(@ServiceParam(name="id") String id,@ServiceParam(name="filePath")String filePath);


}

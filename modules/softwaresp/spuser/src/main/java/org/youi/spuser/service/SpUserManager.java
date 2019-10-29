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
package org.youi.spuser.service;

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

import org.youi.framework.security.IUser;
import org.youi.spuser.entity.SpUser;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface SpUserManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="7001010101",caption="主键查询开发人员")
    List<SpUser> getSpUsers(
        @ConditionCollection(domainClazz=SpUser.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="7001010102",caption="主键查询开发人员")
    SpUser getSpUser(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="7001010103",caption="分页查询开发人员")
    PagerRecords getPagerSpUsers(Pager pager,//分页条件
    @ConditionCollection(domainClazz=SpUser.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="7001010104",caption="保存开发人员")
    SpUser saveSpUser(SpUser o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="7001010105",caption="主键删除开发人员")
    void removeSpUser(@ServiceParam(name="id") String id);

    /**
     * 修改登录用户密码
     * @param username
     * @param password
     * @param confirmPassword
     * @param oldPassword
     */
    @EsbServiceMapping(trancode="7001010106",caption="修改开发人员密码")
    void modifyPassword(
            @ServiceParam(name="username",pubProperty = "username") String username,
            @ServiceParam(name="password") String password,
            @ServiceParam(name="confirmPassword") String confirmPassword,
            @ServiceParam(name="oldPassword") String oldPassword);

    /**
     * 重置开发人员密码
     * @param id
     */
    @EsbServiceMapping(trancode="7001010107",caption="重置密码")
    void resetPassword(@ServiceParam(name="id")String id);

    /**
     * 通过用户登录名查找用户
     * @param username
     * @return
     */
    SpUser findUserByName(String username);

}

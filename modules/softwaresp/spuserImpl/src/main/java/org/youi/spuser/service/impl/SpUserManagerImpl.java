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
package org.youi.spuser.service.impl;

import java.util.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.framework.util.StringUtils;
import org.youi.spuser.entity.SpUser;
import org.youi.spuser.mongo.SpUserDao;
import org.youi.spuser.service.SpUserManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("spUserManager")
public class SpUserManagerImpl implements SpUserManager{

    @Autowired(required = false)
	private SpUserDao spUserDao;

    @Autowired(required = false)
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * setter
     * @param spUserDao
     */
    public void setSpUserDao(SpUserDao spUserDao) {
        this.spUserDao = spUserDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="7001010101",caption="列表开发人员")
    @Override
    public List<SpUser> getSpUsers(
    	@ConditionCollection(domainClazz=SpUser.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return spUserDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="7001010102",caption="主键查询开发人员")
    @Override
    public SpUser getSpUser(@ServiceParam(name="id") String id) {
    	return spUserDao.get(id);
    }
	
	@EsbServiceMapping(trancode="7001010103",caption="分页查询开发人员")
    @Override
	public PagerRecords getPagerSpUsers(Pager pager,//分页条件
			@ConditionCollection(domainClazz=SpUser.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = spUserDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="7001010104",caption="保存开发人员")
    @Override
    public SpUser saveSpUser(SpUser o){
    	String spUserId = o.getId();
    	boolean isUpdate = StringUtils.isNotEmpty(spUserId);
    	if(isUpdate){//修改
            SpUser spUser = spUserDao.get(spUserId);
            o.setPassword(spUser.getPassword());//密码
            o.setInitPassword(spUser.getInitPassword());//初始化密码
    	}else{//新增
            String initPassword = buildInitPassword();//初始化密码生成器
            if(passwordEncoder!=null){
                o.setPassword(passwordEncoder.encode(initPassword));//密码
            }
            o.setPassword(initPassword);//初始化密码
    	}
    	return spUserDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="7001010105",caption="主键删除开发人员")
    @Override
    public void removeSpUser(@ServiceParam(name="id") String id){
    	spUserDao.remove(id);
    }

    @EsbServiceMapping(trancode="7001010106",caption="修改开发人员密码")
    public void modifyPassword(
            @ServiceParam(name="username",pubProperty = "username") String username,
            @ServiceParam(name="password") String password,
            @ServiceParam(name="confirmPassword") String confirmPassword,
            @ServiceParam(name="oldPassword") String oldPassword){
        if(StringUtils.isEmpty(confirmPassword)){
            throw new BusException("确认密码不能为空.");
        }
        if(!confirmPassword.equals(password)){
            throw new BusException("两次密码输入不一致.");
        }
        SpUser spUser = findUserByName(username);//查找用户
        if(spUser!=null){
            if(!spUser.getPassword().equals(passwordEncoder.encode(oldPassword))){
                throw new BusException("旧密码错误.");
            }
        }
        //设置密码
        spUser.setPassword(passwordEncoder.encode(password));
        spUserDao.save(spUser);//更新用户密码
    }

    @EsbServiceMapping(trancode="7001010107",caption="重置密码")
    public void resetPassword(@ServiceParam(name="id")String id){
        if(spUserDao.exists(id)){
            SpUser spUser = spUserDao.get(id);
            if(StringUtils.isEmpty(spUser.getInitPassword())){
                spUser.setInitPassword(buildInitPassword());
            }
            spUser.setPassword(passwordEncoder.encode(spUser.getInitPassword()));
            spUserDao.save(spUser);
        }
    }

    @Override
    public SpUser findUserByName(String username) {
        return spUserDao.findByUsernameOrPhone(username,username);
    }

    /**
     * 生成初始化密码
     * @return
     */
    private String buildInitPassword() {
        String num = new Double(Math.random()*10000000).toString().replace("\\.","");
        num = org.apache.commons.lang.StringUtils.leftPad(num,6,"0");
        return "sp@"+ num.substring(0,6);
    }

}

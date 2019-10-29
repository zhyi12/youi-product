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
package org.youi.spuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.framework.security.IUser;
import org.youi.framework.security.IUserAdapter;
import org.youi.spuser.Constant;
import org.youi.spuser.entity.SpUser;
import org.youi.spuser.service.SpUserManager;

/**
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class SpLoginUserAdapter implements IUserAdapter{

    @Autowired
    private SpUserManager spUserManager;

    @Override
    public IUser findUserByName(String username) {
        SpUser spUser = spUserManager.findUserByName(username);
        if(spUser!=null){
            //通过手机号查找
            spUser.addRole(Constant.ROLE_SP_USER);
            //设置机构ID（开发商的机构ID）
            spUser.addConfig("agencyId",spUser.getAgencyId())
                    .addConfig("phone",spUser.getPhone());
        }
        return spUser;
    }

    @Override
    public boolean supports(String username) {
        //TODO 使用本地缓存判断username是否存在
        return true;
    }
}

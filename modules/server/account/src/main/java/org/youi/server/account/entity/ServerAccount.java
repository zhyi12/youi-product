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
package org.youi.server.account.entity;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.security.IUser;
import org.youi.framework.security.PrincipalConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ServerAccount implements IUser,Domain{

    private static final long serialVersionUID = 8216994857670083655L;

    private String username;//服务登录账户

    private String password;//服务密码

    private PrincipalConfig principalConfig;

    @Override
    public List<String> roleIds() {
        List<String> roleIds = new ArrayList<>();
        roleIds.add("ROLE_SERVER");
        return roleIds;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public PrincipalConfig getPrincipalConfig() {
        return principalConfig;
    }

    public void setPrincipalConfig(PrincipalConfig principalConfig) {
        this.principalConfig = principalConfig;
    }

}

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
package org.youi.server.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.youi.framework.security.IUserDetailsAdapter;
import org.youi.server.account.entity.ServerAccount;
import org.youi.server.caller.config.ServerCallerProperties;
import org.youi.server.caller.service.ServerSecurityService;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class ServerAccountAdapter implements IUserDetailsAdapter<ServerAccount> {

    @Autowired(required = false)
    private ServerCallerProperties serverCallerProperties;

    @Autowired(required = false)
    private ServerSecurityService serverSecurityService;

    @Autowired(required = false)
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ServerAccount loadUserByUsername(String loginName) {
        String serverId = loginName.substring(serverCallerProperties.getServerPrefix().length());
        ServerAccount serverAccount = new ServerAccount();
        serverAccount.setUsername(loginName);
        //构建password，确保和请求参数中的密码一致
        serverAccount.setPassword(passwordEncoder.encode(serverSecurityService.encodePassword(serverId)));
        return serverAccount;
    }

    @Override
    public boolean supports(String loginName) {
        //使用固定前缀的登录名允许登录
        return loginName.startsWith(serverCallerProperties.getServerPrefix());
    }
}

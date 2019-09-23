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
package org.youi.server.caller.service;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.generators.DESKeyGenerator;
import org.bouncycastle.jcajce.provider.asymmetric.util.DESUtil;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.youi.framework.core.exception.BusException;
import org.youi.server.caller.config.ServerCallerProperties;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 *
 * 生成无用户微服务间的安全token
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class ServerSecurityService implements InitializingBean{

    @Value("${spring.application.name}")
    private String appName;

    @Value("${youi.server.caller.keystoreFile}")
    private Resource keystoreResource;

    @Autowired(required = false)
    private ServerCallerProperties serverCallerProperties;

    //
    private String securityKey;//app唯一密码

    /**
     *
     * @return
     */
    public String getSecurityKey(){
        return securityKey;
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public String encodePassword(String password){
        //简单加密处理
        return new String(Base64.encode((password+securityKey).getBytes()));
    }

    /**
     * 安全密钥
     * @return
     */
    private String initSecurityKey(){
        if(keystoreResource!=null&&keystoreResource.exists()) {
            try (InputStream inputStream = keystoreResource.getInputStream()) {
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                ks.load(inputStream, serverCallerProperties.getKeystorePassword().toCharArray());
                Certificate cert = ks.getCertificate(serverCallerProperties.getKeystoreName());
                if (cert == null) {
                    throw new BusException("密钥配置错误，keyStoreName[" + serverCallerProperties.getKeystoreName() + "]不匹配。");
                }
                //使用公钥对称加密appName
                byte[] keys = ArrayUtils.addAll(cert.getPublicKey().getEncoded(), appName.getBytes());
                return Base64.toBase64String(keys);
            } catch (KeyStoreException e) {
                throw new BusException(e.getMessage());
            } catch (IOException e) {
                throw new BusException(e.getMessage());
            } catch (CertificateException e) {
                throw new BusException(e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new BusException(e.getMessage());
            }
        }
        return "server-youi";//默认值
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化securityKey
        securityKey = this.initSecurityKey();
    }
}

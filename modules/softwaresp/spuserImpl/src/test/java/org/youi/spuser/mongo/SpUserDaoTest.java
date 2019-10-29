package org.youi.spuser.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.spuser.entity.SpUser;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/10/9.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class SpUserDaoTest {

    @Autowired
    private SpUserDao spUserDao;

    @Test
    public void testFindByUsernameOrPhone(){
        String username = "zhangsan",phone = "18610629547";
        SpUser spUser = new SpUser();
        spUser.setId(username);
        spUser.setUsername(username);
        spUser.setPhone(phone);
        spUserDao.save(spUser);

        SpUser user = spUserDao.findByUsernameOrPhone(username,username);
        SpUser phoneUser = spUserDao.findByUsernameOrPhone(phone,phone);

        assertEquals(user,phoneUser);

        spUserDao.remove(username);

    }
}
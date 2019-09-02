package org.youi.metadata.dictionary.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.metadata.dictionary.entity.BussTableColumn;

/**
 * Created by zhouyi on 2019/8/31.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class BussTableColumnDaoTest {

    @Autowired
    private BussTableColumnDao bussTableColumnDao;

    @Test
    public void genDatas(){
        BussTableColumn bussTableColumn = new BussTableColumn();
        bussTableColumn.setColumnName("AAA");
        bussTableColumnDao.save(bussTableColumn);
    }
}
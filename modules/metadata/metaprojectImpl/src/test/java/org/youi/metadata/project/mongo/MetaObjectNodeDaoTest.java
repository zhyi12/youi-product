package org.youi.metadata.project.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.metadata.object.MetaObjectConstants;
import org.youi.metadata.project.entity.MetaObjectNode;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/5.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class MetaObjectNodeDaoTest {

    @Autowired
    private  MetaObjectNodeDao metaObjectNodeDao;

    @Test
    public void findByProjectIdAndMetaObjectNameIn() throws Exception {

    }

    @Test
    public void savePlan(){
        MetaObjectNode metaObjectNode = new MetaObjectNode();
        metaObjectNode.setId("plan001");
        metaObjectNode.setText("软件和信息服务业统计调查制度");
        metaObjectNode.setProjectId("proj001");
        metaObjectNode.setMetaObjectName(MetaObjectConstants.META_OBJECT_NAME_PLAN);
        metaObjectNodeDao.save(metaObjectNode);
    }

    @Test
    public void saveTask(){
        MetaObjectNode metaObjectNode = new MetaObjectNode();
        metaObjectNode.setId("task001");
        metaObjectNode.setText("软件和信息服务业统计方案");
        metaObjectNode.setProjectId("proj001");
        metaObjectNode.setMetaObjectName(MetaObjectConstants.META_OBJECT_NAME_TASK);
        metaObjectNode.addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,"plan001");
        metaObjectNodeDao.save(metaObjectNode);
    }

    @Test
    public void saveReport(){
        MetaObjectNode metaObjectNode = new MetaObjectNode();
        metaObjectNode.setId("report001");
        metaObjectNode.setText("企业基本情况表");
        metaObjectNode.setProjectId("proj001");
        metaObjectNode.setNodeFolderPath("/年报");
        metaObjectNode.setMetaObjectName(MetaObjectConstants.META_OBJECT_NAME_REPORT);
        metaObjectNode.addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,"plan001")
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_TASK,"task001");
        metaObjectNodeDao.save(metaObjectNode);
    }

}
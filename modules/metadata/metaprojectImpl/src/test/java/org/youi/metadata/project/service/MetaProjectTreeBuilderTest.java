package org.youi.metadata.project.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.metadata.common.IMetaParentFinderAdapter;
import org.youi.metadata.object.MetaObjectConstants;
import org.youi.metadata.project.entity.MetaObjectNode;
import org.youi.metadata.project.service.impl.MetaProjectTreeBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by zhouyi on 2019/9/5.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ServiceTestConfig.class})
public class MetaProjectTreeBuilderTest {

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Autowired
    private MetaProjectTreeBuilder metaProjectTreeBuilder;//

    @Mock
    private IMetaParentFinderAdapter metaTaskParentFinderAdapter;

    @Mock
    private IMetaParentFinderAdapter metaReportParentFinderAdapter;

    @Before
    public void setup(){
        List<IMetaParentFinderAdapter> metaParentFinderAdapters = new ArrayList<>();
        metaParentFinderAdapters.add(metaTaskParentFinderAdapter);
        metaParentFinderAdapters.add(metaReportParentFinderAdapter);

        metaProjectTreeBuilder.setMetaParentFinderAdapters(metaParentFinderAdapters);

        when(metaTaskParentFinderAdapter.supports(MetaObjectConstants.META_OBJECT_NAME_TASK)).thenReturn(true);
        when(metaTaskParentFinderAdapter.findParentMetaObjectName()).thenReturn(MetaObjectConstants.META_OBJECT_NAME_PLAN);

        when(metaReportParentFinderAdapter.supports(MetaObjectConstants.META_OBJECT_NAME_REPORT)).thenReturn(true);
        when(metaReportParentFinderAdapter.findParentMetaObjectName()).thenReturn(MetaObjectConstants.META_OBJECT_NAME_TASK);
    }

    /**
     * @Story            构建元数据树 - .metaProjectTreeBuilder.buildTreeNodes
     * @Case             构建制度、方案、报表树
     * @Description
     */
    @Test
    public void testBuildTreeNodes(){
        List<MetaObjectNode> metaObjectNodes = new ArrayList<>();
        String planId = "plan001",taskId ="task001",reportId = "report001";
        metaObjectNodes.add(buildMetaPlanObjectNode(planId,planId));
        metaObjectNodes.add(buildMetaTaskObjectNode(taskId,taskId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,planId));
        metaObjectNodes.add(buildMetaReportObjectNode(reportId,reportId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,planId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_TASK,taskId));

        List<TreeNode> treeNodes = metaProjectTreeBuilder.buildTreeNodes(MetaObjectConstants.META_OBJECT_NAME_PLAN,metaObjectNodes);
        //验证树形结构
        Assert.assertEquals("","M_plan001\n" +
                "  M_task001\n" +
                "    M_report001\n",printTreeNodes("",treeNodes));
    }
    /**
     * @Story            构建元数据树 - .metaProjectTreeBuilder.buildTreeNodes
     * @Case             构建制度、方案、报表树，其中报表上支持文件夹
     * @Description
     */
    @Test
    public void testBuildTreeNodesWithFolderPath(){
        List<MetaObjectNode> metaObjectNodes = new ArrayList<>();
        String planId = "plan001",taskId ="task001",reportId = "report001",reportId2 = "report002",reportId3="report003";
        metaObjectNodes.add(buildMetaPlanObjectNode(planId,planId));
        metaObjectNodes.add(buildMetaTaskObjectNode(taskId,taskId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,planId));
        metaObjectNodes.add(buildMetaReportObjectNode(reportId,reportId).nodeFolderPath("/年报")
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,planId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_TASK,taskId));

        metaObjectNodes.add(buildMetaReportObjectNode(reportId2,reportId2).nodeFolderPath("/年报")
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,planId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_TASK,taskId));

        metaObjectNodes.add(buildMetaReportObjectNode(reportId3,reportId3).nodeFolderPath("/月报")
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_PLAN,planId)
                .addMetaObjectParent(MetaObjectConstants.META_OBJECT_NAME_TASK,taskId));

        List<TreeNode> treeNodes = metaProjectTreeBuilder.buildTreeNodes(MetaObjectConstants.META_OBJECT_NAME_PLAN,metaObjectNodes);

        //验证树形结构
        Assert.assertEquals("","M_plan001\n" +
                        "  M_task001\n" +
                        "    /年报\n" +
                        "      M_report001\n"+
                        "      M_report002\n"+
                        "    /月报\n" +
                        "      M_report003\n"
                ,printTreeNodes("",treeNodes));

    }

    /****************************************  工具方法  ****************************************/

    private String printTreeNodes(String prefix,List<TreeNode> treeNodes) {
        StringBuilder stringBuilder = new StringBuilder();
        treeNodes.forEach(treeNode -> {
            stringBuilder.append((prefix+treeNode.getId())+"\n");
            if(!CollectionUtils.isEmpty(treeNode.getChildren())){
                stringBuilder.append(printTreeNodes(prefix+"  ",treeNode.getChildren()));
            }
        });
        return stringBuilder.toString();
    }

    /****************************************  模拟数据  ****************************************/
    //制度
    private MetaObjectNode buildMetaPlanObjectNode(String id,String text){
        return buildMetaObjectNode(id,text,MetaObjectConstants.META_OBJECT_NAME_PLAN);
    }
    //方案
    private MetaObjectNode buildMetaTaskObjectNode(String id,String text){
        return buildMetaObjectNode(id,text,MetaObjectConstants.META_OBJECT_NAME_TASK);
    }
    //报表
    private MetaObjectNode buildMetaReportObjectNode(String id,String text){
        return buildMetaObjectNode(id,text,MetaObjectConstants.META_OBJECT_NAME_REPORT);
    }
    /**
     * 构造元数据对象
     * @param id
     * @param text
     * @param metaObjectName
     * @return
     */
    private MetaObjectNode buildMetaObjectNode(String id,String text,String metaObjectName){
        MetaObjectNode metaObjectNode = new MetaObjectNode();
        metaObjectNode.setId(id);
        metaObjectNode.setText(text);
        metaObjectNode.setMetaObjectName(metaObjectName);
        return metaObjectNode;
    }

}
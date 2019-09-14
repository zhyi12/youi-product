package org.youi.metadata.dictionary.service;

import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.List;

/**
 * Created by zhouyi on 2019/9/14.
 */
public interface DataResourceService {

    @EsbServiceMapping
    List<TreeNode> getUserDataResourceTree(
            @ServiceParam(name = "roleIds") String loginRoleIds,
            @ServiceParam(name = "loginName") String loginUserId,
            @ServiceParam(name = "agencyId") String loginAgencyId);

    @EsbServiceMapping
    List<TreeNode> getTableColumnTreeNodes(@ServiceParam(name = "id") String dataTableId);
}

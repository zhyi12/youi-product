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
package org.youi.periodfile.service.impl;

import java.util.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.fileserver.filestore.entity.FileStore;
import org.youi.fileserver.filestore.service.FileClientManager;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.periodfile.entity.PeriodFile;
import org.youi.periodfile.mongo.PeriodFileDao;
import org.youi.periodfile.service.PeriodFileManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("periodFileManager")
public class PeriodFileManagerImpl implements PeriodFileManager{

    @Autowired(required = false)
	private PeriodFileDao periodFileDao;

    @Autowired
    private FileClientManager fileClientManager;

    /**
     * setter
     * @param periodFileDao
     */
    public void setPeriodFileDao(PeriodFileDao periodFileDao) {
        this.periodFileDao = periodFileDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8005080101",caption="列表固定期文件")
    @Override
    public List<PeriodFile> getPeriodFiles(
    	@ConditionCollection(domainClazz=PeriodFile.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return periodFileDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8005080102",caption="主键查询固定期文件")
    @Override
    public PeriodFile getPeriodFile(@ServiceParam(name="id") String id) {
    	return periodFileDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8005080103",caption="分页查询固定期文件")
    @Override
	public PagerRecords getPagerPeriodFiles(Pager pager,//分页条件
			@ConditionCollection(domainClazz=PeriodFile.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = periodFileDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8005080104",caption="保存固定期文件")
    @Override
    public PeriodFile savePeriodFile(PeriodFile o){
//    	String periodFileId = o.getPeriodFileId();
//    	boolean isUpdate = StringUtils.isNotEmpty(periodFileId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return periodFileDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8005080105",caption="主键删除固定期文件")
    @Override
    public void removePeriodFile(@ServiceParam(name="id") String id){
    	periodFileDao.remove(id);
    }

    @Override
    @EsbServiceMapping(trancode="8005080106",caption="主键删除固定期文件")
    public void uploadFile(@ServiceParam(name="id") String id,@ServiceParam(name="filePath")String filePath) {
        //获取分布式文件信息
        FileStore fileStore = fileClientManager.processFile(filePath,(file -> {
            //文件判断，只允许上传xls、xlsx、csv、zip、rar、dbf文件
            //文件大小判断
            return true;
        }));

        if(fileStore!=null){
            PeriodFile periodFile = getPeriodFile(id);
            periodFile.setFileName(fileStore.getUploadFileName());
            periodFile.setUploadTime(System.currentTimeMillis());
            periodFile.setFilePath(filePath);
        }
    }
}

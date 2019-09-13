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
package org.youi.metadata.dictionary.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_metadata_folder")
public class MetaDataFolder implements Domain{

    private static final long serialVersionUID = -6069845600086011400L;

    @Id
    private String folderId;//文件夹ID

    private String folderCaption;//文件夹名称

    private String parentFolderId;//父文件夹ID

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderCaption() {
        return folderCaption;
    }

    public void setFolderCaption(String folderCaption) {
        this.folderCaption = folderCaption;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaDataFolder that = (MetaDataFolder) o;

        if (folderId != null ? !folderId.equals(that.folderId) : that.folderId != null) return false;
        if (folderCaption != null ? !folderCaption.equals(that.folderCaption) : that.folderCaption != null)
            return false;
        return parentFolderId != null ? parentFolderId.equals(that.parentFolderId) : that.parentFolderId == null;
    }

    @Override
    public int hashCode() {
        int result = folderId != null ? folderId.hashCode() : 0;
        result = 31 * result + (folderCaption != null ? folderCaption.hashCode() : 0);
        result = 31 * result + (parentFolderId != null ? parentFolderId.hashCode() : 0);
        return result;
    }

}

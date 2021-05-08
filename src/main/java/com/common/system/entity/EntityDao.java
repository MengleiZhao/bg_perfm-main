package com.common.system.entity;


import java.io.Serializable;
import java.util.Date;

public interface EntityDao extends Serializable {
    /**
     * 绑定数据库标
     */
    public String getJoinTable();
    /**
     * 实体tetity的主键
     */
    public Integer getEntryId();

    /**
     * 文件名称
     * @param fileName
     */
    public void setFileName(String fileName) ;

    /**
     * 文件路径
     * @param filePath
     */
    public void setFilePath(String filePath) ;

    /**
     * 文件大小
     * @param fileSize
     */
    public void setFileSize(String fileSize) ;

    /**
     * 文件上传者
     * @param createor
     */
    public void setCreateor(String createor);

    /**
     * 创建时间
     * @param createTime
     */
    public void setCreateTime(Date createTime);
}
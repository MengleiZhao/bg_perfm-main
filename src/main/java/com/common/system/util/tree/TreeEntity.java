package com.common.system.util.tree;

import lombok.Data;

import java.util.List;

/**
 * Title: TreeEntity
 * Description：用于装前台tree类数据
 * Author: 陈睿超
 * Date: 2021/3/10 14:44
 * Updater: 陈睿超
 * Date: 2021/3/10 14:44
 * Company: 天职国际
 * Version:
 **/
@Data
public class TreeEntity implements DataTree{
    
    private Integer id;
    private String label;
    private Integer parentId;
    private List<TreeEntity> childList;


    @Override
    public void setChildList(List childList) {
        this.childList = childList;
    }
}

package com.common.system.util.tree;

import java.util.List;

/**
 * Title: DataTree
 * Description：tree接口
 * Author: 陈睿超
 * Date: 2021/3/10 14:24
 * Updater: 陈睿超
 * Date: 2021/3/10 14:24
 * Company: 天职国际
 * Version:
 **/
public interface DataTree<T> {
    
    public Integer getId();

    public Integer getParentId();

    public void setChildList(List<T> childList);

    public List<T> getChildList();
    
    
    
}

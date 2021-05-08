package com.common.system.page;


import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 下拉框
 * </p>
 * @author 安达
 * @since 2021-03-17
 */
@Data
public class ComboboxJson implements Serializable {

    private static final long serialVersionUID = 6777379740318824580L;

    //主键id
    public Integer id;
    //编码
    public String code;
    //文本
    public String text;
    //描述
    public String desc;
    //默认选择
    public boolean selected;


}
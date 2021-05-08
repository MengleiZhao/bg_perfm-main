package com.common.system.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.common.business.planmgr.otherdraftdesign.entity.TProWorkingManuscriptDesignResearch;
import com.common.system.entity.Combobox;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("rc_dept")
public class RcDept   implements Combobox{
    private Integer id;

    private Integer num;

    private Integer pid;

    private String simpleName;

    private String fullName;

    private String tips;

    private Integer version;

    public Integer getId() {
        return id;
    }

    @Override
    public String getCode() {
        return id+"";
    }

    @Override
    public String getText() {
        return simpleName;
    }

    @Override
    public String getDesc() {
        return fullName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName == null ? null : simpleName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
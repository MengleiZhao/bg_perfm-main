package com.common.system.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.common.system.entity.Combobox;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典管理表
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@Log
@TableName("rc_dict")
public class RcDict extends Model<RcDict>  implements Combobox {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 编码
     */
	@TableField("dic_code")
	private String dicCode;
    /**
     * 名称
     */
	@TableField("dic_name")
	private String dicName;
    /**
     * 类型
     */
	private String type;
	/**
	 * 级联依赖
	 */
	@TableField("parent_code")
	private String parentCode;
    /**
     * 描述
     */
	private String descript;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RcDict{" +
			", id=" + id +
			", dicCode=" + dicCode +
			", dicName=" + dicName +
			", type=" + type +
			", descript=" + descript +
			", sort=" + sort +
			", createTime=" + createTime +
			"}";
	}

	@Override
	public String getCode() {
		return dicCode;
	}

	@Override
	public String getText() {
		return dicName;
	}

	@Override
	public String getDesc() {
		return descript;
	}
}

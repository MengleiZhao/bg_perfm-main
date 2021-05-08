package com.common.business.collection.meanslist.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * <p>
 * 存放项目资料清单模板信息
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
@TableName("t_development_information_list_temp")
public class TDevelopmentInformationListTemp extends Model<TDevelopmentInformationListTemp> {

    private static final long serialVersionUID = 1L;

	@TableId(value="IN_ID", type= IdType.AUTO)
	private Integer inId;
	@TableField("INFO_TYPE1")
	private String infoType1;
	@TableField("INFO_TYPE2")
	private String infoType2;
	@TableField("INFO_NAME")
	private String infoName;
	@TableField("CREATEOR")
	private String createor;
	@TableField("CREATE_TIME")
	private Date createTime;
	@TableField("UPDATEOR")
	private String updateor;
	@TableField("UPDATE_TIME")
	private Date updateTime;


	@Override
	protected Serializable pkVal() {
		return this.inId;
	}

}

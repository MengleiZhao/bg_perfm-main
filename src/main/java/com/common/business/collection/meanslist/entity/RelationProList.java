package com.common.business.collection.meanslist.entity;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *	项目资料清单关系表
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Data
@ExcelTarget("relationProList")
@Accessors(chain = true)
@TableName("relation_pro_list")
public class RelationProList extends Model<RelationProList> {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@TableId(value="ID_R", type= IdType.AUTO)
	private Integer idR;
	/**
	 * 外键 （TProPerformanceInfo表的主键idA）
	 */
	@TableField("ID_A")
	private Integer idA;
	/**
	 * 版本号
	 */
	@TableField("VERSION_NO")
	private String versionNo;
	/**
	 * 拟定（申请）时间
	 */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 下级审批人ID
	 */
	@TableField("NEXT_CHECK_ID")
	private String nextCheckId;
	/**
	 * 下级审批人姓名
	 */
	@TableField("NEXT_CHECK_NAME")
	private String nextCheckName;
	/**
	 * 版本状态
	 * -1-退回
	 * 0-暂存
	 * 1-审批中
	 * 2-已完成
	 */
	@TableField("CREATE_STAUTS")
	private String createStauts;
	/**
	 * 拟定人
	 */
	@TableField("CREATE_UASE_NAME")
	private String createUaseName;	
	 /**
	 * 拟定人id
	 */
	@TableField("CREATE_UASE_ID")
	private String createUaseId;	
	/**
	 * 单据状态
	 * 1-正常
	 * 9-删除
	 */
	@TableField("RELATION_STATUS")
	private String relationStatus;
	
	/**
	 * 子项目类
	 */
	@ExcelEntity()
	@TableField(exist = false)
	private TProPerformanceInfo proPerformanceInfo;
	/**
	 * 接收“资料清单拟定”json转换成list
	 */
	@TableField(exist = false)
	private List<TDevelopmentInformationList> developmentInformationLists;
	
	
	@Override
	protected Serializable pkVal() {
		return this.idR;
	}

}

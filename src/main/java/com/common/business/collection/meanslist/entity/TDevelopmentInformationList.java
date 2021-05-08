package com.common.business.collection.meanslist.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.common.business.collection.means.entity.TInformations;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资料清单拟定
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@TableName("t_development_information_list")
public class TDevelopmentInformationList extends Model<TDevelopmentInformationList>  {

    private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@TableId(value="ID_B", type= IdType.AUTO)
	private Integer idB;
	/**
	 * 外键（RelationProList表的主键idR）
	 */
	@TableField("ID_R")
	private Integer idR;
    /**
     * 资料大类
     */
	@Size(max = 100,message = "资料一级分类字段大小不得超过100")
    @Excel(name = "资料一级分类",orderNum = "1")
	@TableField("INFO_TYPE1")
	private String infoType1;
    /**
     * 二级分类
     */
    @Size(max = 200,message = "资料二级分类字段大小不得超过200")
    @Excel(name = "资料二级分类",orderNum = "2")
	@TableField("INFO_TYPE2")
	private String infoType2;
    /**
     * 资料名称
     */
	@Size(max = 200,message = "资料名称字段大小不得超过200")
    @Excel(name = "资料名称",orderNum = "3")
	@TableField("INFO_NAME")
	private String infoName;
    /**
     * 资料收集人ID
     */
	@TableField("DATA_COLLECTOR_ID")
	private String dataCollectorId;
    /**
     * 资料收集人姓名
     */
    @Excel(name = "资料收集人",orderNum = "4")
	@TableField("DATA_COLLECTOR_NAME")
	private String dataCollectorName;
    /**
     * 任务开始时间
     */
	@Excel(name = "开始时间",orderNum = "5")
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField("TASK_START_TIME")
	private Date taskStartTime;
    /**
     * 任务结束时间
     */
	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Excel(name = "结束时间",orderNum = "6")
	@TableField("TASK_END_TIME")
	private Date taskEndTime;
    /**
     * 工期（小时）
     */
	@Excel(name = "工期",orderNum = "7")
	@TableField("TASK_DURATION")
	private String taskDuration;

	/**
	 * 数据库新增字段
	 * 0-未上传（默认） 含暂存
	 * 1-已上传
	 * 2-已认证
	 */
	@TableField("TASK_STAUTS")
	private String taskStatus;

	/**
	 *数据库不存在字段  资料收集上传-->资料上传状态      1-上传   2-暂存
	 */
	@TableField(exist = false)
	private String informationStatus;

	/**
	 *数据库不存在字段   资料收集上传-->资料表 与资料拟定清单是一对多的关系
	 */
	@TableField(exist = false)
	private List<TInformations> informations;

	
	@Override
	protected Serializable pkVal() {
		return this.idB;
	}

}

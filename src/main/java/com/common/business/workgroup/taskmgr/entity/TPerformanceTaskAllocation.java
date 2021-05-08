package com.common.business.workgroup.taskmgr.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 绩效任务分配表
 * </p>
 *
 * @author 安达
 * @since 2021-03-09
 */
@Data
@Accessors(chain = true)
@TableName("t_performance_task_allocation")
public class TPerformanceTaskAllocation extends Model<TPerformanceTaskAllocation>  {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_B")
	private Integer idB;
	@TableField("ID_A")
	private Integer idA;
    /**
     * 任务编号
     */
	@TableField("TASK_CODE")
	private String taskCode;
    /**
     * 任务描述
     */
	@TableField("TASK_DESC")
	private String taskDesc;
    /**
     * 任务层级
     */
	@TableField("TASK_LEAVEL")
	private Integer taskLeavel;
    /**
     * 上级任务编号
     */
	@TableField("PARENT_ID")
	private Integer parentId;
    /**
     * 计划开始时间
     */
	@TableField("START_TIME_PLAN")
	private Date startTimePlan;
    /**
     * 计划结束时间
     */
	@TableField("END_TIME_PLAN")
	private Date endTimePlan;
    /**
     * 任务工期
     */
	@TableField("TASK_DURATION")
	private String taskDuration;
    /**
     * 资源
     */
	@TableField("TASK_RESOURCES")
	private String taskResources;
    /**
     * 创建人
     */
	@TableField("CREATEOR")
	private String createor;
    /**
     * 创建时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
    /**
     * 修改人
     */
	@TableField("UPDATEOR")
	private String updateor;
    /**
     * 修改时间
     */
	@TableField("UPDATE_TIME")
	private Date updateTime;

	/**
	 * 0-未选择
	 * 1-已选择（默认）
	 */
	@TableField("HAVE_BEEN_CHOOSE")
	private String haveBeenChoose;

	/**
	 * 不在数据库tree使用
	 */
	@TableField(exist = false)
	private List<TPerformanceTaskAllocation> children =new ArrayList<>();

	@Override
	protected Serializable pkVal() {
		return idB;
	}
}

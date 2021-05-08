package com.common.business.workgroup.taskmgr.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 绩效任务分配模板表
 * </p>
 *
 * @author 安达
 * @since 2021-03-17
 */
@Data
@Accessors(chain = true)
@TableName("t_performance_task_allocation_temp")
public class TPerformanceTaskAllocationTemp extends Model<TPerformanceTaskAllocationTemp> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_X")
	private Integer idX;
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

	@TableField(exist = false)
	private List<TPerformanceTaskAllocationTemp> childrenList;


	@Override
	protected Serializable pkVal() {
		return this.idX;
	}

}

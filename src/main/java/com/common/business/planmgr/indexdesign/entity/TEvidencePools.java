package com.common.business.planmgr.indexdesign.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 佐证材料池
 * </p>
 *
 * @author 安达
 * @since 2021-03-20
 */
@Data
@Accessors(chain = true)
@TableName("t_evidence_pools")
public class TEvidencePools extends Model<TEvidencePools> {

    private static final long serialVersionUID = 1L;

	@TableId(value="ID_C", type= IdType.AUTO)
	private Integer idC;
	@TableField("ID_B")
	private Integer idB;

	/**
	 * 存储上传资料的信息 的id
	 */
	@TableField("ID_F")
	private Integer idF;
    /**
     * 资料一级分类
     */
	@TableField("INFO_TYPE1")
	private String infoType1;
    /**
     * 资料二级分类
     */
	@TableField("INFO_TYPE2")
	private String infoType2;
    /**
     * 资料名称
     */
	@TableField("INFO_NAME")
	private String infoName;
    /**
     * 文档存储路径
     */
	@TableField("FILE_PATH")
	private String filePath;
    /**
     * 文件大小
     */
	@TableField("FILE_SIZE")
	private String fileSize;

	/**
	 * 上传人
	 */
	@TableField("F_UPDATEOR")
	private String updateor;

	/**
	 * 不在数据库    佐证材料池子集合
	 */
	@TableField(exist = false)
	private List<TEvidencePools> children =new ArrayList<>();

	/**
	 *数据库不存在字段    0-未选择  1-已选择（默认）
	 */
	@TableField(exist = false)
	private String haveBeenChoose;

	@Override
	protected Serializable pkVal() {
		return this.idC;
	}

}

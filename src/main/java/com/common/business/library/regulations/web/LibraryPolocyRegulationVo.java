package com.common.business.library.regulations.web;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationUptAtta;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


/**
 * 接受前端传递的值，针对于保存、上传文件等
 */
@Data //注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
@AllArgsConstructor //注在类上，提供类的全参构造
@NoArgsConstructor //注在类上，提供类的无参构造
@Accessors(chain = true)
public class LibraryPolocyRegulationVo {

    //private Integer idA;//主子项目id主键值
   // private MultipartFile[] files;//上传的文件集合

    //private MultipartFile file;//一个政策法规上传的文件附件
    //private LibraryPolocyRegulation libraryPolocyRegulation;//政策法规文件对象
    //private Integer idC;//针对于 政策法规的来源是 “项目”的情况，选择的是一个资料文件，idC为该资料文件的id主键
    private Integer idX;
    /**
     * 政策法规名称
     */
    private String polocyName;


    /**
     * 文号
     */
    private String docNumber;
    /**
     * 发文单位
     */
    private String unitName;
    /**
     * 关键词
     */
    private String keyWords;
    /**
     * 行政地区
     */
    private String administrativeRegion;
    /**
     * 备注
     */
    private String remark;
    /**
     * 正文
     */
    private String content;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 存储路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 数据权限
     1-公开
     2-私有
     */
    private String dataRights;
    /**
     * 数据状态
     -1 退回
     0-暂存
     1-审批中
     2-已审批
     */
    private String dataStauts;
    /**
     * 申请人ID
     包含了
     出库申请人
     入库申请人
     更新申请人
     */
    private String applicantId;
    /**
     * 申请人姓名
     包含了
     出库申请人
     入库申请人
     更新申请人
     */
    private String applicantName;
    /**
     * 申请时间
     包含了
     出库申请时间
     入库申请时间
     更新申请时间
     */
    private Date applyTime;

    /**
     * 申请原因
     包含了
     出库申请原因
     入库申请原因
     更新申请原因
     */
    private String applyDesc;
    /**
     * 调整类型
     1-出库申请
     2-入库申请
     3-修改申请
     */
    private String uptType;
    /**
     * 当前审批人ID
     */
    private String currCheckId;
    /**
     * 当前审批人姓名
     */
    private String currCheckName;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 数据来源
     * 1-项目 2-非项目
     */
    private String dataSources;

    private LibraryPolocyRegulation libraryPolocyRegulation;

    private MultipartFile[] files;//批量上传文件

    /**
     * 一个政法 有多个附件
     */
    private List<TLibraryPolocyRegulationAtta> libraryPolocyRegulationAttas;

    /**
     * 一个政法 有多个修改的附件
     */
    private List<TLibraryPolocyRegulationUptAtta> libraryPolocyRegulationUptAttas;


}

package com.common.business.library.regulations.web;

import com.baomidou.mybatisplus.annotations.TableField;
import com.common.business.library.regulations.entity.LibraryPolocyRegulation;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationAtta;
import com.common.business.library.regulations.entity.TLibraryPolocyRegulationUptAtta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
public class TLibraryPolocyRegulationVo {




    /**
     *
     */
    private LibraryPolocyRegulation libraryPolocyRegulation;


    /**
     * 数据来源
     * 1-项目
     * 2-非项目
     */
    private String dataSources;

    /**
     * 针对于项目 传递资料主键值 中间用逗号分割
     */
    private String idCs;

    /**
     * 调整类型
     * 1-出库申请
     * 2-入库申请
     * 3-修改申请
     */
    private String uptType;



    /**
     * 上传的附件
     */
    private MultipartFile[] file;

    /**
     * 保留的政法附件
     */
    private List<TLibraryPolocyRegulationAtta> savePolocyRegulationAttas;

    /**
     * 保留的政法政法修改附件
     */
    private List<TLibraryPolocyRegulationUptAtta> savePolocyRegulationUptAttas;


    /**
     * 删除的政法附件
     */
    private List<TLibraryPolocyRegulationAtta> delPolocyRegulationAttas;

    /**
     * 删除的政法修改附件
     */
    private List<TLibraryPolocyRegulationUptAtta> delPolocyRegulationUptAttas;

    /**
     * 关闭时，前端已经上传的政法附件集合
     */
    private List<TLibraryPolocyRegulationAtta> uploadPolocyRegulationAttas;

    /**
     * 关闭时，前端已经上传的政法修改附件集合
     */
    private List<TLibraryPolocyRegulationUptAtta> uploadPolocyRegulationUptAttas;



   /* *//**
     * 前端还剩下的主键id，中间用逗号分割
     *//*
    private String saveIds;

    *//**
     * 前端删除的主键id，中间用逗号分割
     *//*
    private String deleteIds;*/

}

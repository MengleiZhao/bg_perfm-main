package com.common.business.collection.means.mapper;

import com.common.business.collection.means.entity.TInformations;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 存储上传资料的信息 Mapper 接口
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-16
 */
public interface TInformationsMapper extends BaseMapper<TInformations> {


/*================================================================================================================*/
/*===========资料收集上传    author:田鑫艳    [开始]================================================================ */
/*================================================================================================================*/

    /**
     * 1.存储上传资料的信息 Mapper 接口
     * 查询已经上传的资料的集合(前提：最新版本下的)
     * @param versionNO 最新版本
     * @param idR 项目资料清单关系表 的主键id值 与拟定表相关联
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 15:24
     * @updateTime 2021/3/17 15:24
     */
    List<TInformations> informances(@Param("idR") Integer idR, @Param("versionNO") String versionNO);
    /**
     * 2.存储上传资料的信息 Mapper 接口
     * 根据拟定列表的id值查询该模块下已经上传的资料
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/18 16:19
     * @updateTime 2021/3/18 16:19
     */
    List<TInformations> getModuleInformances(@Param("idB") Integer idB,@Param("userId") Integer userId);

    /**
     * 3.删除一个模块选中的资料信息
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 13:54
     * @updateTime 2021/3/19 13:54
     */
    void chooseClassDelete(@Param("deleteInformanceIds") List<String> deleteInformanceIds, @Param("idB") Integer idB);

    /**
     * 4.根据idB和idC在集合列表中，进行查询
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 14:31
     * @updateTime 2021/3/19 14:31
     */
    List<TInformations> selectInformations(@Param("informanceIdCs") List<String> informanceIdCs, @Param("idB") Integer idB);


    /**
     * 4.修改 资料中下载次数、预览次数，在原来的基础上加1
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/22 16:44
     * @updateTime 2021/3/22 16:44
     */
    void modifyAutoIncrement(@Param("autoIncrementColumn") String autoIncrementColumn, @Param("idC")Integer idC);


    /**
     * 5.根据 项目资料清单关系表的idR 查询 资料表中该idR下的上传的所有资料数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/23 15:45
     * @updateTime 2021/3/23 15:45
     */
    List<TInformations> selectInformationByIdR(Integer idR);

    /**
     * 1.资料收集审核
     * 根据idR查询该版本下是否有未认证的资料
     * @param  idR 拟定关系表的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 10:56
     * @updateTime 2021/3/24 10:56
     */
    Integer selectIsNoAgree(Integer idR);

    /**
     * 2.资料收集审核
     * 修改认证结果
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/29 15:07
     * @updateTime 2021/3/29 15:07
     */
    void updateAgreeFiles(@Param("information") TInformations information);

    /**
     * 根据资料拟定单的主键值查询该拟定单下上传的资料个数
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/12 11:33
     * @updateTime 2021/4/12 11:33
     */
    Integer queryInfoByIdB(@Param("idB") Integer idB);



    /*================================================================================================================*/
/*===========资料收集上传    author:田鑫艳    [结束]================================================================ */
/*================================================================================================================*/




}
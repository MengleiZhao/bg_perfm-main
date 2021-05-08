package com.common.business.collection.meanslist.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  资料清单拟定 Mapper 接口
 * </p>
 *
 * @author 陈睿超
 * @since 2021-03-16
 */
public interface TDevelopmentInformationListMapper extends BaseMapper<TDevelopmentInformationList> {
    /*================================================================================================================*/
    /*===========资料收集上传    author:田鑫艳    [开始]================================================================ */
    /*================================================================================================================*/


    /**
     * 1.得到该版本下需要上传的拟定清单（可以得到具体的资料收集人需要上传的拟定清单）
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/17 15:01
     * @updateTime 2021/3/17 15:01
     */
    List<TDevelopmentInformationList> informanceLists(@Param("idR") Integer idR,
                                                      @Param("versionNO") String versionNO,
                                                      @Param("userId")Integer userId);

    /**
     * 2.根据最新版号，和ID_R、当前登录人 查询所有拟定的资料清单关系表数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/18 16:45
     * @updateTime 2021/4/14 20:51
     */
    List<TDevelopmentInformationList> selectEndStatus(@Param("idR") Integer idR, @Param("versionNO") String versionNO,@Param("userId")Integer userId);

    /**
     * 3.根据主键id，修改一个字段的值
     * @param updateColumn 要修改的数据库中的字段
     * @param updateValue  被修改的字段的值
     * @param idB 主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 11:43
     * @updateTime 2021/3/19 11:43
     */
    void updateCoumnById(@Param("updateColumn") String updateColumn,@Param("updateValue")String updateValue,
                         @Param("idB") Integer idB,@Param("idA") Integer idA,
                         @Param("userId") Integer userId);

    /**
     * 4.根据子项目的idA查询该子项目的拟定清单
     * @param idR 拟定关系表中的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/23 21:50
     * @updateTime 2021/3/23 21:50
     */
    List<TDevelopmentInformationList> fileDetails(Integer idR);

}
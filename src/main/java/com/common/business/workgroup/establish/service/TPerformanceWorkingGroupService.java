package com.common.business.workgroup.establish.service;

import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.baomidou.mybatisplus.service.IService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 绩效工作组 服务类
 * </p>
 *
 * @author 安达
 * @since 2021-03-08
 */
public interface TPerformanceWorkingGroupService extends IService<TPerformanceWorkingGroup> {
    /**
     * 根据 idA 查询被评价商家
     * @param idA
     * @return
     * @throws Exception
     */
    public List<TEvalUnitInfo> selectList(Integer idA) throws Exception;

    /**
     * 根据 idA 查询外勤主管的用户id
     * @param idA
     * @return
     * @throws Exception
     */
    public String getFieldSupervisorIdByIdA(Integer idA) throws Exception;

    /**
     * 根据 idA 查询外勤主管工作组
     * @param idA
     * @return
     * @throws Exception
     */
    public TPerformanceWorkingGroup getFieldSupervisorByIdA(Integer idA) throws Exception;

    /**
     * 查询已经立项未组建,但是未组建工作组的项目
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo>  findToEstablishedPage(Integer pageNum,Integer pageSize,String search,TProPerformanceInfo bea,ShiroUser user) throws Exception;

    /**
     * 查询已组建工作组或者组件中的项目
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    PageInfo<TProPerformanceInfo> findHavingEstablishedPage(Integer pageNum,Integer pageSize,String search,TProPerformanceInfo bean,ShiroUser user) throws Exception;

    /**
     * 根据项目idA项目信息
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @return
     * @throws Exception
     */
    TProPerformanceInfo getTProPerformanceInfoByIdA(Integer idA) throws Exception;

    /**
     * 根据主项目编号查询子项目集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param parentProCode
     * @return
     * @throws Exception
     */
    List<TProPerformanceInfo> findSonListByParentProCode(String parentProCode)throws Exception;
    /**
     * 根据主项目编号查询子项目和子项目工作组集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param parentProCode
     * @return
     * @throws Exception
     */
    List<TProPerformanceInfo> findSonListAndGroupsByParentProCode(String parentProCode)throws Exception;

    /**
     * 根据项目idA查询工作组集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @throws Exception
     */
    List<TPerformanceWorkingGroup> findTPerformanceWorkingGroupListByIdA(Integer idA)throws Exception;

    /**
     * 查询人员集合（选择秘书需要）
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @throws Exception
     */
    List<RcUser> findSecretaryList(RcUser bean)throws Exception;


    /**
     * 查询还没被组建过的工作组成员集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @throws Exception
     */
    List<RcUser> findToEstablishedWorkingGroupList(RcUser bean,Integer idA,String groupMemberCodes,List<TPerformanceWorkingGroup> chooseList)throws Exception;


    /**
     * 选中人员信息,跟老数据匹配且返回给前端
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param userList
     * @param idA
     * @throws Exception
     */
    List<TPerformanceWorkingGroup> findTotalEstablishedWorkingGroupList(List<RcUser> userList,Integer idA,String groupMemberCodes,List<TPerformanceWorkingGroup> chooseList)throws Exception;


    /**
     * 组建工作组保存
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param isSetupWorkingGroup
     * @param proSecretaryId
     * @param proSecretaryName
     * @param proPerformanceInfoList
     * @throws Exception
     */
    void saveWorkingGroup (String isSetupWorkingGroup, Integer  proSecretaryId, String  proSecretaryName, List<TProPerformanceInfo> proPerformanceInfoList, ShiroUser user)throws Exception;

    /**
     * @Title: 
     * @Description: 根据项目id和用户账号状态查询工作组成员（目前有多处调用该方法，修改时请注意）
     * @author: 陈睿超
     * @createDate: 2021/4/14 10:18
     * @updater: 陈睿超
     * @updateDate: 2021/4/14 10:18
     * @param bean:项目信息
     * @param status:sql中用in连接，传入需要的值。状态（0:管理员，1：工作组成员）
     * @return 
     **/
    PageInfo<TPerformanceWorkingGroup> findWorkGroupByStatus(Integer pageNum, Integer pageSize,String search,TPerformanceWorkingGroup bean,String status);

    
}

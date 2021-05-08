package com.common.business.workgroup.establish.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.project.approval.entity.TEvalUnitInfo;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TEvalUnitInfoMapper;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.business.workgroup.establish.entity.TPerformanceWorkingGroup;
import com.common.business.workgroup.establish.mapper.TPerformanceWorkingGroupMapper;
import com.common.business.workgroup.establish.service.TPerformanceWorkingGroupService;
import com.common.system.shiro.ShiroUser;
import com.common.system.sys.entity.RcUser;
import com.common.system.sys.mapper.RcUserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 绩效工作组 服务实现类
 * </p>
 *
 * @author 安达
 * @since 2021-03-08
 */
@Service
@Transactional
public class TPerformanceWorkingGroupServiceImpl extends ServiceImpl<TPerformanceWorkingGroupMapper, TPerformanceWorkingGroup> implements TPerformanceWorkingGroupService {
    @Autowired
    private TEvalUnitInfoMapper tEvalUnitInfoMapper;
    @Autowired
    private TProPerformanceInfoMapper tProPerformanceInfoMapper;
    @Autowired
    private TPerformanceWorkingGroupMapper tPerformanceWorkingGroupMapper;
    @Autowired
    private RcUserMapper userMapper;

    /**
     * 查询已经立项,但是未组建工作组的项目
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo>  findToEstablishedPage(Integer pageNum,Integer pageSize,String search,TProPerformanceInfo bean,ShiroUser user) throws Exception {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //查询已经立项,但是未组建工作组的项目
        //项目经理id
        bean.setProManagerId(user.getId().toString());
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.findToEstablishedList(bean,search);
        return new PageInfo<TProPerformanceInfo>(list);
    }
    /**
     * 查询已组建工作组或者组件中的项目
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<TProPerformanceInfo> findHavingEstablishedPage(Integer pageNum,Integer pageSize,String search,TProPerformanceInfo bean,ShiroUser user) throws Exception {
        //设置分页   pageNum 当前页      pageSize 每页多少条
        PageHelper.startPage(pageNum, pageSize);
        //查询已组建工作组的项目
        //项目经理id
        bean.setProManagerId(user.getId().toString());
        List<TProPerformanceInfo> list= tProPerformanceInfoMapper.findHavingEstablishedList(bean,search);
        return new PageInfo<TProPerformanceInfo>(list);

    }


    /**
     * 根据集合获取 ida-创建时间 map
     * @param workingGroupList
     * @return
     */
    private Map<Integer, Date> getMapByList(List<TPerformanceWorkingGroup> workingGroupList){
        Map<Integer, Date> map=new HashMap<>();
        for(TPerformanceWorkingGroup workingGroup:workingGroupList){
            map.put(workingGroup.getIdA(),workingGroup.getCreateTime());
        }
        return map;
    }

    /**
     * 根据 idA 查询外勤主管的用户id
     * @param idA
     * @return
     * @throws Exception
     */
    @Override
    public String getFieldSupervisorIdByIdA(Integer idA) throws Exception {
        //创建外勤主管
        EntityWrapper fieldSupervisorEntityWrapper = new EntityWrapper();
        fieldSupervisorEntityWrapper.eq("ID_A",idA);
        fieldSupervisorEntityWrapper.eq("IS_WORK_CHARGE","Y");
        List<TPerformanceWorkingGroup> fieldSupervisorList = tPerformanceWorkingGroupMapper.selectList(fieldSupervisorEntityWrapper);
        return fieldSupervisorList.get(0).getGroupMemberId();
    }
    /**
     * 根据 idA 查询外勤主管工作组
     * @param idA
     * @return
     * @throws Exception
     */
    @Override
    public TPerformanceWorkingGroup getFieldSupervisorByIdA(Integer idA) throws Exception {
        //创建外勤主管
        EntityWrapper fieldSupervisorEntityWrapper = new EntityWrapper();
        fieldSupervisorEntityWrapper.eq("ID_A",idA);
        fieldSupervisorEntityWrapper.eq("IS_WORK_CHARGE","Y");
        List<TPerformanceWorkingGroup> fieldSupervisorList = tPerformanceWorkingGroupMapper.selectList(fieldSupervisorEntityWrapper);
        return fieldSupervisorList.get(0);
    }

    /**
     * 根据 idA 查询被评价商家
     * @param idA
     * @return
     * @throws Exception
     */
    @Override
    public List<TEvalUnitInfo> selectList(Integer idA) throws Exception {

        TEvalUnitInfo bean=new TEvalUnitInfo();
        EntityWrapper<TEvalUnitInfo> entity=new EntityWrapper<TEvalUnitInfo>();
        //根据idA查询
        entity.eq("ID_A",idA);
        List<TEvalUnitInfo> list=tEvalUnitInfoMapper.selectList(entity);
        return list;
    }

    /**
     * 根据项目idA项目信息
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @return
     * @throws Exception
     */
    @Override
    public TProPerformanceInfo getTProPerformanceInfoByIdA(Integer idA) throws Exception {
        return tProPerformanceInfoMapper.selectById(idA);
    }
    /**
     * 根据主项目编号查询子项目集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param parentProCode
     * @return
     * @throws Exception
     */
    @Override
    public List<TProPerformanceInfo> findSonListByParentProCode(String parentProCode) throws Exception {
        TProPerformanceInfo bean=new TProPerformanceInfo();
        EntityWrapper<TProPerformanceInfo> entity=new EntityWrapper<TProPerformanceInfo>();
        //根据parentProCode查询
        entity.eq("PARENT_PRO_CODE",parentProCode);
        //已经立项，未删除
        entity.eq("PRO_STATUS","1");
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.selectList(entity);
        return list;
    }
    /**
     * 根据主项目编号查询子项目和子项目工作组集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param parentProCode
     * @return
     * @throws Exception
     */
    @Override
    public List<TProPerformanceInfo> findSonListAndGroupsByParentProCode(String parentProCode) throws Exception {
        TProPerformanceInfo bean=new TProPerformanceInfo();
        EntityWrapper<TProPerformanceInfo> entity=new EntityWrapper<TProPerformanceInfo>();
        //根据parentProCode查询
        entity.eq("PARENT_PRO_CODE",parentProCode);
        //已经立项，未删除
        entity.eq("PRO_STATUS","1");
        List<TProPerformanceInfo> list=tProPerformanceInfoMapper.selectList(entity);
        if(list !=null && list.size()>0){
            for(TProPerformanceInfo info:list){
                //根据idA获取工作组集合
                List<TPerformanceWorkingGroup> workingGroupList=findTPerformanceWorkingGroupListByIdA(info.getIdA());
                info.setPerformanceWorkingGroupList(workingGroupList);
            }
        }
        return list;
    }
    /**
     * 根据项目idA查询工作组集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param idA
     * @throws Exception
     */
    @Override
    public List<TPerformanceWorkingGroup> findTPerformanceWorkingGroupListByIdA(Integer idA) throws Exception {

        TPerformanceWorkingGroup bean=new TPerformanceWorkingGroup();
        EntityWrapper<TPerformanceWorkingGroup> entity=new EntityWrapper<TPerformanceWorkingGroup>();
        //根据idA查询
        entity.eq("ID_A",idA);
        List<TPerformanceWorkingGroup> list=tPerformanceWorkingGroupMapper.selectList(entity);
        if(list !=null && list.size()>0){
            return list;
        }else{
            return new ArrayList<TPerformanceWorkingGroup>() ;
        }
    }


    /**
     *  查询人员集合（选择秘书需要）
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @throws Exception
     */
    @Override
    public List<RcUser> findSecretaryList(RcUser bean) throws Exception {

        EntityWrapper<RcUser> entity=new EntityWrapper<RcUser>();
        //状态为1的，排除管理员和专家
        entity.eq("status",1);
        if(bean !=null){
            //组员姓名
            if(StringUtils.isNotEmpty(bean.getGroupMemberName())){
                entity.like("GROUP_MEMBER_NAME",bean.getGroupMemberName());
            }
            //员工编号
            if(StringUtils.isNotEmpty(bean.getGroupMemberCode())){
                entity.eq("GROUP_MEMBER_CODE",bean.getGroupMemberCode());
            }
            //所在分所ID
            if(StringUtils.isNotEmpty(bean.getBranchOfficeId())){
                entity.eq("BRANCH_OFFICE_ID",bean.getBranchOfficeId());
            }
        }


        List<RcUser> list=userMapper.selectList(entity);
        return list;
    }
    /**
     * 查询还没被组建过的工作组成员集合
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param bean
     * @throws Exception
     */
    @Override
    public List<RcUser> findToEstablishedWorkingGroupList(RcUser bean,Integer idA,String groupMemberCodes,List<TPerformanceWorkingGroup> chooseList) throws Exception {

        //把被删除的人员编号转换成map
        Map<String,Integer> deleteCodeMap= getDeleteCodeMap(groupMemberCodes);
        //根据项目idA查询工作组集合
        List<TPerformanceWorkingGroup> groupList=findTPerformanceWorkingGroupListByIdA(idA);
        if(chooseList !=null && chooseList.size()>0){
            //如果页面传过来数据，就获取页面传过来的（新增的时候用到）
            groupList=chooseList;
        }
        //定义页面排除人员编码list
        List<String> excludeCodeList=new ArrayList<>();
        if(groupList !=null && groupList.size()>0){
            for(TPerformanceWorkingGroup oldGroup:groupList){
                //获得被删除的idA
                Integer deleteIdA=deleteCodeMap.get(oldGroup.getGroupMemberCode());
                if(deleteIdA !=null && deleteIdA.equals(oldGroup.getIdA())){
                    //如果 这个子项目idA的人员编码被前端列表删了，就跳过，不再存入集合
                    continue;
                }
                excludeCodeList.add(oldGroup.getGroupMemberCode());
            }
        }

        EntityWrapper<RcUser> entity=new EntityWrapper<RcUser>();
        //状态为1的，排除管理员
        entity.eq("status",1);
        //排除显示人员
        entity.notIn("GROUP_MEMBER_CODE",excludeCodeList);
        //组员姓名
        if(bean !=null && StringUtils.isNotEmpty(bean.getGroupMemberName())){
            entity.like("GROUP_MEMBER_NAME",bean.getGroupMemberName());
        }
        //员工编号
        if(bean !=null && StringUtils.isNotEmpty(bean.getGroupMemberCode())){
            entity.eq("GROUP_MEMBER_CODE",bean.getGroupMemberCode());
        }
        //所在分所ID
        if(bean !=null && StringUtils.isNotEmpty(bean.getBranchOfficeId())){
            entity.eq("BRANCH_OFFICE_ID",bean.getBranchOfficeId());
        }

        List<RcUser> list=userMapper.selectList(entity);
        return list;
    }
    /**
     * 选中人员信息,跟老数据匹配且返回给前端
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param userList
     * @param idA
     * @throws Exception
     */
    @Override
    public List<TPerformanceWorkingGroup> findTotalEstablishedWorkingGroupList(List<RcUser> userList,Integer idA,String groupMemberCodes,List<TPerformanceWorkingGroup> chooseList)throws Exception{
        List<TPerformanceWorkingGroup> returnList=new ArrayList<>();
        //根据项目idA查询工作组集合
        List<TPerformanceWorkingGroup> groupList=findTPerformanceWorkingGroupListByIdA(idA);
        if(chooseList !=null && chooseList.size()>0){
            //如果页面传过来数据，就获取页面传过来的（新增的时候用到）
            groupList=chooseList;
        }
        //把工作组集合转换成map<groupMemberCode,bean>
        Map<String,TPerformanceWorkingGroup> groupMap=new HashMap<>();
        //把被删除的人员编号转换成map
        Map<String,Integer> deleteCodeMap= getDeleteCodeMap(groupMemberCodes);
        for(TPerformanceWorkingGroup oldGroup:groupList){
            //获得被删除的idA
            Integer deleteIdA=deleteCodeMap.get(oldGroup.getGroupMemberCode());
            if(deleteIdA !=null && deleteIdA.equals(oldGroup.getIdA())){
                //如果 这个子项目idA的人员编码被前端列表删了，就跳过，不再存入集合
                continue;
            }
            //把老数据存入集合
            returnList.add(oldGroup);
            //存入map，下面循环的时候去重复
            groupMap.put(oldGroup.getGroupMemberCode(),oldGroup);
        }
        for(RcUser user:userList){
            //获取老的数据
            TPerformanceWorkingGroup oldGroup=groupMap.get(user.getGroupMemberCode());
            if(oldGroup ==null){
                //如果新选人员跟老数据不重复，新建组员用户且存入集合
                TPerformanceWorkingGroup group=rcUserToGroupUser(user);
                returnList.add(group);
            }
        }
        return returnList;
    }

    /**
     * 获得被删除的用户编码map
     * @param groupMemberCodes
     * @return
     */
    private Map<String,Integer> getDeleteCodeMap(String groupMemberCodes){
        // map<code,idA>
        Map<String,Integer> map=new HashMap<>();
        if(StringUtils.isNotEmpty(groupMemberCodes)){
            // 解析逗号
            String[] idACodes=groupMemberCodes.split(",");
            for(String idACode:idACodes) {
                // 解析竖线   map<code,idA>
                map.put(idACode.split("\\|")[1],Integer.parseInt(idACode.split("\\|")[0]));
            }
        }
        return map;
    }

    /**
     * 把 RcUser 对象转换为 TPerformanceWorkingGroup对象
     * @param user
     * @return
     */
    private TPerformanceWorkingGroup rcUserToGroupUser(RcUser user) {
        TPerformanceWorkingGroup group=new TPerformanceWorkingGroup();
        //项目组员ID
        group.setGroupMemberId(user.getId()+"");
        //组员姓名
        group.setGroupMemberName(user.getGroupMemberName());
        //员工编号
        group.setGroupMemberCode(user.getGroupMemberCode());
        //所在分所ID
        group.setBranchOfficeId(user.getBranchOfficeId());
        //所在分所
        group.setBranchOfficeName(user.getBranchOfficeName());
        //所在部门ID
        group.setDeptId(user.getDeptId());
        //所在部门
        group.setDeptName(user.getDeptName());
        //人员级别
        group.setUserLeavel(user.getUserLeavel());
        //毕业院校
        group.setGraduatedFrom(user.getGraduatedFrom());
        //学历
        group.setEducation(user.getEducation());
        //专业
        group.setMajor(user.getMajor());
        //是否有主评人资格
        group.setIsQualifiedMainReviewer(user.getIsQualifiedMainReviewer());
        //项目类型
        group.setProjectType(user.getProjectType());
        //曾担任项目角色
        group.setPreviousProjectRole(user.getPreviousProjectRole());
        //年限/经验
        group.setYearsExperience(user.getYearsExperience());
        //备注
        group.setRemark(user.getRemark());
        //是否外勤主管,Y-是  页面指定后变更（逻辑：一个工作组只允许一名外勤主管）  N-否（默认）
        group.setIsWorkCharge("N");
        return group;
    }

    /*
     * 组建工作组保存
     * @author:安达
     * @date:2021年3月9日 9：51
     * @param isSetupWorkingGroup
     * @param proSecretaryId
     * @param proSecretaryName
     * @param proPerformanceInfoList
     * @throws Exception
     */
    @Override
    public void saveWorkingGroup(String isSetupWorkingGroup,Integer  proSecretaryId,String  proSecretaryName, List<TProPerformanceInfo> proPerformanceInfoList, ShiroUser user) throws Exception {
        //循环集合
        for(TProPerformanceInfo info:proPerformanceInfoList){
            //是否已组建工作组   Y-是，组建工作组完成后更新为Y  Z暂存 N-否（默认）
            info.setIsSetupWorkingGroup(isSetupWorkingGroup);
            //目秘书ID
            info.setProSecretaryId(proSecretaryId+"");
            //项目秘书
            info.setProSecretaryName(proSecretaryName);
            //是否已分配任务  Y-分配完毕，组建工作组完成后更新为Y Z-分配中，如果是暂存，则更新未Z N-否（默认）
            info.setIsTaskAssigned("N");
            //修改时间
            info.setUpdateTime(new Date());
            //修改人
            info.setUpdater(user.getName());
            //定义删除的实体
            EntityWrapper<TPerformanceWorkingGroup> deleteEntity=new EntityWrapper<TPerformanceWorkingGroup>();
            //根据idA查询
            deleteEntity.eq("ID_A",info.getIdA());
            //删除老工作组
            super.delete(deleteEntity);
            //前端返回来的工作组
            List<TPerformanceWorkingGroup> workingGroupList=info.getPerformanceWorkingGroupList();
            for(TPerformanceWorkingGroup workingGroup:workingGroupList){
                workingGroup.setIdA(info.getIdA());
                if(workingGroup.getIdB()==null){
                    //创建人
                    workingGroup.setCreateor(user.getName());
                    //创建时间
                    workingGroup.setCreateTime(new Date());
                }
                //修改人
                workingGroup.setUpdateor(user.getName());
                //修改时间
                workingGroup.setUpdateTime(new Date());
                //插入或者修改
                super.insertOrUpdate(workingGroup);
            }
            tProPerformanceInfoMapper.updateById(info);
        }
    }
    
    @Override
    public PageInfo<TPerformanceWorkingGroup> findWorkGroupByStatus(Integer pageNum, Integer pageSize, String search, TPerformanceWorkingGroup bean, String status) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<TPerformanceWorkingGroup> list = baseMapper.findWorkGroupByStatus(pageNum,pageSize,search,bean.getIdA(),status);
        return new PageInfo<TPerformanceWorkingGroup>(list);
    }


}

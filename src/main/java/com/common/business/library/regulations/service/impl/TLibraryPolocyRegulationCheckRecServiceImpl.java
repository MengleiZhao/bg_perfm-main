package com.common.business.library.regulations.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.common.business.library.regulations.entity.*;
import com.common.business.library.regulations.mapper.*;
import com.common.business.library.regulations.service.TLibraryPolocyRegulationCheckRecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.FileUpLoadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 政策法规库审批表
入库、出库、查阅 审批记录表 服务实现类
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-26
 */
@Service
public class TLibraryPolocyRegulationCheckRecServiceImpl extends ServiceImpl<TLibraryPolocyRegulationCheckRecMapper, LibraryPolocyRegulationCheckRec> implements TLibraryPolocyRegulationCheckRecService {

    @Autowired
    private TLibraryPolocyRegulationCheckRecMapper libraryPolocyRegulationCheckRecMapper;//政策法规审核 mapper
    @Autowired
    private TLibraryPolocyRegulationMapper libraryPolocyRegulationMapper;//政策法规 mapper
    @Autowired
    private BussinessFlowLibraryPolocyRegulationMapper flowLibraryPolocyRegulationMapper;//政法审批流业务 mapper
    @Autowired
    private TLibraryPolocyRegulationCheckAttaMapper libraryPolocyRegulationCheckAttaMapper ;//审核附件 mapper
    @Autowired
    private TLibraryPolocyRegulationUptMapper libraryPolocyRegulationUptMapper;//政策法规修改表 mapper
    @Autowired
    private TLibraryPolocyRegulationAttaMapper libraryPolocyRegulationAttaMapper;//政法附件 mapper
    @Autowired
    private TLibraryPolocyRegulationUptAttaMapper libraryPolocyRegulationUptAttaMapper;//政法修改附件 mapper

    /**
     * 1.政策法规审核：主页面显示
     * 约束：入库、修改、出库 已经提交，并且政法表中的当前申请人是当前登录人，且上个人的活跃状态为活跃,并且已完成。
     * @param current                  开始查询的页码数 默认为第1页
     * @param size                     每页的大小  默认每页显示10条数据
     * @param libraryPolocyRegulation  精确查询封装字段
     * @param search                    综合查询字段
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 16:05
     * @updateTime 2021/3/31 16:05
     */
    @Override
    public PageInfo<LibraryPolocyRegulation> policyCheckPage(Integer current, Integer size, LibraryPolocyRegulation libraryPolocyRegulation, String search, String userId) throws Exception{
        PageHelper.startPage(current,size);
        //约束：入库、修改、出库 已经提交，并且政法表中的当前申请人是当前登录人
        List<LibraryPolocyRegulation> libraryPolocyRegulations=libraryPolocyRegulationMapper.policyCheckPage(libraryPolocyRegulation,search,userId);
        //2）判断查询的结果中，是否有“调整状态为3-修改,状态为 1-审批中”的政法数据
        for(LibraryPolocyRegulation policy:libraryPolocyRegulations){
            //如果返回的数据中，当前审批人不是当前登录人，则，该政策法规的审批状态在当前登录人这里是已审批
            if(policy.getCurrCheckId()==null || (policy.getCurrCheckId()!=null &&(!String.valueOf(userId).equals(policy.getCurrCheckId())))){
                policy.setStatus("1");
            }else{
                policy.setStatus("0");
            }


            //设置时间
            //去掉时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            policy.setReleaseDate(sdf.format(policy.getReleaseTime()).substring(0,sdf.format(policy.getReleaseTime()).lastIndexOf("")));

            //设置行政地区
            //admRegionProvince  admRegionCity  admRegionCounty
            //设置 行政地区
            String temp="";
            if(StringUtils.isNotEmpty(policy.getAdmRegionProvince())){
                temp=temp+policy.getAdmRegionProvince()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCity())){
                temp=temp+policy.getAdmRegionCity()+"-";
            }
            if(StringUtils.isNotEmpty(policy.getAdmRegionCounty())){
                temp=temp+policy.getAdmRegionCounty();
            }
            policy.setAdministrativeRegion(temp);


            //如果是修改
            if("3".equals(policy.getUptType())){
                //3）有==》查询政法修改表中的数据，替换掉原本的数据
                if("1".equals(policy.getDataStauts())){
                    TLibraryPolocyRegulationUpt policyUpt=libraryPolocyRegulationUptMapper.queryByIdX(policy.getIdX());
                    //设置返回对象
                    if(policyUpt!=null){
                        policy.setPolocyName(policyUpt.getPolocyName());
                        policy.setDocNumber(policyUpt.getDocNumber());
                        policy.setKeyWords(policyUpt.getKeyWords());
                        policy.setUnitName(policyUpt.getUnitName());
                        policy.setAdministrativeRegion(policyUpt.getAdministrativeRegion());
                        policy.setRemark(policyUpt.getRemark());
                        policy.setContent(policyUpt.getContent());
                    }

                }
            }
        }
        //(上个人的活跃状态为活跃,并且已完成。）======》此判断，，貌似多此一举，先注释掉，报错的话，再弄
        //根据当前用户id 判断该用户的上一级审批人是否活跃，且已完成。如果是，则表示 审批环节进入到当前审批人这里了
       /* BussinessFlowLibraryPolocyRegulation flow=flowLibraryPolocyRegulationMapper.previousLevelStatus(userId);
        String nodeIsActive=flow.getNodeIsActive();//是否活跃 0-不活跃  1-活跃
        String currentNodeStatus=flow.getCurrentNodeStatus();//完成状态 0-未开始  1-已完成
        List<TLibraryPolocyRegulation> lastPolicyRegulations=new ArrayList<>();//最终要返回的值
        if("1".equals(nodeIsActive) && "1".equals(currentNodeStatus)){
            lastPolicyRegulations.addAll(libraryPolocyRegulations);
        }*/

        return new PageInfo<>(libraryPolocyRegulations);
    }

    /**
     * 2.选择一个法规进行审核 查看该法规的审批流相关数据
     * 返回的数据：
     *      1）该法规详情（调用政策法规 控制层中的updatePolicyDetail接口：修改(一条法规)申请 显示原有的数据）
     *      2）审批流数据：
     *          1.申请人：姓名、编号、完成
     *          2.审批记录
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 11:12
     * @updateTime 2021/4/2 11:12
     */
    @Override
    public LibraryPolocyRegulation queryFlowCheck(Integer idX) throws Exception{
        LibraryPolocyRegulation libraryPolocyRegulation=new LibraryPolocyRegulation();
        libraryPolocyRegulation.setIdX(idX);
        //1.查询审批记录
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("ID_X",idX);
        List<LibraryPolocyRegulationCheckRec> policyCheck=libraryPolocyRegulationCheckRecMapper.selectList(entityWrapper);
        libraryPolocyRegulation.setLibraryPolocyRegulationCheckRecs(policyCheck);
        //2.查询业务流程
        List<BussinessFlowLibraryPolocyRegulation> flowLibraryPolocyRegulations=flowLibraryPolocyRegulationMapper.queryAllFlowsByIdX(idX);
        libraryPolocyRegulation.setBussinessFlowLibraryPolocyRegulations(flowLibraryPolocyRegulations);
        return libraryPolocyRegulation;
    }


    /**
     * 3.插入数据至审批附件表
     *  整体思路：
     *      1）设置审批附件对象值
     *      2）插入数据
     * @param policyCheckAtta 要插入的数据
     * @param user            当前登录人
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 14:07
     * @updateTime 2021/4/4 11:15
     */
    @Override
    @Transactional
    public void insertCheckAtta(TLibraryPolocyRegulationCheckAtta policyCheckAtta, ShiroUser user) throws Exception{
        //1.设置审批附件对象值
        //上传人ID
        if(null==policyCheckAtta.getCreateor()){
            policyCheckAtta.setCreateorId(String.valueOf(user.getId()));
        }
        //上传人姓名
        if(null==policyCheckAtta.getCreateor()){
            policyCheckAtta.setCreateor(user.getName());
        }
        //上传时间
        if(null==policyCheckAtta.getCreateTime()){
            policyCheckAtta.setCreateTime(new Date());
        }
        //2.插入数据
        libraryPolocyRegulationCheckAttaMapper.insert(policyCheckAtta);

    }

    /**
     * 4.审批 新增数据至审批表里（有没有附件上传都需要这个操作）、修改业务表数据
     * 整体思路：
     *      1) 设置审批对象值：调用工具方法 Tool:1.(installCheckObject)设置审批对象值
     *      2）新增审批表数据（并返回主键）
     *      3）修改政法表、业务表：调用工具方法 Tool:2.(updatePolicyByCheck)根据审批结果修改政法表、修改业务表
     * @param
     * @return Integer 新增审批表的主键id值
     * @author 田鑫艳
     * @createTime 2021/4/2 14:38
     * @updateTime 2021/4/2 14:38
     */
    @Override
    @Transactional
    public Integer checkPolicy(LibraryPolocyRegulationCheckRec libraryPolocyRegulationCheckRec, ShiroUser user) throws Exception{

        //1.设置审批对象值：调用工具方法 Tool:1.(installCheckObject)设置审批对象值
        libraryPolocyRegulationCheckRec=installCheckObject(libraryPolocyRegulationCheckRec,user);

        //2.新增审批表数据（并返回主键）
        libraryPolocyRegulationCheckRecMapper.insertCheckBackKey(libraryPolocyRegulationCheckRec);

        //3.修改政法表、业务表：调用工具方法 Tool:2.(updatePolicyByCheck)根据审批结果修改政法表、修改业务表
        updatePolicyByCheck(libraryPolocyRegulationCheckRec.getCheckResult(),user.getId(),libraryPolocyRegulationCheckRec.getIdX());

        return libraryPolocyRegulationCheckRec.getIdB();
    }


    /**
     * Tool:1.(installCheckObject)设置审批对象值
     *  整体思路：
     *      1) 得到该审批人的上一批次的审批记录
     *      2）上一批次的审批记录有值，修改上一批次的审批记录中的“审批数据状态”为 0-历史记录
     *      3）设置该审批人的本次记录的“审批数据状态”为 1-本次记录
     *      4）设置当前记录的审批节点：根据当前审批人的主键id值从业务表中得到审批节点
     *      5）设置该记录的其他数据
     * @param
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 15:41
     * @updateTime 2021/4/2 15:41
     */
    @Transactional
    public LibraryPolocyRegulationCheckRec installCheckObject(LibraryPolocyRegulationCheckRec libraryPolocyRegulationCheckRec, ShiroUser user) {

        try {
            //1.根据政法表的主键值，得到当前审批人的之前的上一批审批数据:有，则将原来的 状态 改为0；将现在的设置为1
            List<Integer> oldNowChecks=libraryPolocyRegulationCheckRecMapper.selectCheckDataStatus(libraryPolocyRegulationCheckRec.getIdX(),user.getId());

            //2.上一批次的审批记录有值，修改上一批次的审批记录中的“审批数据状态”为 0-历史记录
            if(oldNowChecks.size()>0){
                libraryPolocyRegulationCheckRecMapper.updateLastBatch(oldNowChecks);
            }

            //3.设置该审批人的本次记录的“审批数据状态”为 1-本次记录
            libraryPolocyRegulationCheckRec.setCheckDataStatus("1");

            //4.设置当前记录的审批节点：根据当前审批人的主键id值从业务表中得到审批节点
            Integer orderOfCurrentNode=flowLibraryPolocyRegulationMapper.selectOrderOfCurrentNode(user.getId(),libraryPolocyRegulationCheckRec.getIdX(),user.getDeptId());
            libraryPolocyRegulationCheckRec.setOrderOfCurrentNode(orderOfCurrentNode);

            //5.设置该记录的其他数据
            //5-1.审批人id
            if(null==libraryPolocyRegulationCheckRec.getCheckUserId()){
                libraryPolocyRegulationCheckRec.setCheckUserId(String.valueOf(user.getId()));
            }
            //5-2.审批人姓名
            if(null==libraryPolocyRegulationCheckRec.getCheckUser()){
                libraryPolocyRegulationCheckRec.setCheckUser(user.getName());
            }
            //5-3.审批时间
            if(null==libraryPolocyRegulationCheckRec.getCheckTime()){
                libraryPolocyRegulationCheckRec.setCheckTime(new Date());
            }

            return libraryPolocyRegulationCheckRec;
        } catch (Exception e) {
            System.out.println("TLibraryPolocyRegulationCheckRecServiceImpl 中 Tool:1.(installCheckObject)设置审批对象值 出错：");
        e.printStackTrace();
        return null;
    }
}

    /**
     * Tool:Tool:2.(updatePolicyByCheck)根据审批结果修改政法表、修改业务表
     * 整体思路：
     *      1）准备工作：
     *          1-1.得到该政法的所有审批业务信息
     *          1-2.得到该政法的当前审批节点信息
     *          1-3.得到该政法的下级审批节点信息
     *          1-4.得到该政法的发起人节点信息
     *      2）有没有通过都得修改业务表：当前节点：0-不活跃,1-已完成,修改人和修改时间为当前登录人
     *      3）通过 1
     *          3-1.当前审批人是最后一个审批人：
     *              3-1.1.根据idX得到该政法数据
     *              3-1.2.判断该法规是否是修改申请，如果是，则将政策法规修改表中的数据替换政策法规表中的数据
     *                  3-1.2.1.查询修改表中的数据
     *                  3-1.2.2.调用工具方法，Tool:3.(replacePolicy)对于修改申请审核通过的，替换原来的数据
     *                  3-1.2.3.删除修改表中的数据
     *              3-1.3.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为null、数据状态（DATA_STAUTS）为 2:已审批
     *          3-2.当前审批人不是最后一个审批人：
     *              3-2.1.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为下级审批人的数据
     *              3-2.2.修改业务表的下级节点：1-活跃,0-未完成,修改人和修改时间为当前登录人
     *      4）不通过 0
     *          4-1.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为null、数据状态（DATA_STAUTS）为 -1:已退回
     *          4-2.修改业务表 的发起人 1-活跃,0-未完成,修改人和修改时间为当前登录人
     * @param result 审批结果：1-通过（默认）0-不通过
     * @param userId 当前审批人主键id值
     * @param idX    审批的政策法规主键值
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/2 16:08
     * @updateTime 2021/4/2 16:08
     */
    @Transactional
    public void updatePolicyByCheck(String result,Integer userId,Integer idX){
        try {
            //1.准备工作：
            //1-1.得到该政法的所有审批业务信息
            List<BussinessFlowLibraryPolocyRegulation> allFlows=flowLibraryPolocyRegulationMapper.queryAllFlowsByIdX(idX);
            Map<Integer,BussinessFlowLibraryPolocyRegulation> allFlowsMap=new HashMap<>();
            //用于存放 当前节点业务审批信息
            BussinessFlowLibraryPolocyRegulation nowFlow=new BussinessFlowLibraryPolocyRegulation();
            //用于存放，当前的节点顺序值（发起人1、一级审批人2、二级审批人3）
            Integer nowNode=null;
            //1-1.得到该政法的所有审批业务信息
            for(BussinessFlowLibraryPolocyRegulation flow:allFlows){
                allFlowsMap.put(flow.getOrderOfCurrentNode(),flow);
                //根据当前登录用户的id值和活跃状态，找到当前的节点顺序值（1、2、3……）
                if((String.valueOf(userId).equals(flow.getCheckUserId())) && ("1".equals(flow.getNodeIsActive()))){
                    nowNode=flow.getOrderOfCurrentNode();//当前节点顺序值
                    //1-2.得到该政法的当前审批节点信息
                    nowFlow=flow;
                }
            }
            //1-3.得到该政法的下级审批节点信息
            BussinessFlowLibraryPolocyRegulation nextFlow=allFlowsMap.get(nowNode+1);
            //1-4.得到该政法的发起人节点信息
            BussinessFlowLibraryPolocyRegulation firstFlow=allFlowsMap.get(0);


            //2）有没有通过都得修改业务表：当前节点：0-不活跃,1-已完成,修改人和修改时间为当前登录人
            nowFlow.setNodeIsActive("0");//活跃状态为 0-不活跃
            nowFlow.setCurrentNodeStatus("1");//节点状态为1-已完成
            nowFlow.setUpdateor(nowFlow.getCheckUser());//修改人
            nowFlow.setUpdateTime(new Date());//修改时间
            flowLibraryPolocyRegulationMapper.updateFlow(nowFlow,null);


            //3.通过 1
            if("1".equals(result)){
                //3-1.当前审批人是最后一个审批人：(即下个审批人的数据为空)
                if(null==nextFlow){
                    //3-1.1.根据idX得到该政法数据
                    LibraryPolocyRegulation libraryPolocyRegulation=libraryPolocyRegulationMapper.selectById(idX);
                    EntityWrapper idXwrapper=new EntityWrapper();
                    idXwrapper.eq("ID_X",idX);
                    //3-1.2.判断该法规是否是修改申请，如果是，则将政策法规修改表中的数据替换政策法规表中的数据
                    if("3".equals(libraryPolocyRegulation.getUptType())){
                        //删除政法表中，原来的本地服务器中的文件
                        List<TLibraryPolocyRegulationAtta> attas=libraryPolocyRegulationAttaMapper.selectList(idXwrapper);
                        List<Integer> attasIdCs=new ArrayList<>();
                        if(attas!=null && attas.size()>0){
                            for(TLibraryPolocyRegulationAtta atta:attas){
                                FileUpLoadUtil.deleteFile(atta.getFilePath());
                                attasIdCs.add(atta.getIdC());
                            }
                        }
                        //批量删除原来的政法附件数据
                        libraryPolocyRegulationAttaMapper.deleteBatchIds(attasIdCs);

                        //3-1.2.1.查询修改表中的数据
                        TLibraryPolocyRegulationUpt libraryPolocyRegulationUpt=libraryPolocyRegulationUptMapper.queryByIdX(idX);
                        List<TLibraryPolocyRegulationUptAtta> uptAttas=libraryPolocyRegulationUptAttaMapper.selectList(idXwrapper);
                        //3-1.2.2.调用工具方法，Tool:3.(replacePolicy)对于修改申请审核通过的，替换原来的数据
                        replacePolicy(idX,libraryPolocyRegulationUpt,uptAttas);
                        //3-1.2.3.删除修改表中的数据
                        libraryPolocyRegulationUptMapper.deleteById(libraryPolocyRegulationUpt.getIdU());
                    }
                    //3-1.2.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为null、数据状态（DATA_STAUTS）为 2:已审批
                    libraryPolocyRegulationMapper.updateStatus("2",idX);
                }
                //3-2.当前审批人不是最后一个审批人：
                else if(null!=nextFlow){
                    //3-2.1.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为下级审批人的数据
                    libraryPolocyRegulationMapper.updateLastCheck(nextFlow.getCheckUserId(),nextFlow.getCheckUser(),idX);

                    //3-2.2.修改业务表的下级节点：1-活跃,0-未完成,修改人和修改时间为当前登录人
                    nextFlow.setNodeIsActive("1");//活跃状态为 1-活跃
                    nextFlow.setCurrentNodeStatus("0");//节点状态为0-未完成
                    nextFlow.setUpdateor(nowFlow.getCheckUser());//修改人
                    nextFlow.setUpdateTime(new Date());//修改时间
                    flowLibraryPolocyRegulationMapper.updateFlow(nextFlow,null);
                }

            }

            //3.不通过 0
            else if("0".equals(result)){
                //3-1.修改政法表 当前审批人id(CURR_CHECK_ID)和审批人姓名(CURR_CHECK_NAME)为null、数据状态（DATA_STAUTS）为 -1:已退回
                libraryPolocyRegulationMapper.updateStatus("-1",idX);

                //3-2.修改业务表 的发起人 1-活跃,0-未完成,修改人和修改时间为当前登录人
                firstFlow.setNodeIsActive("1");//活跃状态为 1-活跃
                firstFlow.setCurrentNodeStatus("0");//节点状态为0-未完成
                firstFlow.setUpdateor(nowFlow.getCheckUser());//修改人
                firstFlow.setUpdateTime(new Date());//修改时间
                flowLibraryPolocyRegulationMapper.updateFlow(firstFlow,null);
            }
        } catch (Exception e) {
            System.out.println("TLibraryPolocyRegulationCheckRecServiceImpl 中 Tool:2.(updatePolicyByCheck)根据审批结果修改政法表修改政法表 出错：");
            e.printStackTrace();
        }


    }

    /**
     * Tool:3.(replacePolicy)对于修改申请审核通过的，替换原来的数据
     * 整体思路：
     *      1）设置修改后的政法对象
     *      2）根据idX调用myBatis_plus中的根据主键修改部分字段的方法 进行修改
     * @param idX                        原有的政策法规主键id值
     * @param libraryPolocyRegulationUpt 修改后的数据
     * @return
     * @author 田鑫艳
     * @createTime 2021/4/7 9:28
     * @updateTime 2021/4/7 9:28
     */
    @Transactional
    public void replacePolicy(Integer idX,TLibraryPolocyRegulationUpt libraryPolocyRegulationUpt,List<TLibraryPolocyRegulationUptAtta> uptAttas){
        //最终要修改的政法数据对象
        LibraryPolocyRegulation policy=new LibraryPolocyRegulation();
        policy.setIdX(idX);


        //1）设置修改后的政法对象
        //政策法规名称 POLOCY_NAME
        policy.setPolocyName(libraryPolocyRegulationUpt.getPolocyName());
        //文号 DOC_NUMBER
        policy.setDocNumber(libraryPolocyRegulationUpt.getDocNumber());
        //发文单位 UNIT_NAME
        policy.setUnitName(libraryPolocyRegulationUpt.getUnitName());
        //关键词 KEY_WORDS
        policy.setKeyWords(libraryPolocyRegulationUpt.getKeyWords());
        //行政地区 ADMINISTRATIVE_REGION
        policy.setAdministrativeRegion(libraryPolocyRegulationUpt.getAdministrativeRegion());
        //备注 REMARK
        policy.setRemark(libraryPolocyRegulationUpt.getRemark());
        //正文 CONTENT
        policy.setContent(libraryPolocyRegulationUpt.getContent());

        //2）根据idX调用myBatis_plus中的根据主键修改部分字段的方法 进行修改
        //注：updateById在插入时，会根据实体类的每个属性进行非空判断，只有非空的属性所对应的字段才会出现在SQL语句中
        libraryPolocyRegulationMapper.updateById(policy);

        //最终要修改的政法附件数据对象集合
        List<TLibraryPolocyRegulationAtta> attas=new ArrayList<>();
        for(TLibraryPolocyRegulationUptAtta uptAtta:uptAttas){
            TLibraryPolocyRegulationAtta atta=new TLibraryPolocyRegulationAtta();
            atta.setIdX(idX);
            atta.setFileName(uptAtta.getFileName());
            atta.setFilePath(uptAtta.getFilePath());
            atta.setFileSize(uptAtta.getFileSize());
            atta.setCreateorId(uptAtta.getCreateorId());
            atta.setCreateor(uptAtta.getCreateor());
            atta.setCreateTime(uptAtta.getCreateTime());
        }
        //批量插入
        libraryPolocyRegulationAttaMapper.insertBatches(attas);

    }



}

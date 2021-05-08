package com.common.business.collection.means.service.impl;

import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.means.mapper.TInformationsMapper;
import com.common.business.collection.means.service.TInformationsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.collection.means.web.InformationsVo;
import com.common.business.collection.meanslist.entity.RelationProList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.collection.meanslist.mapper.RelationProListMapper;
import com.common.business.collection.meanslist.mapper.TDevelopmentInformationListMapper;
import com.common.business.project.approval.entity.TProPerformanceInfo;
import com.common.business.project.approval.mapper.TProPerformanceInfoMapper;
import com.common.system.shiro.ShiroUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * TInformationsServiceImpl [资料收集上传 Service服务实现层]
 * </p>
 *
 * @author 田鑫艳
 * @since 2021-03-16
 */
@Service
public class TInformationsServiceImpl extends ServiceImpl<TInformationsMapper, TInformations> implements TInformationsService {

    @Autowired
    private TInformationsMapper informationsMapper;//资料收集上传 mapper
    @Autowired
    private TProPerformanceInfoMapper proPerformanceInfoMapper;//项目主子信息 mapper
    @Autowired
    private TDevelopmentInformationListMapper developmentInformationListMapper;//资料清单拟定表 mapper



    /**
     * 1.TInformationsServiceImpl [资料收集上传 Service服务实现层]
     * 资料收集上传的主页面显示
     * @param current       开始查询的页码数 默认为第1页
     * @param size          每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo 精确查询的字段（封装成了TProPerformanceInfo）对象
     * @param search        综合查询的字段
     * @param userId        当前登录用户的id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/16 16:10
     * @updateTime 2021/3/16 16:10
     */
    @Override
    public PageInfo<TProPerformanceInfo> queryForPage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String search,String userId) throws Exception{

        PageHelper.startPage(current, size);
        //1.拿到符合条件的子项目信息
        List<TProPerformanceInfo> showDatas = proPerformanceInfoMapper.queryInformationPerformanceInfo(search,proPerformanceInfo,userId);//接收的是TProPerformanceInfo类型的列表
        //2.如果该拿到子项目的信息不为空，则进行下一步的查询
        if(showDatas.size()>0){
            //2-1.从后台查询数据，得到每一个子项目的需要上传的资料是否上传完毕  0-未上传  1-已上传  2-已认证
            //拟定上传的资料集合 跟 已经上传的资料集合
            int j=0;
            for(TProPerformanceInfo tpr:showDatas){
                //判断 所有资料清单拟定 中的任务状态。有 2-已认证 则该项目的状态为2-已认证，有1-已上传 则该项目的状态为1-已上传
                //拿到该登录人需要上传的资料清单集合 taskStatus
                List<TDevelopmentInformationList> developLists=developmentInformationListMapper.selectEndStatus(tpr.getIdR(),tpr.getInformationVersionNO(),Integer.parseInt(userId));
                Map<String,String> developListsMap=new HashMap<>();
                for(TDevelopmentInformationList develop:developLists){
                    developListsMap.put(develop.getTaskStatus(),develop.getTaskStatus());
                }
                //如果有2-已认证，则该项目的状态为 已认证
                if("2".equals(developListsMap.get("2"))){
                    tpr.setInformationISEnd(2);//已认证
                }else if("1".equals(developListsMap.get("1"))){
                    tpr.setInformationISEnd(1);//已上传
                }else{
                    tpr.setInformationISEnd(0);//未上传
                }


            }
        }

        System.out.println("资料收集上传--服务实现层 分页显示数据 从后端拿到的集合对象："+showDatas.toString());
        return new PageInfo<TProPerformanceInfo>(showDatas);//将list列表 转换成PageInfo的形式并返回

    }

    /**
     * 2.TInformationsServiceImpl [资料收集上传 Service服务实现层]
     * 选择一个项目，进行该项目的资料上传
     * @param idR 该项目下的资料清单关系表主键id
     * @param versionNO 该项目需要上传的最新版本号
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/18 14:01
     * @updateTime 2021/3/18 14:01
     */
    @Override
    @Transactional
    public List<TDevelopmentInformationList> chooseProjectUpload(Integer idR, String versionNO,ShiroUser user) throws Exception{

        //1.拟定表拟定上传的资料
        List<TDevelopmentInformationList>  informanceLists=developmentInformationListMapper.informanceLists(idR,versionNO,user.getId());

        //2.遍历该拟定表中的模块，得到每一个模块上传的资料,并将该上传的资料添加到每一个 拟定表中相对应的模块
        if(informanceLists.size()>0){
            for(TDevelopmentInformationList informationList:informanceLists){
                List<TInformations> informations=informationsMapper.getModuleInformances(informationList.getIdB(),user.getId());
                informationList.setInformations(informations);
            }
        }

        return informanceLists;
    }

    /**
     * 3.修改 该拟定单的状态
     * 整体思路：
     *      1）根据idA拟定关系表主键值，拿到当前登录者的所有的拟定表
     *      2）遍历拟定单，如果是提交并且该拟定单上传的资料个数大于1，则修改该拟定单的状态为 1-已上传
     *      3）如果是暂存，该用户下的拟定单所有的状态为 0-未上传
     * @param idA 拟定关系表主键值
     * @param operation 操作0-暂存  1-提交
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/27 15:34
     * @updateTime 2021/3/27 15:34
     */
    @Override
    @Transactional
    public void updateCoumnById(Integer idA,Integer operation,Integer idR,ShiroUser user) {
        // 1）根据idA拟定关系表主键值，拿到当前登录者的该项目下所有的拟定表
        List<TDevelopmentInformationList> developmentInformations=developmentInformationListMapper.informanceLists(idR,null,user.getId());

        // 2）遍历拟定单，如果是提交并且该拟定单上传的资料个数大于1，则修改该拟定单的状态为 1-已上传
        if(operation!=null && 1==operation) {
            for (TDevelopmentInformationList deveInfo : developmentInformations) {
                Integer infoCount = informationsMapper.queryInfoByIdB(deveInfo.getIdB());
                if (infoCount > 0) {
                    //修改该模块下的拟定表的状态 (要修改的数据库中的字段，修改的值，主键idB)  TASK_STAUTS:1 表示“已上传 ”     0 表示“未上传”
                    developmentInformationListMapper.updateCoumnById("TASK_STAUTS",String.valueOf(1),deveInfo.getIdB(),null,user.getId());
                }

            }
        }
        // 3）如果是暂存，该用户下的拟定单所有的状态为 0-未上传
        else if(0==operation){
            //修改该模块下的拟定表的状态 (要修改的数据库中的字段，修改的值，主键idB)  TASK_STAUTS:1 表示“已上传 ”     0 表示“未上传”
            developmentInformationListMapper.updateCoumnById("TASK_STAUTS",String.valueOf(0),null,idA,user.getId());
        }


    }


    /**
     * 4.TInformationsServiceImpl [资料收集上传 Service服务实现层]
     * 选择一个模块下，上传该模块下的资料信息
     * @param information 资料信息
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 11:33
     * @updateTime 2021/3/19 11:33
     */
    @Override
    @Transactional
    public void saveClassUpload(TInformations information, ShiroUser user) throws Exception {
        System.out.println("上传的资料信息："+information.toString());
        //设置 文件资料下载次数、文件预览次数
        information.setDownloadsNum(0);//下载次数 默认为0
        information.setPreviewNum(0);//预览次数 默认为0
        information.setUpdateorId(String.valueOf(user.getId()));//上传人id
        information.setUpdateor(user.getName());//上传人姓名
        information.setUpdateTime(new Date());//上传时间
        information.setCeResult("0");//默认状态为0-未认证

        //向数据表中插入数据
        informationsMapper.insert(information);


    }

    /**
     * 4.TInformationsServiceImpl [资料收集上传 Service服务实现层]
     * 删除资料
     * @param deleteInformanceIdCs 要删除的资料id集合
     * @param idB  要删除的idB 资料清单拟定的主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 13:47
     * @updateTime 2021/3/19 13:47
     */
    @Override
    @Transactional
    public void chooseClassDelete(List<String> deleteInformanceIdCs, Integer idB) throws Exception{
        //1.删除资料数据
        informationsMapper.chooseClassDelete(deleteInformanceIdCs,idB);
        //2.如果该模块下的资料数据都被删除了，那么，修改该模块的 任务状态：0-未上传（默认）
        List<TInformations> informations = informationsMapper.selectInformations(null,idB);
        if(informations==null|| informations.size()==0){
            //3.修改该模块下的拟定表的状态 (要修改的数据库中的字段，修改的值，主键idB)  TASK_STAUTS:1 表示“已上传 ”     0 表示“未上传”
            developmentInformationListMapper.updateCoumnById("TASK_STAUTS","0",idB,null,null);
        }

    }

    /**
     * 5.TInformationsServiceImpl [资料收集上传 Service服务实现层]
     *根据要idC集合和idB来查询 资料表中的数据
     * @param informanceIdCs 要删除的资料表中的主键id集合
     * @param idB 要删除的idB 资料清单拟定的主键id值
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/19 14:29
     * @updateTime 2021/3/19 14:29
     */
    @Override
    public List<TInformations> selectInformations(List<String> informanceIdCs, Integer idB) throws Exception {
        List<TInformations> informations =informationsMapper.selectInformations(informanceIdCs,idB);

        return informations;
    }

    /**
     * 6.TInformationsServiceImpl [资料收集上传 Service服务实现层]
     * 修改资料表中的字段
     * @param idC 资料的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/22 16:40
     * @updateTime 2021/3/22 16:40
     */
    @Override
    public void modifyColumnStatus(Integer idC)throws Exception {
        informationsMapper.modifyAutoIncrement("F_DOWNLOADS_NUM",idC);
    }

    /**ok
     * 1.资料收集审核
     * 分页显示数据
     * @param current                   开始查询的页码数 默认为第1页
     * @param size                      每页的大小  默认每页显示10条数据
     * @param proPerformanceInfo        精确查询封装的对象
     * @param search                    综合查询的字段
     * @param userId             项目组员id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 10:39
     * @updateTime 2021/3/24 10:39
     */
    @Override
    public PageInfo<TProPerformanceInfo> agreePage(Integer current, Integer size, TProPerformanceInfo proPerformanceInfo, String userId, String search) throws Exception{
        //1.分页拦截
        PageHelper.startPage(current,size);
        List<TProPerformanceInfo> lastProformanceInfos=new ArrayList<>();
        //2.查询数据  条件：项目完结、已经通过审批、最新版本、当前登录人是项目主管
        /**
         * g.GROUP_MEMBER_ID=u.id 相对应，应该会变
         * 	r.CREATE_STAUTS='2' 拟定关系表已经审批
         * 	r.RELATION_STATUS='1' 拟定关系表的单据状态为正常
         * 	p.PRO_STATUS='1' 项目已经立项
         * 	d.TASK_STAUTS ='1' 拟定单表的任务状态为 1-已经上传
         * 	u.id=#{userId} and g.IS_WORK_CHARGE='Y' 当前登录人是外勤主管
         */
        List<TProPerformanceInfo> proPerformanceInfos=proPerformanceInfoMapper.pageForFileCertificInfos(proPerformanceInfo,userId,search);
        //审核状态 如果拟定表中的每一个模块下拟定的资料都已经审核，则审核状态为 已审核 否则都是 待审核
        if(proPerformanceInfos.size()>0){
            for(TProPerformanceInfo tpr:proPerformanceInfos){
                //得到 已经上传资料的 项目资料清单关系表的idR主键值
                Integer idR=tpr.getIdR();


                //该idR是所有模块的资料信息已经上传的idR
                //根据 项目资料清单关系表的idR 查询 该idR版本下上传的资料信息(查询的是该拟定单已经上传的资料信息)
                List<TInformations> informations=informationsMapper.selectInformationByIdR(idR);

                if(informations.size()>0){

                    tpr.setFileUpdateNum(informations.size());//设置上传文件个数
                    //根据 项目资料清单关系表的idR 查询 该idR版本下是否有没有认证的信息 如果有则该项目的状态为 未审核
                    Integer isNoAgree=informationsMapper.selectIsNoAgree(idR);
                    if(isNoAgree>0){
                        tpr.setFileCertificISEnd(2);//2 待审核
                    }else{
                        tpr.setFileCertificISEnd(1);//1 已经审核
                    }
                }
                //将拟定资料中已经上传的资料（包含已审核和未审核的数据）的项目添加到要返回的最终项目列表中
                lastProformanceInfos.add(tpr);

            }
        }
        return new PageInfo<TProPerformanceInfo>(lastProformanceInfos);
    }

    /**ok
     * 2.资料收集审核
     * 该项目下需要审核认证的资料信息界面
     * @param idR 拟定关系表中的主键id
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 11:27
     * @updateTime 2021/4/15 11:27
     */
    @Override
    public List<TDevelopmentInformationList> fileDetails(Integer idR,Integer detail) throws Exception{
        //最终要返回的拟定单集合（最终返回的 每个拟定下都上传了资料）
        List<TDevelopmentInformationList> lastDevelopment=new ArrayList<>();
        //根据idR查询该版本下所有的拟定单
        List<TDevelopmentInformationList> developmentInformations=developmentInformationListMapper.fileDetails(idR);
        if(developmentInformations!=null && developmentInformations.size()>0){
            //遍历拟定单 拿到每个拟定清单下的已经上传的资料
            for(TDevelopmentInformationList developmentInformation:developmentInformations){
                //判断是否是 审核界面的查看详情
                //查看详情界面
                if(detail!=null && detail==1){

                    //判断该拟定单是否不是 0-未上传，如果不是，则将该资料信息添加到拟定单中
                    if(!"0".equals(developmentInformation.getTaskStatus())){
                        List<TInformations> informations=informationsMapper.selectInformations(null,developmentInformation.getIdB());
                        developmentInformation.setInformations(informations);//将 资料信息 添加到 对应的拟定模块中
                        lastDevelopment.add(developmentInformation);//将有资料上传的拟定单 添加到最终要返回的 拟定单集合中
                    }
                }
                //审核具体资料的界面
                else{
                    //判断该拟定单 是否处于1-已上传 即（要进行审核的状态）
                    if("1".equals(developmentInformation.getTaskStatus())){
                        List<TInformations> informations=informationsMapper.selectInformations(null,developmentInformation.getIdB());
                        if(informations!=null && informations.size()>0){
                            //判断该文件是否是 0-未认证 ，如果是未认证，则将该资料添加到拟定单中
                            List<TInformations> lastInfomations=new ArrayList<>();
                            for(TInformations information:informations){
                                if("0".equals(information.getCeResult())){
                                    lastInfomations.add(information);
                                }
                            }
                            //最终的要审核的资料不为空，则将该资料添加到该拟定单中
                            if(lastInfomations!=null && lastInfomations.size()>0){
                                developmentInformation.setInformations(informations);//将 资料信息 添加到 对应的拟定模块中
                                lastDevelopment.add(developmentInformation);//将有资料上传的拟定单 添加到最终要返回的 拟定单集合中
                            }

                        }
                    }
                }

            }
        }
        return lastDevelopment;
    }

    /**ok
     * 3.资料收集审核
     * 单个/批量 认证
     * @param informations 前端传过来的 资料集合
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/24 13:44
     * @updateTime 2021/3/24 13:44
     */
    @Override
    public void agreeFiles(InformationsVo informations,ShiroUser user) throws Exception{
        //判断 传递过来的拟定单集合 是否为空，如果不为空进行操作
        if(informations.getDevelopmentInformationList()!=null && informations.getDevelopmentInformationList().size()>0){
            //遍历 传递过来的拟定单集合
            for(TDevelopmentInformationList developmentInformation:informations.getDevelopmentInformationList()){
                //遍历 每一个拟定单下的资料信息 进行认证修改 资料表数据
                for(TInformations information:developmentInformation.getInformations()){
                    information.setCeId(String.valueOf(user.getId()));//认证人id
                    information.setCeName(user.getName());//认证人姓名
                    information.setCeDate(new Date());//设置当前时间

                    informationsMapper.updateAgreeFiles(information);
                }
                //修改该拟定单的任务状态为：2-已认证
                //修改该模块下的拟定表的状态 (要修改的数据库中的字段，修改的值，资料清单拟定主键idB)  TASK_STAUTS:1 表示“已上传 ”     0 表示“未上传”  2 表示“已认证”
                developmentInformationListMapper.updateCoumnById("TASK_STAUTS","2",developmentInformation.getIdB(),null,null);
            }
        }

    }


}

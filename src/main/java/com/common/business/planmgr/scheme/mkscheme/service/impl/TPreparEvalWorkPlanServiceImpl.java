package com.common.business.planmgr.scheme.mkscheme.service.impl;

import com.common.business.planmgr.scheme.mkscheme.entity.RelationProPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.entity.TPreparEvalWorkPlan;
import com.common.business.planmgr.scheme.mkscheme.mapper.TPreparEvalWorkPlanMapper;
import com.common.business.planmgr.scheme.mkscheme.service.TPreparEvalWorkPlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.business.planmgr.scheme.mkscheme.web.RelationProPreparEvalWorkPlanVo;
import com.common.system.entity.EntityDao;
import com.common.system.exception.ServiceException;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.FileUpLoadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  编制评价工作方案
 *  服务实现类
 * </p>
 *
 * @author 陈睿超
 * @since 2021-04-08
 */
@Service
public class TPreparEvalWorkPlanServiceImpl extends ServiceImpl<TPreparEvalWorkPlanMapper, TPreparEvalWorkPlan> implements TPreparEvalWorkPlanService {




    @Override
    public List<TPreparEvalWorkPlan> upLoadFiles(RelationProPreparEvalWorkPlanVo relationProPreparEvalWorkPlanVo, ShiroUser user) throws Exception {
        List<TPreparEvalWorkPlan> returnList = new ArrayList<TPreparEvalWorkPlan>();
        RelationProPreparEvalWorkPlan relationProPreparEvalWorkPlan = relationProPreparEvalWorkPlanVo.getRelationProPreparEvalWorkPlan();
        //前端传过来的附件集合
        MultipartFile[] files = relationProPreparEvalWorkPlanVo.getFiles();
        //1.将上传的文件保存到本地服务器中
        //遍历 前台上传的文件个数，将每一个文件上传到本地服务器中， 并将每一个文件的相关数据插入到数据表中
        if (files != null && files.length > 0) {
            //遍历最终要上传的文件，进行上传
            for (MultipartFile listFile:files) {
                //如果当前文件的大小大于0，说明这是个文件，那么才可以对该文件进行上传操作（getSize方法用来获取文件的大小，单位是字节。）
                if (listFile.getSize() > 0) {
                    //向上转型 成接口类 EntityDao
                    EntityDao entity = new TPreparEvalWorkPlan();
                    //调用文件保存方法，并且返回接口类  (接口类，文件类型，文件实体类，当前登录对象，上传的是哪个项目的(主子项目id值))
                    entity = FileUpLoadUtil.upload(entity, FileUpLoadUtil.PLANMGR_SCHEME_MKSCHEME, listFile, user, relationProPreparEvalWorkPlan.getIdA());
                    TPreparEvalWorkPlan newPreparEvalWorkPlan = (TPreparEvalWorkPlan) entity;
                    newPreparEvalWorkPlan.setFileSize(((TPreparEvalWorkPlan) entity).getFileSize());
                    newPreparEvalWorkPlan.setFilePath(((TPreparEvalWorkPlan) entity).getFilePath());
                    newPreparEvalWorkPlan.setFileName(((TPreparEvalWorkPlan) entity).getFileName());
                    newPreparEvalWorkPlan.setCreateor(((TPreparEvalWorkPlan) entity).getCreateor());
                    newPreparEvalWorkPlan.setCreateTime(((TPreparEvalWorkPlan) entity).getCreateTime());
                    newPreparEvalWorkPlan.setCreateorId(((TPreparEvalWorkPlan) entity).getCreateorId());
                    baseMapper.insert(newPreparEvalWorkPlan);

                    returnList.add(newPreparEvalWorkPlan);
                }else {
                    throw new ServiceException(listFile.getOriginalFilename()+"文件大小为0，请不要上传空文件");
                }
            }
            return returnList;

        } else {
            throw new ServiceException("您没有选择任何一个文件进行上传");
        }
    }
}

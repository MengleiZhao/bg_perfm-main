package com.common.system.page;

import com.common.system.entity.Combobox;
import com.common.system.exception.BaseException;
import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/6/20.
 * Time:16:14
 * ProjectName:bg_perfm
 */
public class BaseController {
    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected ShiroUser getUser(){
        return (ShiroUser)ShiroKit.getSubject().getPrincipal();
    }

    /**
     * 封装返回方法
     * @param success
     * @param info
     * @param data
     * @return
     */
    public Result getJsonResult(boolean success,String info,Object data){
        Result result=new Result();
        result.setStatus(success);
        result.setData(data);
        if(StringUtils.isEmpty(info)){
            //如果是null根据SUCCESS来返回
            if(success){
                result.setCode(MsgCode.SUCCESS);
                result.setMsg("操作成功");
            }else{
                result.setMsg("操作失败");
                result.setCode(MsgCode.FAILED);
            }
        }else{
            if(success){
                result.setCode(MsgCode.SUCCESS);
            }else{
                result.setCode(MsgCode.FAILED);
            }
            result.setMsg(info);
        }
        return result;
    }

    protected Result buildResult(boolean status,int code,String msg,Object o){
        Result result = new Result();
        result.setMsg(msg);
        result.setStatus(status);
        result.setCode(code);
        result.setData(o);
        return result;
    }

    public List<ComboboxJson> getComboboxJson(List<?> list){
        List<ComboboxJson> listCombobox=new ArrayList<ComboboxJson>();
        ComboboxJson comboboxJson=null;
        if(null!=list && list.size()>0){
            int t=list.size();
            for (int i = 0; i < t; i++) {
                comboboxJson=new ComboboxJson();
                Combobox combobox=(Combobox)list.get(i);
                comboboxJson.setId(combobox.getId());
                comboboxJson.setCode(combobox.getCode());
                comboboxJson.setText(combobox.getText());
                comboboxJson.setDesc(combobox.getDesc());
                if(i==0){
                    //默认选中第一个
                    comboboxJson.setSelected(true);
                }
                listCombobox.add(comboboxJson);
            }
        }
        return listCombobox;
    }
    protected String getException(Exception e) {
        String msg = "操作失败";
        if (e instanceof BaseException) {
            msg = e.getMessage();
        } else {
            e.printStackTrace();
        }
        return msg;
    }
}

package com.common.business.collection.means.web;

import com.common.business.collection.means.entity.TInformations;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationList;
import com.common.business.collection.meanslist.entity.TDevelopmentInformationListTemp;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 资料收集审核 前端传送的参数
 */
@Data
@Accessors(chain = true)
@Log
@Getter
@Setter
@ToString
public class InformationsVo {

    private Integer idB;//拟定单的id主键
    private String idCs;//资料单的id主键 集合，中间用“,”分割

    private List<TDevelopmentInformationList> developmentInformationList;//拟定表集合


    private Integer idA;//主子项目id主键
    private MultipartFile[] files;//前端传过来的集合

}

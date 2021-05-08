package com.common.business.index.mapper;

import com.common.business.index.entity.TOperationManualRegulation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.sys.entity.RcDict;

import java.util.List;

/**
 * <p>
  * 操作手册及业规管理 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
public interface TOperationManualRegulationMapper extends BaseMapper<TOperationManualRegulation> {

    List<TOperationManualRegulation> listForPage(TOperationManualRegulation bean)  throws  Exception;
}
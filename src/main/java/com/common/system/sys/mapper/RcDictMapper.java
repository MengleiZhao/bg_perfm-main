package com.common.system.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.sys.entity.RcDict;

/**
 * <p>
  * 字典管理表 Mapper 接口
 * </p>
 *
 * @author 安达
 * @since 2021-03-02
 */
public interface RcDictMapper extends BaseMapper<RcDict> {
	 List<RcDict> getRcDictList();
}
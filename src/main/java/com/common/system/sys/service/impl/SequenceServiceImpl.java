package com.common.system.sys.service.impl;

import com.common.system.sys.service.SequenceService;
import com.common.system.util.sequence.Sequence;

import org.springframework.stereotype.Service;

/**
 * Created by Mr.Yangxiufeng on 2017/8/2.
 * Time:17:06
 * ProjectName:bg_perfm
 */
@Service
public class SequenceServiceImpl implements SequenceService {
    private Sequence sequence = new Sequence(0, 0);
    @Override
    public String getSequenceId() {
        return String.valueOf(sequence.nextId());
    }
}

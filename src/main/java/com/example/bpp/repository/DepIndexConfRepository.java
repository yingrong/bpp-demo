package com.example.bpp.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bpp.entity.DepIndexConfPo;
import com.example.bpp.mapper.DepIndexConfMapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xuefei
 */
@Service
public class DepIndexConfRepository extends ServiceImpl<DepIndexConfMapper, DepIndexConfPo> {

    public List<DepIndexConfPo> queryDependentIndexConf() {
        LambdaQueryWrapper<DepIndexConfPo> wrapper = Wrappers.lambdaQuery(DepIndexConfPo.class);
        return baseMapper.selectList(wrapper);
    }


}



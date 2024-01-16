package com.example.bpp.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bpp.entity.IndexFormulaConfPo;
import com.example.bpp.mapper.IndexFormulaConfMapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xuefei
 */
@Service
public class IndexFormulaConfRepository extends ServiceImpl<IndexFormulaConfMapper, IndexFormulaConfPo> {

    public List<IndexFormulaConfPo> queryDependentIndexConf() {
        LambdaQueryWrapper<IndexFormulaConfPo> wrapper = Wrappers.lambdaQuery(IndexFormulaConfPo.class);
        return baseMapper.selectList(wrapper);
    }


}



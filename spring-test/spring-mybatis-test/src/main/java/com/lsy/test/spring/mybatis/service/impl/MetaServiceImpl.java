package com.lsy.test.spring.mybatis.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lsy.test.spring.mybatis.entity.MetaVertexLabelEntity;
import com.lsy.test.spring.mybatis.mapper.MetaVertexLabelMapper;
import com.lsy.test.spring.mybatis.service.MetaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MetaServiceImpl implements MetaService {

    @Resource
    private MetaVertexLabelMapper metaVertexLabelMapper;

    @Override
    public List<MetaVertexLabelEntity> getEdgeTypeList() {
        return metaVertexLabelMapper.selectList(Wrappers.emptyWrapper());
    }
}

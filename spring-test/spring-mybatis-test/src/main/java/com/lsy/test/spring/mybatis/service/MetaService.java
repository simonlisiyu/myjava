package com.lsy.test.spring.mybatis.service;


import com.lsy.test.spring.mybatis.entity.MetaVertexLabelEntity;

import java.util.List;

/**
 * Created by lisiyu on 2019/6/24.
 */
public interface MetaService {

    List<MetaVertexLabelEntity> getEdgeTypeList();
}

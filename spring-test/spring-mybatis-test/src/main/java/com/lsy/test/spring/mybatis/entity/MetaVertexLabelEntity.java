package com.lsy.test.spring.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("kg_meta_vertex_label")
public class MetaVertexLabelEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "label")
    private String label;

    @TableField(value = "label_chn")
    private String labelChn;

    @TableField(value = "label_class")
    private String labelClass;

    @TableField(value = "label_subclass")
    private String labelSubclass;

    @TableField(value = "signature")
    private int signature;

    @TableField(value = "description")
    private String description;

    @TableField(value = "create_at")
    private Date createAt;

    @TableField(value = "create_by")
    private String createBy;

    @TableField(value = "update_at")
    private Date updateAt;

    @TableField(value = "update_by")
    private String updateBy;

    @JsonIgnore
    @TableLogic(value = "0")
    @TableField(value = "is_deleted")
    private int isDeleted;
}

package com.dianping.csc.common.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * 所有实体类的基础类
 * Created by yuchao on 15/6/17.
 */
@Data
public class EntityTest extends Entity{
    private int sex;
    private String name;
}

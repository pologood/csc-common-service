package com.dianping.csc.common.service.dao;

import com.dianping.csc.common.service.entity.Entity;
import lombok.Data;

/**
 * Created by csophys on 15/8/27.
 */
@Data
class MyEntity extends Entity {
    public String name;
    public int age;
    public String sex;

    public MyEntity(int age, String name, String sex) {
        this.setName(name);
        this.setSex(sex);
        this.setAge(age);
    }
}

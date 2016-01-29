package com.dianping.csc.common.service.util;

import com.dianping.csc.common.service.entity.EntityTest;

public class DaoCodeGenerateTest {

    public static void main(String[] args) {
        AvatarDaoCodeGenerate.generateByJavaBean(EntityTest.class);
    }
}
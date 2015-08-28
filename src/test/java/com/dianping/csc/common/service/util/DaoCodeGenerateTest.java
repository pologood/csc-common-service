package com.dianping.csc.common.service.util;

import com.dianping.csc.common.service.entity.EntityTest;
import org.junit.Test;

public class DaoCodeGenerateTest {

    @Test
    public void testDaoGenerate(){
        DaoCodeGenerate.generateByJavaBean(EntityTest.class);
    }
}
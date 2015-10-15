package com.dianping.csc.common.service.util;

import com.dianping.csc.common.service.entity.EntityTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoCodeGenerateTest {

    public static void main(String[] args) {
        DaoCodeGenerate.generateByJavaBean(EntityTest.class);
    }
}
package com.dianping.csc.common.service.util;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * Created by csophys on 14-10-25.
 */

public class CustomizeJunit4ClassRunner extends SpringJUnit4ClassRunner {

    static {
        try {
            Log4jConfigurer.initLogging("classpath:log/log4j.xml");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot load log4j,please check log4j.xml file exist?");
        }
    }

    public CustomizeJunit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}


package com.dianping.csc.common.service.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Created by csophys on 15/8/27.
 */
public class DAOMockTest {
    DAOMock daoMock = new DAOMock();

    @Test
    public void testGetByProperties() {
        /**
         * 1.java 引用坑
         * 2.map 顺序坑
         */
        MyEntity entity = new MyEntity(12, "zs", "男");
        MyEntity entity1 = new MyEntity(12, "zs", "女");
        MyEntity entity2 = new MyEntity(13, "zs", "女");
        MyEntity entity3 = new MyEntity(12, "xinxin", "男");

        daoMock.insert(entity);
        daoMock.insert(entity1);
        daoMock.insert(entity2);
        daoMock.insert(entity3);

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("age", 12);
        map.put("sex", "男");

        List list = daoMock.getByProperties(map);

        Assert.assertTrue(list.contains(entity));
        Assert.assertFalse(list.contains(entity1));
        Assert.assertFalse(list.contains(entity2));
        Assert.assertTrue(list.contains(entity3));


        HashMap<String, Object> map1 = Maps.newHashMap();
        map1.put("age", 12);
        map1.put("sex", Lists.newArrayList("男","女"));

        List list1 = daoMock.getByProperties(map1);

        Assert.assertTrue(list1.contains(entity));
        Assert.assertTrue(list1.contains(entity1));
        Assert.assertFalse(list1.contains(entity2));
        Assert.assertTrue(list1.contains(entity3));
    }

}
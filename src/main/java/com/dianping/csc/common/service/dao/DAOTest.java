package com.dianping.csc.common.service.dao;

import com.dianping.csc.common.service.entity.Entity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by yuchao on 15/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/local/appcontext-*.xml"
})
public abstract class DAOTest {

    protected DAO dao;
    protected Entity entity;


    @Before
    public void setUp() throws Exception {
        init();
    }

    protected abstract void init();


    @Test
    public void testInsert() throws Exception {
        Assert.assertTrue(dao.insert(entity) > 0);
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = dao.insert(entity);
        Assert.assertTrue(dao.delete(id) > 0);
    }

    @Test
    public void testGet() throws Exception {
        Integer id = dao.insert(entity);
        Assert.assertEquals(entity, dao.get(id));
    }

    @Test
    public void testUpdate() throws Exception {
        Integer id = dao.insert(entity);
        doUpdate();
        int count = dao.update(id, entity);
        Assert.assertTrue(count > 0);
    }

    /**
     * 具体update的事情
     */
    protected abstract void doUpdate();

    protected void set(DAO dao, Entity entity) {
        this.dao = dao;
        this.entity = entity;
    }


}

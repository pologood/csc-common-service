package com.dianping.csc.common.service.dao;

import com.dianping.csc.common.service.entity.Entity;
import com.dianping.csc.common.service.util.CustomizeJunit4ClassRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;


/**
 * Created by yuchao on 15/6/17.
 */

@RunWith(CustomizeJunit4ClassRunner.class)
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
        Integer result = dao.insert(entity);
        Assert.assertTrue(result > 0);
        Assert.assertTrue(dao.delete(result) > 0);
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
        Assert.assertTrue(dao.delete(id) > 0);
    }

    @Test
    public void testUpdate() throws Exception {
        Integer id = dao.insert(entity);
        doUpdate();
        int count = dao.update(id, entity);
        Assert.assertTrue(count > 0);
        Assert.assertTrue(dao.delete(id) > 0);
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

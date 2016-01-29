package ${package};

import com.dianping.csc.common.service.util.SpringBaseWithCustomizeRunnerTest;
import ${entity};
import ${dao};
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
* Created by csophys on with template on ${.now?date}.
*/
public class ${daoSimple}Test extends SpringBaseWithCustomizeRunnerTest {
    public static final int TEST_ID = -1;
    @Resource
    ${daoSimple} ${daoID};

    @Test
    public void get() {
        ${entitySimple} e = insertEntity(TEST_ID);
        Assert.assertTrue(${daoID}.get(e.getId()) != null);
    }

    @Test
    public void insert() {
        insertEntity(TEST_ID);
    }

    @Test
    public void delete() {
        Assert.assertTrue(${daoID}.delete(insertEntity(TEST_ID).getId()) > 0);
    }

    @Test
    public void update() {
        ${entitySimple} e = new ${entitySimple}();
        //TODO set something
        e.setId(insertEntity(TEST_ID).getId());
        Assert.assertTrue(${daoID}.update(e) > 0);
    }

    private ${entitySimple} insertEntity(int id) {
        ${entitySimple} e = new ${entitySimple}();
        e.setId(id);
        //TODO set something
        Assert.assertTrue(${daoID}.insert(e) == 1);
        return e;
    }
}
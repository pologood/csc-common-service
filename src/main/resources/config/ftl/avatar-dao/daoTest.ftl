package ${package};

import com.dianping.csc.common.service.dao.DAOTest;
import ${entity};

import javax.annotation.Resource;

/**
* Created by csophys on with template on ${.now?date}.
*/
public class ${daoSimple}Test extends DAOTest {

    @Resource
    ${daoSimple} ${daoID};
    ${entitySimple} ${entityID};

    @Override
    protected void init() {
        ${entityID} = new ${entitySimple}();
        //TODO:assign data
        set(${daoID}, ${entityID});
    }

    @Override
    protected void doUpdate() {
      //TODO:assign data for update
    }
}
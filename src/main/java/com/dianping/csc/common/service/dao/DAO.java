package com.dianping.csc.common.service.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.csc.common.service.entity.Entity;

/**
 * 所有DAO的父类
 * <p/>
 * Created by yuchao on 15/6/17.
 */
@Deprecated
public interface DAO<T extends Entity> extends GenericDao {
    @DAOAction(action = DAOActionType.INSERT)
    Integer insert(@DAOParam("e") T entity);

    @DAOAction(action = DAOActionType.DELETE)
    Integer delete(@DAOParam("id") int id);

    @DAOAction(action = DAOActionType.LOAD)
    T get(@DAOParam("id") int id);

    @DAOAction(action = DAOActionType.UPDATE)
    Integer update(@DAOParam("id") int id, @DAOParam("e") T entity);

}

package com.dianping.csc.common.service.dao.mock;

import com.dianping.avatar.dao.DAOMethod;
import com.dianping.core.type.PageModel;
import com.dianping.csc.common.service.dao.DAO;
import com.dianping.csc.common.service.entity.Entity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有DAO测试的基础类,以map存储
 * <p/>
 * Created by yuchao on 15/6/17.
 */
public class DAOMock<T extends Entity> implements DAO<T> {
    protected int id = 0;
    protected Map<Integer, T> db = new HashMap<Integer, T>();

    public Integer insert(T entity) {
        entity.setId(++id);
        db.put(id, entity);

        return id;
    }

    public Integer delete(int id) {
        db.remove(id);

        return 1;
    }

    public T get(int id) {
        return db.get(id);
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<T>();
        for (T t : db.values()) {
            list.add(t);
        }
        return list;
    }

    public Integer update(int id, T entity) {
        db.put(id, entity);

        return 1;
    }


    public List<Object> executeQuery(DAOMethod daoMethod) throws DataAccessException {
        return null;
    }

    public PageModel executePageQuery(DAOMethod daoMethod) throws DataAccessException {
        return null;
    }

    public Object executeLoad(DAOMethod daoMethod) throws DataAccessException {
        return null;
    }

    public Object executeInsert(DAOMethod daoMethod) throws DataAccessException {
        return null;
    }

    public int executeUpdate(DAOMethod daoMethod) throws DataAccessException {
        return 0;
    }

    public int executeDelete(DAOMethod daoMethod) throws DataAccessException {
        return 0;
    }

    public Object executeCall(DAOMethod daoMethod) throws DataAccessException {
        return null;
    }

    public Object executeLimit(DAOMethod daoMethod) {
        return null;
    }
}

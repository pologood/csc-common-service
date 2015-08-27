package com.dianping.csc.common.service.dao;

import com.dianping.avatar.dao.DAOMethod;
import com.dianping.core.type.PageModel;
import com.dianping.csc.common.service.entity.Entity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.dao.DataAccessException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 所有DAO测试的基础类,以map存储
 * <p/>
 * Created by yuchao on 15/6/17.
 */
public class DAOMock<T extends Entity> implements DAO<T> {
    protected int id = 0;
    protected Map<Integer, T> db = Maps.newLinkedHashMap();

    public Integer insert(T entity) {
        entity.setId(++id);
        entity.setAddTime(new Date());
        entity.setUpdateTime(new Date());
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

    public List<T> getByProperties(Map<String, Object> propertyValues) {
        ArrayList<T> arrayList = Lists.newArrayList();
        row:
        for (T t : db.values()) {
            for (String property : propertyValues.keySet()) {
                if (!propertyValues.get(property).equals(getPropertyValue(t, property))) {
                    continue row;
                }
            }

            arrayList.add(t);
        }
        return arrayList;
    }

    public Integer update(int id, T entity) {
        entity.setUpdateTime(new Date());
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

    private static <T> Object getPropertyValue(T t1, String property) {
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        Object result = null;
        try {
            Method method = t1.getClass().getMethod(methodName);
            result = method.invoke(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}



package com.dianping.csc.common.service.util;

import com.dianping.avatar.cache.CacheKey;
import com.dianping.avatar.cache.CacheService;
import com.dianping.pigeon.remoting.ServiceFactory;
import com.dianping.pigeon.remoting.common.exception.RpcException;
import com.dianping.pigeon.remoting.common.util.Constants;
import com.dianping.pigeon.remoting.invoker.config.InvokerConfig;

import java.lang.reflect.Method;

/**
 * 查询product\b使用了缓存
 * Created by wanghaixin on 15/11/6.
 */
public class CacheHelper {
    //根据id获取类型的category
    public static final String TYPE_BY_ID_CATEGORY = "CSCBaseData";
    //所有business
    public static final String ALL_BUSINESS_CATEGORY = "CSCAllBusinessData";
    //根据departmentId获取所有product
    public static final String PRODUCT_BY_DEPARTMENTID_CATEGORY = "CSCProductByDepartmentIdData";
    /**
     * CacheService的get和add方法
     */
    private static final String GET = "get";
    private static final String ADD = "add";
    /**
     * 该类型所有值
     */
    private static final String ALL = "all";

    /**
     * 通用缓存方法,需要传入cacheService实例
     *
     * @param cacheKey  参数：{ category; 前缀_id }
     * @param dao       dao实例
     * @param daoMethod dao方法名
     * @param daoClazz  dao类
     * @param service   cacheService实例
     * @param <T>
     * @param <K>
     * @param <V>
     * @return
     */
    @Deprecated
    public static <T, K, V> T getTypeById(CacheKey cacheKey, K dao, String daoMethod, Class daoClazz, V service) {
        T type = null;
        Class serviceClazz = CacheService.class;
        try {
            Method serviceGetMethod = serviceClazz.getMethod(GET, CacheKey.class);
            type = (T) serviceGetMethod.invoke(service, cacheKey);
            if (type == null) {
                synchronized (CacheHelper.class) {
                    if (type == null) {
                        Method method1 = null;

                        Object[] key = cacheKey.getParams();
                        String[] tmp = key[0].toString().split("_");

                        if (tmp[1].equals(ALL)) {
                            method1 = daoClazz.getMethod(daoMethod);
                            type = (T) method1.invoke(dao);
                        } else {
                            method1 = daoClazz.getMethod(daoMethod, Integer.class);
                            type = (T) method1.invoke(dao, Integer.parseInt(tmp[1]));
                        }
                        if (type != null) {
                            Method serviceAddMethod = serviceClazz.getMethod(ADD, CacheKey.class, Object.class);
                            serviceAddMethod.invoke(service, cacheKey, type);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 通用缓存方法,优化了方法签名，更加容易使用
     *
     * @param cacheKey
     * @param dao
     * @param daoMethod
     * @param daoClazz
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> T getTypeById(CacheKey cacheKey, K dao, String daoMethod, Class daoClazz) {
        CacheService service = getService(CacheService.class);
        T type = null;
        Class serviceClazz = CacheService.class;
        try {
            Method serviceGetMethod = serviceClazz.getMethod(GET, CacheKey.class);
            type = (T) serviceGetMethod.invoke(service, cacheKey);
            if (type == null) {
                synchronized (CacheHelper.class) {
                    if (type == null) {
                        Method method1 = null;

                        Object[] key = cacheKey.getParams();
                        String[] tmp = key[0].toString().split("_");

                        if (tmp[1].equals(ALL)) {
                            method1 = daoClazz.getMethod(daoMethod);
                            type = (T) method1.invoke(dao);
                        } else {
                            method1 = daoClazz.getMethod(daoMethod, Integer.class);
                            type = (T) method1.invoke(dao, Integer.parseInt(tmp[1]));
                        }
                        if (type != null) {
                            Method serviceAddMethod = serviceClazz.getMethod(ADD, CacheKey.class, Object.class);
                            serviceAddMethod.invoke(service, cacheKey, type);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    private static <T> T getService(Class<T> serviceInterface) throws RpcException {
        InvokerConfig invokerConfig = new InvokerConfig(serviceInterface);
        return (T) ServiceFactory.getService(invokerConfig);
    }
}
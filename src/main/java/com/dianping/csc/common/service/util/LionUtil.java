package com.dianping.csc.common.service.util;

import com.dianping.csc.common.util.HttpUtil;
import com.dianping.lion.client.ConfigCache;

import java.util.HashMap;

/**
 * Created by csophys on 15/11/18.
 */

/**
 * 获取Lionkey 的值建议直接用Lion工具api
 * 请直接使用Lion api中提供的工具
 */
public class LionUtil {
    public static String getValue(String key, String defaultValue) {
        String value = null;

        try {
            value = ConfigCache.getInstance().getProperty(key);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if (value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static Boolean setValue(String key, String value) throws Exception {
        boolean rtnWrite = false;
        String[] list = key.split("\\.");
        String bizType = list[0];
        String detailedKey = "";

        for (int e = 1; e < list.length; ++e) {
            detailedKey = detailedKey + list[e];
            detailedKey = detailedKey + ".";
        }

        if (detailedKey.length() > 0) {
            detailedKey = detailedKey.substring(0, detailedKey.length() - 1);
        }


        String var10 = getValue("csc-common-util.lionApi.url", "");
        HashMap params = new HashMap();
        params.put("p", bizType);
        params.put("k", detailedKey);
        params.put("v", value);
        String response = HttpUtil.post(var10, params);
        if (response != null && response.length() > 0 && response.substring(0, 1).equals("0")) {
            rtnWrite = true;
        }

        return Boolean.valueOf(rtnWrite);
    }
}

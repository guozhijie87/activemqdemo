package com.guo.util;

import java.util.UUID;

/**
 * Created by jack on 2017/3/27.
 */
public class UUIDUtil {
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}

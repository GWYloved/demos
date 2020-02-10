package com.pc.demos.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YinPengcheng on 2019-12-14 14:33 email: mikilangkilo.yin@ximalaya.com
 */
public class MapUtils {

    @SuppressWarnings("unchecked")
    public static List getMapKeyList(Map map) {
        return new ArrayList(map.keySet());
    }
}

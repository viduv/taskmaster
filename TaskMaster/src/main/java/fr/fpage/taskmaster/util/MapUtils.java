package fr.fpage.taskmaster.util;

import java.util.HashMap;

public class MapUtils {

    public static boolean deepCompareHashMap(HashMap<String, String> map1, HashMap<String, String> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }
        for (String key : map1.keySet()) {
            if (!map2.containsKey(key)) {
                return false;
            }
            String value1 = map1.get(key);
            String value2 = map2.get(key);
            if (!value1.equals(value2)) {
                return false;
            }
        }
        return true;
    }


}

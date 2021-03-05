/**
 * projectName: research
 * fileName: FormalCsv.java
 * packageName: cn.cn.cn.zs.tools
 * date: 2021-01-20 21:01
 * copyright(c) 2019-2021 hust
 */
package cn.zs.tools;

import java.util.ArrayList;

/**
 * @version: V1.0
 * @author: cn.cn.zs
 * @className: FormalCsv
 * @packageName: cn.cn.cn.zs.tools
 * @data: 2021-01-20 21:01
 **/
public class DataConverter {
    public static String [][] list2Matrix(ArrayList<ArrayList<Integer>> source){
        String[][] res = new String[source.size()][];
        for (int i = 0; i < source.size(); i++) {
            ArrayList<Integer> list = source.get(i);
            res[i] = new String[list.size()];
            for (int j = 0; j < list.size(); j++) {
                res[i][j] = String.valueOf(list.get(j));
            }
        }
        return res;
    }

}
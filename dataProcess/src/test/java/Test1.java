/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-01 21:41
 * copyright(c) 2019-2021 hust
 */

import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import jnr.ffi.annotations.In;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-03-01 21:41
 **/

public class Test1 {
    @Test
    public  void ss(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("D:\\works\\data\\all\\SilhouetteTest\\brandDistance2.csv");
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (int i = 1; i < arrayLists.size(); i++) {
            ArrayList<Double> list = new ArrayList<>();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                list.add(Double.valueOf(arrayLists.get(i).get(j).trim()));
            }
            res.add(list);
        }
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                System.out.print(res.get(i).get(j)+",");
            }
            System.out.println();
        }
    }

    @Test
    public void rtrt(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("D:\\works\\data\\all\\SilhouetteTest\\brandDistance2.csv");
        double[][] res = new double[arrayLists.size()-1][];
        for (int i = 1; i < arrayLists.size(); i++) {
            res[i-1] = new double[arrayLists.get(i).size()];
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                res[i-1][j] = Double.valueOf(arrayLists.get(i).get(j).trim());
            }
        }
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                System.out.print(res[i][j]+ " ");
            }
            System.out.println();
        }
    }



}
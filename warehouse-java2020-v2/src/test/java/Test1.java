/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-01-17 15:27
 * copyright(c) 2019-2021 hust
 */

import cn.zs.dao.MyDataWriter;
import cn.zs.daoImp.CsvDataWriter;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;
import org.python.util.PythonInterpreter;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @version: V1.0
 * @author: zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-01-17 15:27
 **/
public class Test1 {
    @Test
    public void testSolution(){
        int [] temp = {117, 330, 216, 187, 341, 119, 1, 124, 33, 170, 129, 217, 175, 343, 77,
                112, 82, 196, 293, 258, 174, 355, 11, 106, 173, 281, 271, 205, 23, 245, 97, 0,
                229, 243, 314, 213, 20, 26, 350, 251, 141, 189, 253, 98, 318, 224, 186, 56, 4,
                114, 90, 10, 285, 50, 30, 180, 181, 67, 51, 291, 35, 284, 111, 215, 131, 133,
                123, 339, 194, 109, 252, 179, 351, 31, 287, 62, 74, 43, 232, 81, 19, 12, 316,
                132, 320, 238, 40, 325, 348, 25, 255, 145, 297, 301, 121, 298, 269, 130, 151,
                89, 286, 156, 306, 265, 24, 188, 71, 122, 236, 190, 84, 168, 241, 266, 313, 18,
                214, 310, 250, 107, 322, 108, 100, 45, 152, 22, 349, 203, 207, 249, 110, 8, 183,
                345, 72, 115, 184, 198, 354, 263, 332, 352, 292, 37, 47, 218, 149, 85, 16, 278,
                182, 52, 64, 319, 294, 126, 125, 39, 338, 321, 335, 83, 289, 144, 155, 342, 210,
                104, 128, 73, 222, 137, 79, 282, 158, 277, 103, 283, 6, 233, 200, 13, 176, 160,
                21, 331, 113, 120, 27, 223, 307, 92, 227, 275, 69, 221, 60, 340, 32, 270, 274,
                165, 159, 311, 248, 5, 127, 279, 95, 136, 57, 138, 317, 161, 267, 353, 268, 202,
                356, 101, 88, 231, 326, 38, 172, 15, 254, 276, 195, 36, 63, 70, 139, 220, 309,
                49, 337, 240, 312, 166, 178, 329, 290, 358, 346, 150, 86, 96, 261, 17, 296, 193,
                226, 357, 347, 44, 28, 264, 247, 259, 204, 235, 302, 295, 42, 135, 208, 118, 256,
                185, 93, 199, 143, 225, 359, 157, 2, 87, 300, 142, 146, 246, 191, 171, 303, 344, 34,
                209, 201, 59, 237, 53, 14, 167, 80, 242, 48, 7, 41, 75, 308, 66, 234, 288, 3, 29,
                327, 102, 244, 134, 304, 162, 99, 177, 94, 228, 76, 280, 219, 197, 334, 46, 140,
                212, 164, 65, 9, 328, 239, 153, 147, 78, 323, 105, 336, 273, 262, 260, 206, 54,
                58, 257, 68, 55, 211, 169, 61, 91, 333, 272, 148, 116, 230, 305, 163, 299, 154, 315, 324, 192};
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            list.add(temp[i]);
        }
        Collections.sort(list);
        System.out.println(list);
    }
    @Test
    public void testR() throws Exception {
        //建立连接
        RConnection rc=new RConnection();
        String functionPath = "D:\\works\\R\\cluster.R";
        rc.assign("functionPath",functionPath);
        rc.eval("source(functionPath)");

        rc.assign("n","100");
        rc.assign("k","5");
        rc.assign("dataPath","D:\\works\\data\\brandDistance.csv");

        REXP eval = rc.eval("cluster(dataPath,n,k)");
        String s[] = eval.asStrings();
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }
        rc.close();
    }
    @Test
    public void test1(){
        Object []  data;
        Integer[] integers = new Integer[4];
        integers[0] = 10;
        integers[1]= 20;
        data = integers;
        System.out.println(data[0]+" "+ data[1] + " "+ data[2]+" "+data[3]);
//        int[] ints = new int[3];
////        ints[0] = 15;
////        ints[1] = 16;
////        data = ints;
       String[] strings = new String[5];
        strings[0] = "100";
        strings[1] = "da";
        strings[2] = "adsfd";
        System.out.println(strings[0]+ " "+ strings[1] + " "+ strings[2] + " "+ strings[3] + "  "+ strings[4] );
    }
    @Test
    public void testCsv(){
        CommonData mydata = new CommonData();
//      mydata.setPath("d:\\works\\data\\brandSupportCount.csv");
        mydata.setPath("d:\\works\\data\\test1.csv");
        int [][] temp  = new int[5][5];
        temp[0][0] = 1;
        temp[0][1] = 2;
        temp[0][2] = 21;
        temp[0][3] = 13;
        temp[0][4] = 245;
        String [][] res = new String[5][5];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                res[i][j] = String.valueOf(temp[i][j]);
            }
        }
        CsvContent csvContent = new CsvContent();
        csvContent.setCsvDataMatrix(res);
        csvContent.setTitile("asfdzfdsdfg");
        mydata.setData(csvContent);
        MyDataWriter writer = new CsvDataWriter();
        writer.write(mydata);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = String.valueOf(100 - temp[i][j]);
                if(i == j){
                    res[i][j] = "0";
                }
            }
        }
        mydata.setPath("d:\\works\\data\\test2.csv");
        writer.write(mydata);
    }
    @Test
    public void testPython(){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("a='hello world'; ");
        interpreter.exec("print a;");
    }
}
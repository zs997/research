/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-01-17 15:27
 * copyright(c) 2019-2021 hust
 */

import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.dao.CsvDataWriter;
import cn.zs.dao.OriginDataReaderImp;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;

import org.junit.Test;
import org.python.core.PyFunction;

import org.python.core.PyList;

import org.python.util.PythonInterpreter;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import tspbenchmark.DistanceInstance;
import tspbenchmark.tspsabenchmark.Route;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-01-17 15:27
 **/
public class Test1 {
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
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.exec("a='hello world'; ");
//        interpreter.exec("print a;");
//        Process proc;
        Properties props = new Properties();
        props.put("python.home", "D:\\Program Files\\python39\\Lib\\site-packages");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.matplotlib.pyplot", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);

        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(2);
        x.add(3);
        x.add(4);
        x.add(5);
        ArrayList<ArrayList<Integer>> y = new ArrayList<>();
        y.add(new ArrayList<Integer>());
        y.get(0).add(11);
        y.get(0).add(12);
        y.get(0).add(13);
        y.get(0).add(14);
        y.get(0).add(15);

        PyList pyListx = new PyList(x);
        PyList pyListy = new PyList(y);


        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("F:\\works\\python\\plotImage.py");

        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get("plotImage", PyFunction.class);

        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        pyFunction.__call__(pyListx,pyListy);



    }
    @Test
    public void testPython2(){
        try {
            System.out.println("start");
            String[] args1=new String[]{"python","f:\\works\\python\\test13.py","5"};
            Process pr=Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testRplot() throws Exception {
        RConnection rc = new RConnection();
        int [] x = {1,2,3,4,5};
        int [] y ={1,2,3,4,5};
        rc.assign("x",x);
        rc.assign("y",y);

        REXP eval = rc.eval("plot(x,y)");

        rc.close();
    }

    @Test
    public void testTspbenchmarkData(){
        String s = "0 257 0 187 196 0 91 228 158 0 150 112" +
                " 96 120 0 80 196 88 77 63 0 130 167 59" +
                " 101 56 25 0 134 154 63 105 34 29 22 0" +
                " 243 209 286 159 190 216 229 225 0 185 86 124" +
                " 156 40 124 95 82 207 0 214 223 49 185 123" +
                " 115 86 90 313 151 0 70 191 121 27 83 47" +
                " 64 68 173 119 148 0 272 180 315 188 193 245" +
                " 258 228 29 159 342 209 0 219 83 172 149 79" +
                " 139 134 112 126 62 199 153 97 0 293 50 232" +
                " 264 148 232 203 190 248 122 259 227 219 134 0" +
                " 54 219 92 82 119 31 43 58 238 147 84 53" +
                " 267 170 255 0 211 74 81 182 105 150 121 108" +
                " 310 37 160 145 196 99 125 173 0 290 139 98" +
                " 261 144 176 164 136 389 116 147 224 275 178 154" +
                " 190 79 0 268 53 138 239 123 207 178 165 367" +
                " 86 187 202 227 130 68 230 57 86 0 261 43" +
                " 200 232 98 200 171 131 166 90 227 195 137 69" +
                " 82 223 90 176 90 0 175 128 76 146 32 76" +
                " 47 30 222 56 103 109 225 104 164 99 57 112" +
                " 114 134 0 250 99 89 221 105 189 160 147 349" +
                " 76 138 184 235 138 114 212 39 40 46 136 96" +
                " 0 192 228 235 108 119 165 178 154 71 136 262" +
                " 110 74 96 264 187 182 261 239 165 151 221 0" +
                " 121 142 99 84 35 29 42 36 220 70 126 55" +
                " 249 104 178 60 96 175 153 146 47 135 169 0";
        String[] split = s.split(" ");

        double[][] data = new double[24][24];
        int colNum = 1;
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0;j < colNum;j++){
                data[i][j] = Double.valueOf(split[count].trim());
                data[j][i] = data[i][j];
                count++;
            }
            colNum++;
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + ",");
            }
            System.out.println();
        }
    }
    @Test
    public void test2(){
        String s = "16 11 3 7 6 24 8 21 5 10 17 22 18 19 15 2 20 14 13 9 23 4 12 1";
        String[] split = s.split("\\s+");
        int[] solution = new int[split.length];

        for (int i = 0; i < solution.length; i++) {
            solution[i] = Integer.valueOf(split[i].trim())-1;
        }
        double distance = Route.calculDistance(solution, DistanceInstance.getInstance24());
        System.out.println(distance);
    }
    @Test
    public void testGroup(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> groupInfo = originDataReader.readCsv("f:\\works\\data\\groupinfo.csv");
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 1; i < groupInfo.size(); i++) {
            for (int j = 0; j < groupInfo.get(i).size(); j++) {
                temp.add(Integer.valueOf(groupInfo.get(i).get(j).trim()));
            }
        }
        Collections.sort(temp);
        System.out.println(temp);
    }
}



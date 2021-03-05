/**
 * projectName: research
 * fileName: Kmeans.java
 * packageName: cn.cn.cn.cn.zs.algorithm.cluster
 * date: 2021-01-19 22:24
 * copyright(c) 2019-2021 hust
 */
package cn.zs.service;

import cn.zs.dao.CsvDataWriter;
import cn.zs.dao.MyDataWriter;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import cn.zs.tools.DataConverter;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @version: V1.0
 * @author: cn.cn.cn.zs
 * @className: Kmeans
 * @packageName: cn.cn.cn.cn.zs.algorithm.cluster
 * @data: 2021-01-19 22:24
 * 调用R语言聚类库 将聚类结果 存储于csv
 **/

public class Hcluster {
    /**
     * 调用R库函数  将前n号货物聚类
     * @param：functionPath R函数所在目录  "f:\\works\\R\\cluster.R"     *
     * @param:dataPath 数据所在目录  "f:\\works\\data\\brandDistance.csv"
     * @param:n 选择前n号货物
     * @param：k 聚类k组
     * @para：destination 结果目录 具体到文件名 "f:\\works\\data\\groupinfo.csv"
     * */
    public void generateItemGroupByR(String functionPath,String dataPath,int n,int k,String destination) throws Exception {
        //建立连接
        RConnection rc = new RConnection();
        rc.assign("functionPath",functionPath);
        rc.eval("source(functionPath)");

        rc.assign("n",String.valueOf(n));
        rc.assign("k",String.valueOf(k));
        rc.assign("dataPath",dataPath);

        REXP eval = rc.eval("cluster(dataPath,n,k)");
        //返回结果是 组别 每一位s[i]是第i个属于那个组别
        String s[] = eval.asStrings();
        HashMap<String,ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length; i++) {
          if (map.containsKey(s[i])){
              ArrayList<Integer> list = map.get(s[i]);
              list.add(i);
          }else {
              ArrayList<Integer> list = new ArrayList<>();
              list.add(i);
              map.put(s[i],list);
          }
        }
        Set<String> strings = map.keySet();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (String string : strings) {
           // System.out.println(string+"zxcv");
            ArrayList<Integer> list = map.get(string);
            res.add(list);
          //  System.out.println(list);
        }
        String[][] csvDataMatrix = DataConverter.list2Matrix(res);
        CsvContent csvContent = new CsvContent();
        csvContent.setTitile("nothing but a tittle");
        csvContent.setCsvDataMatrix(csvDataMatrix);
        CommonData commonData = new CommonData();
        commonData.setData(csvContent);
        commonData.setPath(destination);
        MyDataWriter myDataWriter = new CsvDataWriter();
        myDataWriter.write(commonData);
        rc.close();
    }
}
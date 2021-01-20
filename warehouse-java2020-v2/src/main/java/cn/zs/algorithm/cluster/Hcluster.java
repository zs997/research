/**
 * projectName: research
 * fileName: Kmeans.java
 * packageName: cn.zs.algorithm.cluster
 * date: 2021-01-19 22:24
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.cluster;
import cn.zs.daoImp.CsvDataWriter;
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
 * @author: zs
 * @className: Kmeans
 * @packageName: cn.zs.algorithm.cluster
 * @data: 2021-01-19 22:24
 * 调用R语言聚类库 将聚类结果 存储于csv
 **/
public class Hcluster {
    /**
     * 调用R库函数  将前n号货物聚类
     * @param：source 读取的距离矩阵
     * @param:n 距离矩阵前n行列，频率前n的货物
     * @param: destination
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

        CsvDataWriter csvDataWriter = new CsvDataWriter();
        csvDataWriter.write(commonData);
        rc.close();
    }
}
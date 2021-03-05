/**
 * projectName: research
 * fileName: Kmeans.java
 * packageName: cn.cn.cn.cn.zs.algorithm.cluster
 * date: 2021-01-19 22:24
 * copyright(c) 2019-2021 hust
 */
package cn.zs.service;

import cn.zs.dao.MyDataWriter;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import cn.zs.pojo.SilhouetteParam;
import cn.zs.tools.DataConverter;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.ArrayList;
import java.util.Collections;
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
    public  ArrayList<ArrayList<Integer>> generateItemGroupByR(String functionPath,String dataPath,int n,int k,String destination) throws Exception {
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
        MyDataWriter myDataWriter = new MyDataWriter();
        myDataWriter.write(commonData);
        rc.close();
        return  res;
    }

    /**
     * 对阵矩阵标准化 不考虑对角元素 减去均值 除以方差
     */
    public double [][] zeroMeanNormalization(double [][] matrix){
        double sum = 0.0;
        double count = 0;
        double[][] res = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
                count++;
                sum += matrix[i][j];
            }
        }
        double avg = sum/count;

        double sumSquare = 0.0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
               sumSquare += (matrix[i][j]-avg)*(matrix[i][j]-avg);
            }
        }
        double s = Math.sqrt(sumSquare/(count-1));

        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
               res[i][j] = (matrix[i][j]-avg)/s;
               res[j][i] = res[i][j];
            }
        }
        return  res;
    }

    /**
     *
     * */
    public double [][] minMaxNormalization(double [][] matrix){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double[][] res = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
                min = Math.min(min,matrix[i][j]);
                max = Math.max(max,matrix[i][j]);
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
              res[i][j] = (matrix[i][j]-min)/(max-min);
              res[j][i] = res[i][j];
            }
        }
        return  res;
    }

    //计算聚类结果的轮廓度
    //不适合一个组为一组情况
    public SilhouetteParam calculSilhouette(ArrayList<ArrayList<Integer>> groupInfo, double [][] distanceMatrix){
        SilhouetteParam silhouetteParam = new SilhouetteParam();
      //  double[][] silhouette
        double sumSi = 0.0;
        ArrayList<ArrayList<Double>> itemSilhouette = new ArrayList<>();
        ArrayList<Double> avgGroupSilhouette = new ArrayList<>();



        for (int groupIndex = 0; groupIndex < groupInfo.size(); groupIndex++) {
            ArrayList<Integer> groupi = groupInfo.get(groupIndex);
            double sumGroupi = 0.0;

            ArrayList<Double> list = new ArrayList<>();
            for (int itemIndex = 0; itemIndex < groupi.size(); itemIndex++) {
                //货物编号 对该货物计算 轮廓
                Integer item = groupi.get(itemIndex);
                //1.计算本组与该货物距离之和
                double ai = 0.0;
                int countAi = 0;
                for (int k = 0; k < groupi.size(); k++) {
                    if (k == itemIndex){
                        continue;
                    }
                    Integer item2 = groupi.get(k);
                    if (item == item2){
                        continue;
                    }
                    ai += distanceMatrix[item][item2];
                    countAi++;
                }
                if (countAi == 0){
                    ai = 0;
                }else {
                    ai = ai/countAi;
                }

                double bi = Double.MAX_VALUE;
                for (int k = 0; k < groupInfo.size(); k++) {
                    if (k == groupIndex){
                        continue;
                    }
                    double biTemp = 0.0;
                    ArrayList<Integer> otherGroup = groupInfo.get(k);
                    if (otherGroup == groupi){
                        continue;
                    }
                    for (int itemIndex2 = 0; itemIndex2 < otherGroup.size(); itemIndex2++) {
                        Integer item2 = otherGroup.get(itemIndex2);
                        biTemp += distanceMatrix[item][item2];
                    }
                    biTemp = biTemp/otherGroup.size();
                    if (bi > biTemp){
                        bi = biTemp;
                    }
                }
                //样本item的轮廓度
                double si = (bi -ai)/Math.max(ai,bi);
                if (countAi == 0){
                    si = 0.0;
                }
                list.add(si);
                sumGroupi += si;
            }
            Collections.sort(list,Collections.reverseOrder());
            itemSilhouette.add(list);
            avgGroupSilhouette.add(sumGroupi/groupi.size());
            sumSi += sumGroupi;
        }
        silhouetteParam.setAvgSilhouette(sumSi/distanceMatrix.length);
        silhouetteParam.setAvgGroupSilhouette(avgGroupSilhouette);
        silhouetteParam.setItemSilhouette(itemSilhouette);
        return silhouetteParam;
    }
}
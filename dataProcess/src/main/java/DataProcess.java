/**
 * projectName: research
 * fileName: Main.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-01 10:55
 * copyright(c) 2019-2021 hust
 */
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import cn.zs.pojo.SilhouetteParam;
import cn.zs.pojo.Item;
import cn.zs.service.DataService;
import cn.zs.service.Hcluster;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Main
 * @packageName: PACKAGE_NAME
 * @data: 2021-03-01 10:55
 **/
public class DataProcess {
    static DataService dataService;
    static MyDataWriter myDataWriter;
    static OriginDataReader originDataReader;

    static String baseDir = "D:\\works\\data\\all";
    static {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        dataService = ac.getBean(DataService.class);
        myDataWriter = ac.getBean(MyDataWriter.class);
        originDataReader = ac.getBean(OriginDataReader.class);
    }
    public static void main(String[] args) throws Exception {

      //  silhouettePlot(5);
        testSilhouette();

    }
    public static void silhouettePlot(int k) throws Exception {
        ArrayList<String> strings = originDataReader.readTxt(baseDir + "\\warehouseStructure.txt");
        String s = strings.get(strings.size() - 1);
        String[] datas =s.split("\\s+");
        //不必加异常处理 异常直接退出
        int n = Integer.valueOf(datas[0])*Integer.valueOf(datas[1]);
        System.out.println("n= " + n);
        double[][] distanceMatrix = originDataReader.readDistanceMatrix(baseDir + "\\brandDistanceMinMax.csv", n);
        Hcluster hcluster = new Hcluster();
        ArrayList<ArrayList<Integer>> groups = hcluster.generateItemGroupByR(baseDir + "\\cluster.R",
                baseDir + "\\brandDistanceMinMax.csv", n, k, baseDir + "\\groupInfo\\goupInfo" + k + ".csv");
        SilhouetteParam silhouetteParam = hcluster.calculSilhouette(groups, distanceMatrix);
        System.out.println("聚类数目：" + k);
        System.out.println("总平均值:");
        System.out.println(silhouetteParam.getAvgSilhouette());
        ArrayList<Double> avgGroupSilhouette = silhouetteParam.getAvgGroupSilhouette();
        for (int i = 0; i < avgGroupSilhouette.size(); i++) {
            System.out.println("第"+i+"簇平均值：");
            System.out.println(avgGroupSilhouette.get(i));
        }
        System.out.println("个体si值");
        ArrayList<ArrayList<Double>> itemSilhouette = silhouetteParam.getItemSilhouette();
        for (int i = 0; i < itemSilhouette.size(); i++) {
            ArrayList<Double> list = itemSilhouette.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.println(list.get(j));
            }
            System.out.println();
        }
    }
    public static void testSilhouette() throws Exception {

        ArrayList<String> strings = originDataReader.readTxt(baseDir + "\\warehouseStructure.txt");
        String s = strings.get(strings.size() - 1);
        String[] datas =s.split("\\s+");
        //不必加异常处理 异常直接退出
        int n = Integer.valueOf(datas[0])*Integer.valueOf(datas[1]);
        System.out.println("n= " + n);
        double[][] distanceMatrix = originDataReader.readDistanceMatrix(baseDir + "\\brandDistanceMinMax.csv", n);
        Hcluster hcluster = new Hcluster();
        for(int k = 2;k < 40;k++){
            ArrayList<ArrayList<Integer>> groups = hcluster.generateItemGroupByR(baseDir + "\\cluster.R",
                    baseDir + "\\brandDistanceMinMax.csv", n, k, baseDir + "\\groupInfo\\goupInfo" + k + ".csv");
            SilhouetteParam silhouetteParam = hcluster.calculSilhouette(groups, distanceMatrix);
            System.out.println(silhouetteParam.getAvgSilhouette());
        }
    }
    public static void testmatrix(){
        double[][] distanceMatrix = originDataReader.readDistanceMatrix("D:\\works\\data\\all\\brandDistance.csv", 3);
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                System.out.print(distanceMatrix[i][j]+" ");
            }
            System.out.println();
        }
        Hcluster hcluster = new Hcluster();
        double[][] doubles = hcluster.zeroMeanNormalization(distanceMatrix);
        for (int i = 0; i < doubles.length; i++) {
            for (int j = 0; j < doubles[i].length; j++) {
                System.out.print(doubles[i][j]+" ");
            }
            System.out.println();
        }

        double[][] doubles1 = hcluster.minMaxNormalization(distanceMatrix);
        for (int i = 0; i < doubles1.length; i++) {
            for (int j = 0; j < doubles1[i].length; j++) {
                System.out.print(doubles1[i][j]+" ");
            }
            System.out.println();
        }

        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                System.out.print(distanceMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void normalizeData(){
        ArrayList<String> strings = originDataReader.readTxt(baseDir + "\\warehouseStructure.txt");
        String s = strings.get(strings.size() - 1);
        String[] datas =s.split("\\s+");
        //不必加异常处理 异常直接退出
        int n = Integer.valueOf(datas[0])*Integer.valueOf(datas[1]);
        System.out.println("n= " + n);

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDir+"\\brandDistance.csv");
        double[][] distanceMatrix = new double[n][n];
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(arrayLists.get(0).get(i-1)+",");
            for (int j = 0; j < n; j++) {
                distanceMatrix[i-1][j] = Double.valueOf(arrayLists.get(i).get(j).trim());
            }
        }
        String title = sb.substring(0, sb.length() - 1);

        Hcluster hcluster = new Hcluster();
        double[][] matrixZeroMean = hcluster.zeroMeanNormalization(distanceMatrix);
        double[][] matrixMinMax = hcluster.minMaxNormalization(distanceMatrix);

        CommonData commonData = new CommonData();
        commonData.setPath(baseDir+"\\brandDistanceZeroMean.csv");
        CsvContent csvContent = new CsvContent();
        csvContent.setCsvDataMatrix(matrixZeroMean);
        csvContent.setTitile(title);
        commonData.setData(csvContent);
        myDataWriter.write(commonData);

        commonData.setPath(baseDir+"\\brandDistanceMinMax.csv");
        csvContent.setCsvDataMatrix(matrixMinMax);
        csvContent.setTitile(title);
        myDataWriter.write(commonData);
    }
    public static void modifyDistanceMatrix(){
        ArrayList<String> strings = originDataReader.readTxt(baseDir + "\\warehouseStructure.txt");
        String s = strings.get(strings.size() - 1);
        String[] datas =s.split("\\s+");
        //不必加异常处理 异常直接退出
        int n = Integer.valueOf(datas[0])*Integer.valueOf(datas[1]);
        System.out.println("n= " + n);

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDir+"\\brandDistance.csv");
        double[][] distanceMatrix = new double[n][n];
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(arrayLists.get(0).get(i-1)+",");
            for (int j = 0; j < n; j++) {
                distanceMatrix[i-1][j] = Double.valueOf(arrayLists.get(i).get(j).trim());
            }
        }
        double min = Double.MAX_VALUE;
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = i+1; j < distanceMatrix[i].length; j++) {
                min = Math.min(min,distanceMatrix[i][j]);
            }
        }
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = i+1; j < distanceMatrix[i].length; j++) {
                distanceMatrix[i][j] = distanceMatrix[i][j]-min;
                distanceMatrix[j][i] =distanceMatrix[i][j];
            }
        }
        String title = sb.substring(0, sb.length() - 1);
        CommonData commonData = new CommonData();
        commonData.setPath(baseDir+"\\brandDistance2.csv");
        CsvContent csvContent = new CsvContent();
        csvContent.setCsvDataMatrix(distanceMatrix);
        csvContent.setTitile(title);
        commonData.setData(csvContent);
        myDataWriter.write(commonData);
    }

    /**
     * 产生测试集
     * */
    public static void generateTestData(){
        int k = 400;
        List<Item> trainItemList = originDataReader.readItemList(baseDir + "\\itemInfoh.csv");
        List<Item> testItemList = originDataReader.readItemList(baseDir + "\\itemInfon.csv");

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDir + "\\brandDistancen.csv");
        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
        for (int i = 1; i < arrayLists.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                temp.add(arrayLists.get(i).get(j));
            }
            matrix.add(temp);
        }
        dataService.generateTestData(trainItemList,k,testItemList,matrix,baseDir);
    }

}
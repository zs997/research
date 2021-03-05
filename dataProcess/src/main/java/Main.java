/**
 * projectName: research
 * fileName: Main.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-01 10:55
 * copyright(c) 2019-2021 hust
 */
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import cn.zs.pojo.Item;
import cn.zs.service.DataService;
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
public class Main {
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
    public static void main(String[] args) {

        double[][] distanceMatrix = readDistanceMatrix("D:\\works\\data\\all\\SilhouetteTest\\mydata1.csv");
        ArrayList<ArrayList<Integer>> groupInfo = readGroupInfo("D:\\works\\data\\all\\SilhouetteTest\\groupInfoTest.csv");
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                System.out.print(distanceMatrix[i][j]+" ");
            }
            System.out.println();
        }
        for (int i = 0; i < groupInfo.size(); i++) {
            for (int j = 0; j < groupInfo.get(i).size(); j++) {
                System.out.print(groupInfo.get(i).get(j)+" ");
            }
            System.out.println();
        }
        System.out.println(calculSilhouette(groupInfo,distanceMatrix));
    }
    /**
     * "D:\\works\\data\\all\\SilhouetteTest\\groupInfoTest.csv"
     * */
    public static ArrayList<ArrayList<Integer>> readGroupInfo(String path){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(path);
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 1; i < arrayLists.size(); i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                list.add(Integer.valueOf(arrayLists.get(i).get(j).trim()));
            }
            res.add(list);
        }
        return res;
    }

    /**
     * "D:\\works\\data\\all\\SilhouetteTest\\brandDistance1.csv"
     * */
    public static double[][] readDistanceMatrix(String path){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(path);
        double[][] res = new double[arrayLists.size()-1][];
        for (int i = 1; i < arrayLists.size(); i++) {
            res[i-1] = new double[arrayLists.get(i).size()];
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                res[i-1][j] = Double.valueOf(arrayLists.get(i).get(j).trim());
            }
        }
        return res;
    }


    /**
     * @description: 文本 初始化 仓库结构
     * @Param:k 分组数目
     * */
    public static void initParams(int k) throws Exception {
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDir + "\\groupinfoTrain.csv");

    }
    //计算聚类结果的轮廓度
    //不适合一个组为一组情况
    public static double calculSilhouette(ArrayList<ArrayList<Integer>> groupInfo,double [][] distanceMatrix){
        double sumSi = 0;
        for (int groupIndex = 0; groupIndex < groupInfo.size(); groupIndex++) {
            ArrayList<Integer> groupi = groupInfo.get(groupIndex);
            for (int itemIndex = 0; itemIndex < groupi.size(); itemIndex++) {
                //货物编号 对该货物计算 轮廓
                Integer item = groupi.get(itemIndex);
                //1.计算本组与该货物距离之和
                double ai = 0.0;
                for (int k = 0; k < groupi.size(); k++) {
                    if (k == itemIndex){
                        continue;
                    }
                    Integer item2 = groupi.get(k);
                    ai += distanceMatrix[item][item2];
                }
                ai = ai/(groupi.size()-1);

                double bi = Double.MAX_VALUE;
                for (int k = 0; k < groupInfo.size(); k++) {
                    if (k == groupIndex){
                        continue;
                    }
                    double biTemp = 0.0;
                    ArrayList<Integer> otherGroup = groupInfo.get(k);
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
                sumSi += si;
             }
        }
        return sumSi/distanceMatrix.length;
    }

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
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
    public static void main(String[] args) {

//        double[][] distanceMatrix = originDataReader.readDistanceMatrix("D:\\works\\data\\all\\SilhouetteTest\\mydata1.csv");
//        ArrayList<ArrayList<Integer>> groupInfo = originDataReader.readGroupInfo("D:\\works\\data\\all\\SilhouetteTest\\groupInfoTest.csv");

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
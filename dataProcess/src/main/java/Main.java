/**
 * projectName: research
 * fileName: Main.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-01 10:55
 * copyright(c) 2019-2021 hust
 */
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.pojo.Item;
import cn.zs.service.DataService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: zs
 * @className: Main
 * @packageName: PACKAGE_NAME
 * @data: 2021-03-01 10:55
 **/
public class Main {
    static DataService dataService;
    static MyDataWriter myDataWriter;
    static OriginDataReader originDataReader;

    static String dataBaseDir = "D:\\works\\data\\test";
    static {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        dataService = ac.getBean(DataService.class);
        myDataWriter = ac.getBean(MyDataWriter.class);
        originDataReader = ac.getBean(OriginDataReader.class);
    }
    public static void main(String[] args) {
       // dataService.generateItemList(DataBaseDir+"\\itemInfo.csv");
        //dataService.generateSupportCount(DataBaseDir);

        int k = 400;
        List<Item> trainItemList = originDataReader.readItemList(dataBaseDir + "\\itemInfoh.csv");
        List<Item> testItemList = originDataReader.readItemList(dataBaseDir + "\\itemInfon.csv");

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(dataBaseDir + "\\brandDistancen.csv");
        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
        for (int i = 1; i < arrayLists.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                temp.add(arrayLists.get(i).get(j));
            }
            matrix.add(temp);
        }
        dataService.generateTestData(trainItemList,k,testItemList,matrix,dataBaseDir);

    }
}
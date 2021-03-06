package cn.zs;
import cn.zs.algorithm.cluster.Hcluster;
import cn.zs.algorithm.eda.EDA;
import cn.zs.algorithm.ga.Individual;
import cn.zs.algorithm.component.Params;
import cn.zs.algorithm.ga.Population;
import cn.zs.algorithm.component.*;
import cn.zs.algorithm.localsearcheda.LocalEDA;
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import cn.zs.pojo.Item;
import cn.zs.pojo.PickParam;
import cn.zs.service.DataService;
import cn.zs.tools.LineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.*;
import static cn.zs.algorithm.component.Params.*;
public class Main {
    static DataService dataService;
    static  OriginDataReader originDataReader;
    static {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        dataService = ac.getBean(DataService.class);
        originDataReader = ac.getBean(OriginDataReader.class);
    }
    public static void main(String args[]) throws Exception {
    //initBenchmark();
      initParams(6);
//    SA sa = new SA(100, 0.000000005, 100, 0.95,ColumnR.class);
//    sa.doSA();
//    GeneticAlgorithm<ColumnS> columnRGeneticAlgorithm = new GeneticAlgorithm<>(1000
//                ,150, 0.001, 0.9, 15, 5,ColumnS.class);
//    columnRGeneticAlgorithm.doGA();
    LocalEDA localEda = new LocalEDA(150, 15 * 4, 0.1,
            1000, 15, ColumnS.class);
    localEda.doEDA();
      EDA eda = new EDA(150, 15 * 4, 0.1, 1000,
              15, ColumnS.class);
      eda.doEDA();
 //  testIndivual();
    }

    /**
     * @description: 文本 初始化 仓库结构
     * */
    public static void initParams(int k) throws Exception {

        ArrayList<String>  ss = originDataReader.readTxt("f:\\works\\data\\warehouseStructure.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));

        List<Item> itemList = dataService.getItemList();
        Params.initItemList(itemList);
        // double[] pickf = dataService.getBenchmarkPickData(20, 0.5, 0.3, 0.2).getPickf();
        //Params.inititemPickFreq(pickf);
        Params.calculNonEmptyProb();
        new Hcluster().generateItemGroupByR("f:\\works\\R\\cluster.R"
                    ,"f:\\works\\data\\brandDistance.csv",storageCount,k,"f:\\works\\data\\groupinfo.csv");

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("f:\\works\\data\\groupinfo.csv");
        Params.initGroupInfo(arrayLists);
    }
    public static void initBenchmark(){

        ArrayList<String>  ss = originDataReader.readTxt("f:\\works\\data\\warehouseStructure.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));

        PickParam benchmarkPickData = dataService.getBenchmarkPickData(20, 0.5, 0.3, 0.2);
        double[] pickf = benchmarkPickData.getPickf();
        Params.setItemPickFreq(pickf);
        Params.calculNonEmptyProb();
    }

    /**
     * 测试随机可行解性能  算法一定要比随机生成的好 才行啊
     * */
    @Deprecated
    public static void randomSolution(){
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<ArrayList<Double>> y = new ArrayList<>();
        ArrayList<Double> y1 = new ArrayList<>();
        ArrayList<String> stackBar = new ArrayList<>();
        stackBar.add("one");
        y.add(y1);
        for (int i = 0; i < 200000; i++) {
            x.add(i);
            Individual individual = new Individual(ColumnR.class);
            individual.calculFitness();
            y1.add(individual.getCost());
            System.out.println(i+"  "+ individual.getCost());
        }
        Collections.sort(y1,Comparator.reverseOrder());
        System.out.println(y1.get(0));
        System.out.println(y1.get(y1.size()-1));
        LineView.printPic(x,y,stackBar);
    }

    /**
     * 检查一组库位分配可行解的目标函数值及遗传算法个体设计  是否正确
     * 正确
     * */
    @Deprecated
    public static void  testIndivual(){
        int [][] assignMatrix = new int[15][4];
        //第四维 是每行的分配方式  为0 正常分配 为1 对称分配
        assignMatrix[0][0] = 24;
        assignMatrix[0][1] = 0;
        assignMatrix[0][2] = 0;
        assignMatrix[0][3] = 0;

        assignMatrix[1][0] = 2;
        assignMatrix[1][1] = 10;
        assignMatrix[1][2] = 12;
        assignMatrix[1][3] = 1;

        assignMatrix[2][0] = 2;
        assignMatrix[2][1] = 8;
        assignMatrix[2][2] = 14;
        assignMatrix[2][3] = 1;

        assignMatrix[3][0] = 2;
        assignMatrix[3][1] = 8;
        assignMatrix[3][2] = 14;
        assignMatrix[3][3] = 1;

        assignMatrix[4][0] = 2;
        assignMatrix[4][1] = 8;
        assignMatrix[4][2] = 14;
        assignMatrix[4][3] = 1;

        assignMatrix[5][0] = 2;
        assignMatrix[5][1] = 8;
        assignMatrix[5][2] = 14;
        assignMatrix[5][3] = 1;

        assignMatrix[6][0] = 2;
        assignMatrix[6][1] = 8;
        assignMatrix[6][2] = 14;
        assignMatrix[6][3] = 1;

        assignMatrix[7][0] = 2;
        assignMatrix[7][1] = 8;
        assignMatrix[7][2] = 14;
        assignMatrix[7][3] = 1;

        assignMatrix[8][0] = 2;
        assignMatrix[8][1] = 8;
        assignMatrix[8][2] = 14;
        assignMatrix[8][3] = 1;

        assignMatrix[9][0] = 2;
        assignMatrix[9][1] = 8;
        assignMatrix[9][2] = 14;
        assignMatrix[9][3] = 1;

        assignMatrix[10][0] = 2;
        assignMatrix[10][1] = 8;
        assignMatrix[10][2] = 14;
        assignMatrix[10][3] = 1;

        assignMatrix[11][0] = 2;
        assignMatrix[11][1] = 8;
        assignMatrix[11][2] = 14;
        assignMatrix[11][3] = 1;

        assignMatrix[12][0] = 2;
        assignMatrix[12][1] = 8;
        assignMatrix[12][2] = 14;
        assignMatrix[12][3] = 1;

        assignMatrix[13][0] = 0;
        assignMatrix[13][1] = 10;
        assignMatrix[13][2] = 14;
        assignMatrix[13][3] = 1;

        assignMatrix[14][0] = 24;
        assignMatrix[14][1] = 0;
        assignMatrix[14][2] = 0;
        assignMatrix[14][3] = 0;
//        OriginDataReader originDataReader = new OriginDataReaderImp();
//        ArrayList<String>  ss = originDataReader.readTxt("f:\\works\\data\\warehouseStructure.txt");
//        Params.initWarehouseStructure(ss.get(ss.size() - 1));
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        dataService = ac.getBean(DataService.class);
        PickParam pickParam = dataService.getBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.setItemPickFreq(pickParam.getPickf());
        Params.calculNonEmptyProb();
        int storageA = pickParam.getStorageA();
        int storageB = pickParam.getStorageB();
        int storageC = pickParam.getStorageC();
        int indexA = 0;
        int indexB = storageA;
        int indexC = storageA + storageB;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < assignMatrix.length; i++) {
            if (assignMatrix[i][3] == 0){
                //每行的A
                for (int j = 0; j < assignMatrix[i][0]; j++) {
                    list.add(indexA);
                    indexA++;
                }
                //每行B
                for (int k = 0; k < assignMatrix[i][1]; k++) {
                    list.add(indexB);
                    indexB++;
                }
                //每行C
                for (int m = 0; m < assignMatrix[i][2]; m++) {
                    list.add(indexC);
                    indexC++;
                }
            }else {
                int numA = assignMatrix[i][0];
                int numB = assignMatrix[i][1];
                int halfA = (int)Math.ceil(numA/2.0);
                int halfB = (int)Math.ceil(numB/2.0);
                for (int j = 0; j < halfA; j++) {
                    list.add(indexA);
                    indexA++;
                }
                for (int j = 0; j < halfB; j++) {
                    list.add(indexB);
                    indexB++;
                }
                for (int j = 0; j < assignMatrix[i][2]; j++) {
                    list.add(indexC);
                    indexC++;
                }
                for (int j = 0; j < numB - halfB; j++) {
                    list.add(indexB);
                    indexB++;
                }
                for (int j = 0; j < numA - halfA; j++) {
                    list.add(indexA);
                    indexA++;
                }
            }
        }
        int [] temp = {
                5, 48, 70, 54, 49, 53, 166, 111, 179, 83, 153, 138, 162, 160, 358, 221, 350, 256, 290, 230, 303, 355, 205, 203, 0, 20, 29, 43, 45, 21, 75, 101, 178, 116, 91, 174, 148, 265, 204, 248, 359, 240, 261, 267, 302, 190, 292, 263, 60, 59, 1, 56, 67, 38, 154, 72, 85, 173, 129, 208, 146, 194, 264, 233, 319, 236, 282, 279, 242, 293, 209, 337, 19, 71, 3, 65, 33, 69, 145, 58, 80, 135, 137, 122, 81, 149, 110, 269, 232, 280, 351, 235, 243, 322, 313, 294, 28, 66, 4, 6, 25, 133, 97, 121, 96, 141, 140, 106, 299, 296, 343, 283, 191, 180, 239, 332, 181, 238, 336, 189, 26, 23, 32, 55, 10, 78, 136, 113, 108, 109, 152, 339, 89, 120, 281, 144, 312, 196, 353, 219, 285, 223, 255, 249, 11, 18, 44, 46, 24, 14, 12, 151, 98, 92, 74, 168, 139, 147, 171, 201, 316, 231, 213, 328, 277, 320, 259, 228, 9, 62, 36, 13, 39, 61, 47, 167, 126, 119, 156, 104, 102, 99, 346, 237, 212, 315, 197, 195, 275, 252, 287, 202, 27, 73, 34, 15, 51, 68, 172, 90, 100, 169, 210, 79, 163, 188, 132, 300, 330, 309, 216, 273, 229, 215, 185, 220, 64, 17, 35, 42, 63, 16, 164, 107, 117, 115, 276, 82, 314, 325, 246, 354, 254, 184, 308, 272, 200, 244, 321, 207, 30, 2, 57, 52, 40, 118, 41, 37, 192, 177, 105, 114, 150, 158, 352, 349, 298, 324, 198, 297, 234, 333, 284, 345, 50, 31, 84, 22, 161, 131, 123, 165, 130, 128, 94, 270, 76, 291, 245, 327, 268, 278, 318, 227, 342, 222, 250, 226, 7, 134, 8, 176, 88, 112, 87, 95, 157, 86, 225, 103, 338, 260, 356, 288, 258, 217, 305, 251, 348, 289, 344, 271, 175, 77, 127, 124, 125, 142, 170, 159, 329, 199, 262, 317, 307, 323, 304, 286, 274, 182, 326, 295, 214, 257, 340, 253, 143, 155, 93, 331, 301, 335, 193, 347, 211, 183, 241, 206, 306, 357, 224, 247, 310, 334, 218, 266, 341, 187, 311, 186
        };
        ArrayList<Integer> chromo = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            chromo.add(temp[i]);
        }
        Individual<ColumnR> individual = new Individual<>(ColumnR.class);
        individual.setChromosome(chromo);
        System.out.println(chromo);
        individual.calculFitness();
        System.out.println(storageCount);
        for (int i = 0; i < itemPickFreq.length; i++) {
            System.out.println(itemPickFreq[i]);
        }
        System.out.println(individual.getCost());
    }

}

package cn.zs;
import cn.zs.algorithm.cluster.Kmeans;
import cn.zs.algorithm.ga.GeneticAlgorithm;
import cn.zs.algorithm.ga.Individual;
import cn.zs.algorithm.component.Params;
import cn.zs.algorithm.ga.Population;
import cn.zs.algorithm.component.*;
import cn.zs.dao.OriginDataReader;
import cn.zs.daoImp.OriginDataReaderImp;
import cn.zs.pojo.Item;
import cn.zs.pojo.PickParam;
import cn.zs.algorithm.sa.SA;
import cn.zs.service.DataService;
import cn.zs.view.LineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.*;
import static cn.zs.algorithm.component.Params.*;
public class Main {
    static DataService dataService;
    public static void main(String args[]){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        dataService = ac.getBean(DataService.class);
        initParams();

        //doSA();
        doGA();

    }
    public static void doSA(){
        SA sa = new SA(100, 0.000000005, 100, 0.95);
        sa.doSA();
    }
    public static void doGA(){
         GeneticAlgorithm<ColumnR> columnRGeneticAlgorithm = new GeneticAlgorithm<>(5000
                 ,100, 0.001, 0.9, 2, 5);
         columnRGeneticAlgorithm.doGA(ColumnR.class);
    }
    /**
     * @description: 文本 初始化 仓库结构
     * */
    public static void initParams(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<String>  ss = originDataReader.readTxt("D:\\works\\data\\warehouseStructure.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));

        List<Item> itemList = dataService.getItemList();
        Params.initItemList(itemList);
        // double[] pickf = dataService.getBenchmarkPickData(20, 0.5, 0.3, 0.2).getPickf();
        //Params.inititemPickFreq(pickf);

        Params.calculNonEmptyProb();

        try {
            new Kmeans().generateItemGroupByR("D:\\works\\R\\cluster.R"
                    ,"D:\\works\\data\\brandDistance.csv",storageCount,5,"D:\\works\\data\\groupinfo.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("D:\\works\\data\\groupinfo.csv");
        Params.initGroupInfo(arrayLists);
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
            Individual individual = new Individual();
            individual.calculFitness(ColumnR.class);
            y1.add(individual.getCost());
            System.out.println(i+"  "+ individual.getCost());
        }
        Collections.sort(y1,Comparator.reverseOrder());
        System.out.println(y1.get(0));
        System.out.println(y1.get(y1.size()-1));
        LineView.printPic(x,y,stackBar);
    }
    /**
     * 测试遗传算法 种群正确性
     * 正确
     * */
    @Deprecated
    public static void testPopulation() {
        Population population = new Population(100);
        double populationFitness = 0;
        // Loop over population evaluating individuals and summing population fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += individual.calculFitness(ColumnR.class);
        }
        double avgFitness = populationFitness / population.size();
        population.setPopulationFitness(avgFitness);
        population.getFittest(0);
        System.out.println("平均适应度：" + population.getPopulationFitness());
        Individual[] individuals = population.getIndividuals();
        for (Individual individual : individuals) {
            System.out.println(individual.getCost() + " " + individual.getFitness());
        }
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

        PickParam pickParam = dataService.getBenchmarkPickData(20, 0.5, 0.3, 0.2);
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
//        int [] temp = {
//                335, 178, 160, 339, 103, 325, 332, 80, 314, 233, 89, 88, 341, 161, 45, 272, 74, 212, 173, 24, 355, 93, 310, 252, 71, 63, 7, 97, 8, 30, 28, 189, 66, 12, 77, 14, 60, 247, 162, 70, 23, 133, 99, 52, 224, 328, 34, 166, 337, 115, 144, 135, 276, 72, 278, 246, 291, 148, 123, 301, 183, 305, 290, 211, 172, 186, 98, 329, 340, 119, 240, 210, 264, 265, 121, 140, 151, 275, 286, 236, 200, 346, 249, 296, 90, 174, 73, 307, 149, 277, 47, 262, 300, 299, 126, 128, 9, 39, 146, 48, 55, 11, 61, 44, 3, 2, 42, 68, 65, 50, 21, 15, 59, 91, 27, 1, 171, 56, 25, 101, 43, 107, 118, 36, 92, 41, 176, 142, 69, 324, 343, 95, 168, 57, 6, 321, 67, 35, 199, 145, 281, 37, 29, 130, 17, 113, 205, 111, 158, 18, 62, 22, 159, 40, 53, 32, 26, 49, 13, 54, 4, 20, 64, 170, 33, 10, 31, 266, 116, 46, 86, 309, 136, 129, 0, 193, 345, 83, 117, 317, 120, 124, 5, 237, 58, 139, 250, 327, 38, 16, 51, 138, 258, 203, 114, 156, 125, 230, 127, 302, 245, 175, 269, 226, 297, 357, 229, 218, 137, 19, 190, 207, 204, 184, 108, 347, 96, 234, 81, 87, 102, 304, 112, 131, 79, 106, 227, 279, 331, 165, 154, 76, 143, 259, 192, 169, 221, 187, 311, 209, 163, 271, 213, 201, 243, 208, 153, 225, 152, 157, 82, 313, 122, 248, 349, 134, 334, 167, 267, 293, 181, 235, 206, 150, 273, 292, 78, 196, 242, 182, 185, 351, 231, 219, 254, 75, 257, 260, 238, 195, 132, 215, 282, 222, 191, 202, 110, 179, 312, 306, 194, 358, 164, 109, 188, 338, 320, 354, 322, 197, 105, 274, 263, 177, 330, 308, 303, 198, 232, 323, 287, 239, 94, 253, 280, 316, 284, 104, 155, 244, 241, 270, 141, 326, 356, 223, 180, 268, 251, 228, 315, 283, 84, 342, 256, 318, 261, 359, 289, 100, 147, 295, 220, 344, 298, 85, 217, 350, 294, 214, 216, 319, 333, 353, 352, 255, 288, 336, 348, 285
//
//        };
//        ArrayList<Integer> chromo = new ArrayList<>();
//        for (int i = 0; i < temp.length; i++) {
//            chromo.add(temp[i]);
//        }
        Individual<ColumnM> individual = new Individual<>();
        individual.setChromosome(list);
        System.out.println(list);
        individual.calculFitness(ColumnM.class);
        System.out.println(individual.getCost());
    }
    /**
     * 检验通道cost计算的正确性  numA numB本通道的货物
     * usedA usedB usedC  共计多少 算上本通道
     * */
    @Deprecated
    public static void testColumn(int no ,int usedA ,int usedB,int usedC,int numA,int numB){
        PickParam pickParam = dataService.getBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
        ColumnL columnL = new ColumnL();
        ArrayList<Integer> col = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        int i = 0;
        for (; i < numA; i++) {
            //编号为0 一定是A类货物
            col.add(i);
            //   set.add(i);
        }
        for (; i < numA + numB ; i++) {
            //编号为 storageA +1 一定是b类
            col.add(pickParam.getStorageA() + i - numA);
            //   set.add(pickParam.getStorageA() + i - numA);
        }
        for (;i < N;i++){
            col.add(pickParam.getStorageA() + pickParam.getStorageB() + i - numA - numB);
            //   set.add(pickParam.getStorageA() + pickParam.getStorageB() + i - numA - numB);
        }
        for (int j  = 0 ; j < usedA; j++) {
            set.add(j);
        }
        for (int j = pickParam.getStorageA(); j < pickParam.getStorageA() + usedB; j++) {
            set.add(j);
        }
        for(int j = pickParam.getStorageA() + pickParam.getStorageB();j <  pickParam.getStorageA() + pickParam.getStorageB() + usedC; j ++){
            set.add(j);
        }
        columnL.setLocations(col);
        columnL.calculCost(no,set,0,0,0);
        System.out.println(columnL.getCost());
    }
}

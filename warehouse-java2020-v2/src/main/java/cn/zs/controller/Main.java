package cn.zs.controller;
import cn.zs.algorithm.ga.GeneticAlgorithm;
import cn.zs.algorithm.ga.Individual;
import cn.zs.algorithm.ga.Params;
import cn.zs.algorithm.ga.Population;
import cn.zs.algorithm.component.*;
import cn.zs.dao.OriginDataReader;
import cn.zs.daoImp.OriginDataReaderImp;
import cn.zs.mapper.OrdersMapper;
import cn.zs.pojo.Item;
import cn.zs.pojo.PickParam;
import cn.zs.algorithm.sa.SA;
import cn.zs.service.OrdersService;
import cn.zs.view.LineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.*;
import static cn.zs.algorithm.ga.Params.*;
public class Main {
    //目前不能这样 spring加载晚于类加载 只有在web情况，让spring先加载才可以
   // @Autowired
   // Data2Mapper data2Mapper ;
    static  OrdersMapper ordersMapper;
    static OrdersService ordersService;
    static OriginDataReader originDataReader;

    public static void main(String args[]){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        originDataReader = ac.getBean(OriginDataReader.class);
        ordersService = ac.getBean(OrdersService.class);
        ordersMapper = ac.getBean(OrdersMapper.class);
       Main main = new Main();
        main.initWarehouseStructure("D:\\works\\data\\warehouseStructure.txt");
       //  main.initGroupInfo("D:\\works\\data\\groupinfo1.csv");
      //  ordersService.groupItem("","D:\\works\\data\\itemInfo1.csv",storageCount);
        //doSA();
     //doGA();
      //testIndivual();
     //   testPopulation();
    //    checkData(15,72,108,180,2,8);
        //每一组实验数据 都要重新分组  因为货物数目变化了

     //   main.checkData(5,8);
     //      randomSolution();
//      GeneticAlgorithm<ColumnR> columnRGeneticAlgorithm = new GeneticAlgorithm<>();
//      columnRGeneticAlgorithm.doGA(ColumnR.class);
    }
    public static void testPopulation(){
        ordersService.generateBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
        Population population = new Population(100);

        double populationFitness = 0;
        // Loop over population evaluating individuals and summing population fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += individual.calculFitness(ColumnR.class);
        }
        double avgFitness = populationFitness / population.size();
        population.setPopulationFitness(avgFitness);
        population.getFittest(0);
        System.out.println("平均适应度："+population.getPopulationFitness());

        Individual[] individuals = population.getIndividuals();
        for (Individual individual : individuals) {
            System.out.println(individual.getCost() + " "+ individual.getFitness());
        }

       // GeneticAlgorithm<ColumnR> ga = new GeneticAlgorithm<>();


    }
    public static void  testIndivual(){
        int [][] assignMatrix = new int[15][3];
        assignMatrix[0][0] = 5;
        assignMatrix[0][1] = 8;
        assignMatrix[0][2] = 11;

        assignMatrix[1][0] = 5;
        assignMatrix[1][1] = 8;
        assignMatrix[1][2] = 11;

        assignMatrix[2][0] = 5;
        assignMatrix[2][1] = 7;
        assignMatrix[2][2] = 12;

        assignMatrix[3][0] = 5;
        assignMatrix[3][1] = 7;
        assignMatrix[3][2] = 12;

        assignMatrix[4][0] = 5;
        assignMatrix[4][1] = 7;
        assignMatrix[4][2] = 12;

        assignMatrix[5][0] = 5;
        assignMatrix[5][1] = 7;
        assignMatrix[5][2] = 12;

        assignMatrix[6][0] = 5;
        assignMatrix[6][1] = 7;
        assignMatrix[6][2] = 12;

        assignMatrix[7][0] = 5;
        assignMatrix[7][1] = 7;
        assignMatrix[7][2] = 12;

        assignMatrix[8][0] = 5;
        assignMatrix[8][1] = 7;
        assignMatrix[8][2] = 12;

        assignMatrix[9][0] = 5;
        assignMatrix[9][1] = 7;
        assignMatrix[9][2] = 12;

        assignMatrix[10][0] = 5;
        assignMatrix[10][1] = 7;
        assignMatrix[10][2] = 12;

        assignMatrix[11][0] = 5;
        assignMatrix[11][1] = 7;
        assignMatrix[11][2] = 12;

        assignMatrix[12][0] = 5;
        assignMatrix[12][1] = 7;
        assignMatrix[12][2] = 12;

        assignMatrix[13][0] = 5;
        assignMatrix[13][1] = 7;
        assignMatrix[13][2] = 12;

        assignMatrix[14][0] = 2;
        assignMatrix[14][1] = 8;
        assignMatrix[14][2] = 14;

        PickParam pickParam = ordersService.generateBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
        int storageA = pickParam.getStorageA();
        int storageB = pickParam.getStorageB();
        int storageC = pickParam.getStorageC();
        int indexA = 0;
        int indexB = storageA;
        int indexC = storageA + storageB;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < assignMatrix.length; i++) {
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
        }
        int [] temp = {
                335, 178, 160, 339, 103, 325, 332, 80, 314, 233, 89, 88, 341, 161, 45, 272, 74, 212, 173, 24, 355, 93, 310, 252, 71, 63, 7, 97, 8, 30, 28, 189, 66, 12, 77, 14, 60, 247, 162, 70, 23, 133, 99, 52, 224, 328, 34, 166, 337, 115, 144, 135, 276, 72, 278, 246, 291, 148, 123, 301, 183, 305, 290, 211, 172, 186, 98, 329, 340, 119, 240, 210, 264, 265, 121, 140, 151, 275, 286, 236, 200, 346, 249, 296, 90, 174, 73, 307, 149, 277, 47, 262, 300, 299, 126, 128, 9, 39, 146, 48, 55, 11, 61, 44, 3, 2, 42, 68, 65, 50, 21, 15, 59, 91, 27, 1, 171, 56, 25, 101, 43, 107, 118, 36, 92, 41, 176, 142, 69, 324, 343, 95, 168, 57, 6, 321, 67, 35, 199, 145, 281, 37, 29, 130, 17, 113, 205, 111, 158, 18, 62, 22, 159, 40, 53, 32, 26, 49, 13, 54, 4, 20, 64, 170, 33, 10, 31, 266, 116, 46, 86, 309, 136, 129, 0, 193, 345, 83, 117, 317, 120, 124, 5, 237, 58, 139, 250, 327, 38, 16, 51, 138, 258, 203, 114, 156, 125, 230, 127, 302, 245, 175, 269, 226, 297, 357, 229, 218, 137, 19, 190, 207, 204, 184, 108, 347, 96, 234, 81, 87, 102, 304, 112, 131, 79, 106, 227, 279, 331, 165, 154, 76, 143, 259, 192, 169, 221, 187, 311, 209, 163, 271, 213, 201, 243, 208, 153, 225, 152, 157, 82, 313, 122, 248, 349, 134, 334, 167, 267, 293, 181, 235, 206, 150, 273, 292, 78, 196, 242, 182, 185, 351, 231, 219, 254, 75, 257, 260, 238, 195, 132, 215, 282, 222, 191, 202, 110, 179, 312, 306, 194, 358, 164, 109, 188, 338, 320, 354, 322, 197, 105, 274, 263, 177, 330, 308, 303, 198, 232, 323, 287, 239, 94, 253, 280, 316, 284, 104, 155, 244, 241, 270, 141, 326, 356, 223, 180, 268, 251, 228, 315, 283, 84, 342, 256, 318, 261, 359, 289, 100, 147, 295, 220, 344, 298, 85, 217, 350, 294, 214, 216, 319, 333, 353, 352, 255, 288, 336, 348, 285

        };
        ArrayList<Integer> chromo = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            chromo.add(temp[i]);
        }
        Individual<ColumnS> individual = new Individual<>();
        individual.setChromosome(chromo);
        System.out.println(chromo);
        individual.calculFitness(ColumnS.class);
        System.out.println(individual.getCost());
    }
    public static void doSA(){
        initItemList();
        //ordersService.generateBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
        SA.doSA();
    }
    public static void doGA(){
        initItemList();
        //ordersService.generateBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
        System.out.println(Params.nonEmptyProb);
        //每一组实验数据 都要重新分组  因为货物数目变化了
        // main.initGroupInfo("D:\\works\\data\\groupinfo.csv");
        //   main.checkData(5,8);
             GeneticAlgorithm<ColumnR> columnRGeneticAlgorithm = new GeneticAlgorithm<>();
             columnRGeneticAlgorithm.doGA(ColumnR.class);

    }
    public static void randomSolution(){
        ordersService.generateBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
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
     * 检验数据  numA numB本通道的货物
     * usedA usedB usedC  共计多少 算上本通道
     * */
    public static void checkData(int no ,int usedA ,int usedB,int usedC,int numA,int numB){
        PickParam pickParam = ordersService.generateBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.calculNonEmptyProb();
        ColumnR columnR = new ColumnR();

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

        columnR.setLocations(col);
        columnR.calculCost(no,set,0,0,0);

        System.out.println(columnR.getCost());
    }
    /**
     * @description: 初始化数据 将csv订单数据存到数据库 step1
     * */
    public void csv2databse(){
        ArrayList<ArrayList<String>> data = originDataReader.readCsv("D:\\works\\data\\data.csv");
        ordersService.save2database(data);
    }
    /**
    * @description：查询货物列表 包括编号 拣货概率 保存csv
    * */
    public void generateItemList(String path){
        ordersService.generateItemList(path);
    }
    private void generateSupportCount(String path) {
        ordersService.generateSupportCount(path);
    }
    /**
     * @description: 文本 初始化 仓库结构
     * */
    public void initWarehouseStructure(String path){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<String>  ss = originDataReader.readTxt(path);
        String s = ss.get(ss.size() - 1);
        Params.initWarehouseStructure(s);
    }
    /**
     *@description： 从数据库 初始化商品信息
     * */
    public static void initItemList(){
        List<Item> itemList = ordersService.getItemList();
        Params.initItemList(itemList);
    }
    /**
     * @description: 从文本 初始化 关联度矩阵
     * */
    public void initSimilarMatrix(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("D:\\works\\data\\brandSupportCount.csv");
        double [][] matrix = new double[arrayLists.size()-1][arrayLists.size()-1];
        System.out.println(matrix.length);
        for (int i = 1; i < arrayLists.size(); i++) {
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                String s = arrayLists.get(i).get(j);
                Double d = Double.valueOf(s);
                matrix[i-1][j] = d;
            }
        }
        Params.similarMatrix = matrix;
    }
    /**
     * @description: 从文本 读取 分组信息
     * */
    public void initGroupInfo(String path){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(path);
        Params.initGroupInfo(arrayLists);
    }
}

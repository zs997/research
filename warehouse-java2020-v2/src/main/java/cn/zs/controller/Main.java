package cn.zs.controller;
import cn.zs.algorithm.GeneticAlgorithm;
import cn.zs.algorithm.Individual;
import cn.zs.algorithm.Params;
import cn.zs.algorithm.Population;
import cn.zs.algorithm.component.*;
import cn.zs.dao.OriginDataReader;
import cn.zs.daoImp.OriginDataReaderImp;
import cn.zs.mapper.OrdersMapper;
import cn.zs.pojo.Item;
import cn.zs.service.OrdersService;
import cn.zs.view.LineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.*;
public class Main {
    //目前不能这样 spring加载晚于类加载 只有在web情况，让spring先加载才可以
   // @Autowired
   // Data2Mapper data2Mapper ;
    static  OrdersMapper ordersMapper;
    static OrdersService ordersService;
    static OriginDataReader originDataReader;
    public static int maxGenerations = 300;
    public static void main(String args[]){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        originDataReader = ac.getBean(OriginDataReader.class);
        ordersService = ac.getBean(OrdersService.class);
        ordersMapper = ac.getBean(OrdersMapper.class);
     //   ordersService.generateSupportCount("d:\\works\\data\\brandSupportCount.csv");
        Main main = new Main();
        main.initWarehouseStructure("D:\\works\\data\\warehouseStructure.txt");
        main.initItemList();
        //Params.initItemList();
        main.initGroupInfo("D:\\works\\data\\groupinfo.csv");
        Params.calculNonEmptyProb();


//        ArrayList<Integer> x = new ArrayList<>();
//        ArrayList<ArrayList<Double>> y = new ArrayList<>();
//        ArrayList<Double> y1 = new ArrayList<>();
//        ArrayList<String> stackBar = new ArrayList<>();
//        stackBar.add("one");
//        y.add(y1);
//        for (int i = 0; i < 10000; i++) {
//            x.add(i);
//            Individual individual = new Individual();
//          individual.calculFitness();
//            y1.add(individual.getCost());
//            System.out.println(i+"  "+ individual.getCost());
//        }
//        Collections.sort(y1,Comparator.reverseOrder());
//        LineView.printPic(x,y,stackBar);

            GAalgorithm(ColumnL.class);
    }
    /**
     * 遗传算
     * */
    public static <T extends Column>void GAalgorithm(Class<T> t) {
        // Initial GA
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.9, 2, 5);
        // Initialize population
        Population population = ga.initPopulation();
        // Evaluate population
        ga.evalPopulation(population,t);
        // Keep track of current generation
        int generation = 1;
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<ArrayList<Double>> y = new ArrayList<>();
        ArrayList<Double> y1 = new ArrayList<>();
        ArrayList<String> stackBar = new ArrayList<>();
        stackBar.add("one");
        y.add(y1);
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            x.add(generation);
            Individual fittest = population.getFittest(0);
            double cost = fittest.getCost();
            System.out.println(generation+"  "+ cost);
            y1.add(cost);
            // Apply crossover
            population = ga.crossoverPopulation(population);
            // Apply mutation
            population = ga.mutatePopulation(population);
            // Evaluate population
            ga.evalPopulation(population,t);
            // Increment the current generation
            generation++;
        }
        Individual fittest = population.getFittest(0);
        ArrayList<Integer> chromosome = fittest.getChromosome();
        System.out.println(chromosome);
        LineView.printPic(x,y,stackBar);
        System.out.println("Stopped after " + maxGenerations + " generations.");
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
    public void initItemList(){
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

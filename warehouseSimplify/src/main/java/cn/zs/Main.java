package cn.zs;
import cn.zs.algorithm.cluster.Hcluster;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.ColumnR;
import cn.zs.algorithm.component.Individual;
import cn.zs.algorithm.component.Params;
import cn.zs.algorithm.eda.EDA;
import cn.zs.algorithm.ga.GeneticAlgorithm;
import cn.zs.algorithm.localsearcheda.LocalSearchEDA;
import cn.zs.dao.OriginDataReader;
import cn.zs.pojo.Item;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static cn.zs.algorithm.component.Params.*;

public class Main{
    static  OriginDataReader originDataReader;
    static int maxGenerations = 500;
    static String baseDataDir = "D:\\works\\data\\all";
    static {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        originDataReader = ac.getBean(OriginDataReader.class);
    }
    public static void main(String args[]) throws Exception {
         initParams(3);
//        GeneticAlgorithm<ColumnR> ga = new GeneticAlgorithm<>(ColumnR.class
//                ,150, 5, 15, 0.9, 0.01,maxGenerations);
//        Individual gaBest = ga.doGA();
//
//        EDA eda = new EDA(ColumnR.class,150, 15 * 4, 15, 0.1,
//              maxGenerations);
//        Individual edaBest = eda.doEDA();
        ArrayList<Double> res = new ArrayList<>();
        for (int k = 0; k < 10; k++) {
                LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class,50, 10, 5,
                        0.2, maxGenerations, 20);
                Individual localsearchEdaBest = localSearchEDA.doEDA();
                res.add(localsearchEdaBest.getCost());
        }
        double avg = 0;
        for (int i = 0; i < res.size(); i++) {
            System.out.print(res.get(i)+" ");
            avg += res.get(i);
        }
        System.out.println();
        System.out.println(avg/res.size());

//        Individual<ColumnR> randomSolution = new Individual<>(ColumnR.class);
//        randomSolution.calculFitness();
//        Individual zigZagSolution = zigZagSolution();
//        zigZagSolution.calculFitness();
//        Individual returnSolution = returnSolution();
//        returnSolution.calculFitness();


//        System.out.println(randomSolution.getChromosome());
//        System.out.println("randomSolution: "+ randomSolution.getCost());
//        System.out.println(zigZagSolution.getChromosome());
//        System.out.println("zigZagSolution: "+ zigZagSolution.getCost());
//        System.out.println(returnSolution.getChromosome());
//        System.out.println("returnSolution: "+returnSolution.getCost());
      //  System.out.println("gaBestTrain: "+gaBest.getCost());
    //    System.out.println("edaBestTrain: "+edaBest.getCost());
     //   System.out.println("localsearchEdaBestTrain: "+localsearchEdaBest.getCost());

//        changeParam(7);
//        gaBest.calculFitness();
//        edaBest.calculFitness();
//        localsearchEdaBest.calculFitness();
//        System.out.println("gaBestTest: " + gaBest.getCost());
//        System.out.println("edaBestTest: " + edaBest.getCost());
//        System.out.println("localsearchEdaBestTest: " + localsearchEdaBest.getCost());
    }

    /**
     * @description: 文本 初始化 仓库结构
     * @Param:k 分组数目
     * */
    public static void initParams(int k) throws Exception {
        ArrayList<String>  ss = originDataReader.readTxt(baseDataDir + "\\warehouseStructure.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));
        List<Item> itemList = originDataReader.readItemList(baseDataDir+"\\itemInfo.csv");
        Params.initItemList(itemList);
        Params.calculNonEmptyProb();
        new Hcluster().generateItemGroupByR(baseDataDir+"\\cluster.R"
                    ,baseDataDir + "\\brandDistance.csv",storageCount,k,baseDataDir + "\\groupinfoTrain.csv");
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDataDir + "\\groupinfoTrain.csv");
        Params.initGroupInfo(arrayLists);
    }

    public static Individual zigZagSolution(){
        int index = 0;
        int[][] temp = new int[M][N];
        for (int i = 0; i < temp.length; i++) {
            if (i%2 == 0){
                for (int j = 0; j < temp[i].length; j++) {
                    temp[i][j] = index;
                    index++;
                }
            }else {
                for (int j = temp[i].length-1; j >= 0; j--) {
                    temp[i][j] = index;
                    index++;
                }
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                list.add(temp[i][j]);
            }
        }
        return new Individual(ColumnR.class,list);
    }

//    public static Individual stripesSolution(){
//        int index = 0;
//        int[][] temp = new int[M][N];
//        for (int j = 0; j < temp[0].length; j++) {
//            if (j%2 == 0){
//                for (int i = 0; i < temp.length; i++) {
//                    temp[i][j] = index++;
//                }
//            }else {
//               for (int i = temp.length-1;i>=0;i--){
//                   temp[i][j] = index++;
//               }
//            }
//        }
//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 0; i < temp.length; i++) {
//            for (int j = 0; j < temp[i].length; j++) {
//                list.add(temp[i][j]);
//            }
//        }
//        return new Individual(ColumnR.class,list);
//    }
    public static Individual returnSolution(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < storageCount; i++) {
            list.add(i);
        }
        return new Individual(ColumnR.class,list);
    }
    @Deprecated
    public static void changeParam(int k) throws Exception {
        List<Item> itemList = originDataReader.readItemList(baseDataDir+"\\itemInfoTest.csv");
        Params.initItemList(itemList);
        Params.calculNonEmptyProb();
        new Hcluster().generateItemGroupByR(baseDataDir+"\\cluster.R"
                ,baseDataDir + "\\brandDistanceTest.csv",storageCount,k,baseDataDir + "\\groupinfoTest.csv");
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDataDir + "\\groupinfoTest.csv");
        Params.initGroupInfo(arrayLists);
    }

}

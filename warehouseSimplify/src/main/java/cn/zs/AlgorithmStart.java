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
import cn.zs.pojo.EdaParam;
import cn.zs.pojo.Item;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static cn.zs.algorithm.component.Params.*;

public class AlgorithmStart {
    static  OriginDataReader originDataReader;
    static int maxGenerations = 500;
    static String baseDataDir = "D:\\works\\data\\all";
    static {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        originDataReader = ac.getBean(OriginDataReader.class);
    }
    public static void main(String args[]) throws Exception {
         initParams(3);
         //zhengjiao();
        weightAnalysis();
        //test();




    }

    public static void test(){
        GeneticAlgorithm<ColumnR> ga = new GeneticAlgorithm<>(ColumnR.class
                ,100, 5, 2, 0.9, 0.001,maxGenerations,0.5);
        Individual gaBest = ga.doGA();

        EDA eda = new EDA(ColumnR.class,150, 15 * 2, 15, 0.4,
                maxGenerations,0.5);
        Individual edaBest = eda.doEDA();
        LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class, 150, 15 * 2, 15, 0.4,
                maxGenerations, 30,0.5);
        Individual localsearchEdaBest = localSearchEDA.doEDA();
        Individual<ColumnR> randomSolution = new Individual<>(ColumnR.class,0.5);
        randomSolution.calculFitness();
        Individual zigZagSolution = zigZagSolution();
        zigZagSolution.calculFitness();
        Individual returnSolution = returnSolution();
        returnSolution.calculFitness();
        System.out.println(randomSolution.getChromosome());
        System.out.println("randomSolution: "+ randomSolution.getCost());
        System.out.println(zigZagSolution.getChromosome());
        System.out.println("zigZagSolution: "+ zigZagSolution.getCost());
        System.out.println(returnSolution.getChromosome());
        System.out.println("returnSolution: "+returnSolution.getCost());
        System.out.println("gaBestTrain: "+gaBest.getCost());
        System.out.println("edaBestTrain: "+edaBest.getCost());
        System.out.println("localsearchEdaBestTrain: "+localsearchEdaBest.getCost());
    }
    public static void weightAnalysis(){
        double weight = 0;
        ArrayList<ArrayList<ArrayList<Double>>> resss = new ArrayList<>();
        for (;weight <= 1.0;weight += 0.1){
            //每个权重运行10次
            ArrayList<ArrayList<Double>> ress = new ArrayList<>();
            double lengthCost = 0.0;
            double spreadCost = 0.0;
            double cost=  0.0;
            for (int i = 0; i < 10; i++) {
                LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class, 150, 15 * 2, 15, 0.4,
                        maxGenerations, 30,weight);
                Individual localsearchEdaBest = localSearchEDA.doEDA();

                ArrayList<Double> res = new ArrayList<>();
                res.add(localsearchEdaBest.getLengthCost());
                res.add(localsearchEdaBest.getSpreadCost());
                res.add(localsearchEdaBest.getCost());
                ress.add(res);

                lengthCost += localsearchEdaBest.getLengthCost();
                spreadCost += localsearchEdaBest.getSpreadCost();
                cost += localsearchEdaBest.getCost();
            }
            ArrayList<Double> res = new ArrayList<>();
            res.add(lengthCost/10.0);
            res.add(spreadCost/10.0);
            res.add(cost/10.0);
            ress.add(res);

            resss.add(ress);
        }
        for (int i = 0; i < resss.size(); i++) {
            System.out.println(i+" ************************");
            ArrayList<ArrayList<Double>> arrayLists = resss.get(i);
            for (int j = 0; j < arrayLists.size(); j++) {
                for (int k = 0; k < arrayLists.get(j).size(); k++) {
                    System.out.print(arrayLists.get(j).get(k)+" ");
                }
                System.out.println();
            }
            System.out.println("************************");
        }

    }
    public static void agrithmsContest(){
        ArrayList<ArrayList<Double>> ress = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Double> res = new ArrayList<>();

            GeneticAlgorithm<ColumnR> ga = new GeneticAlgorithm<>(ColumnR.class
                    ,150, 5, 15, 0.9, 0.01,maxGenerations,0.5);
            Individual gaBest = ga.doGA();

            EDA eda = new EDA(ColumnR.class,150, 15 * 2, 15, 0.4,
                    maxGenerations,0.5);
            Individual edaBest = eda.doEDA();

            LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class, 150, 15 * 2, 15, 0.4,
                    maxGenerations, 30,0.5);
            Individual localsearchEdaBest = localSearchEDA.doEDA();

            Individual<ColumnR> randomSolution = new Individual<>(ColumnR.class,0.5);
            randomSolution.calculFitness();
            Individual zigZagSolution = zigZagSolution();
            zigZagSolution.calculFitness();
            Individual returnSolution = returnSolution();
            returnSolution.calculFitness();
        }
    }
    /**
     * 正交实验*/
    public static void zhengjiao(){
        ArrayList<EdaParam> edaParams = new ArrayList<>();
        edaParams.add(new EdaParam(50, 5, 0.1, 10, 5));
        edaParams.add(new EdaParam(50, 10, 0.2, 20, 10));
        edaParams.add(new EdaParam(50, 15, 0.3, 30, 15));
        edaParams.add(new EdaParam(50, 20, 0.4, 40, 20));
        edaParams.add(new EdaParam(100, 10, 0.2, 30, 40));
        edaParams.add(new EdaParam(100, 20, 0.1, 40, 30));
        edaParams.add(new EdaParam(100, 30, 0.4, 10, 20));
        edaParams.add(new EdaParam(100, 40, 0.3, 20, 10));
        edaParams.add(new EdaParam(150, 15, 0.3, 40, 30));
        edaParams.add(new EdaParam(150, 30, 0.4, 30, 15));
        edaParams.add(new EdaParam(150, 45, 0.1, 20, 60));
        edaParams.add(new EdaParam(150, 60, 0.2, 10, 45));
        edaParams.add(new EdaParam(200, 20, 0.4, 20, 60));
        edaParams.add(new EdaParam(200, 40, 0.3, 10, 80));
        edaParams.add(new EdaParam(200, 60, 0.2, 40, 20));
        edaParams.add(new EdaParam(200, 80, 0.1, 30, 40));
        ArrayList<ArrayList<Double>> ress= new ArrayList<>();
        for (int i = 0; i < edaParams.size(); i++) {
            //第i组实验
            EdaParam edaParam = edaParams.get(i);
            ArrayList<Double> res = new ArrayList<>();
            double sum = 0;
            for (int j = 0; j < 10; j++) {
                LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class, edaParam.getPopulationSize()
                        , edaParam.getSuperiorityCount(), edaParam.getEliteCount(),edaParam.getAlpha(),
                        maxGenerations, edaParam.getLocalSearchTimes(),0.5);
                Individual localsearchEdaBest = localSearchEDA.doEDA();
                double cost = localsearchEdaBest.getCost();
                sum += cost;
                res.add(cost);
            }
            res.add(sum/10.0);
            ress.add(res);
        }
        for (int i = 0; i < ress.size(); i++) {
            for (int j = 0; j < ress.get(i).size(); j++) {
                System.out.print(ress.get(i).get(j)+" ");
            }
            System.out.println();
        }

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
        return new Individual(ColumnR.class,list,0.5);
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
        return new Individual(ColumnR.class,list,0.5);
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

package cn.zs;
import cn.zs.algorithm.component.*;
import cn.zs.algorithm.eda.EDA;
import cn.zs.algorithm.ga.GeneticAlgorithm;
import cn.zs.algorithm.localsearcheda.LocalSearchEDA;
import cn.zs.benchmark.Bench;
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import cn.zs.pojo.EdaParam;
import cn.zs.pojo.GaParam;
import cn.zs.pojo.Item;
import cn.zs.benchmark.PickParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static cn.zs.algorithm.component.Params.*;
public class AlgorithmStart {
    static  OriginDataReader originDataReader = new OriginDataReaderImp();
    static int maxGenerations = 500;
    static String baseDataDir = "D:\\works\\data\\all";
    static MyDataWriter myDataWriter = new MyDataWriter();
    public static void main(String args[]) throws IOException {
      initParams(5);
      zhengjiao();
//      zhengjiaoCheck();
   //     agrithmsContest();
     //   test();
    }

    public static<T> void test(){
        Class<T> t = (Class<T>) ColumnR.class;
        double weight = 0.6;
        EdaParam edaParam = new EdaParam(200, 40, 0.3, 40, 20);
        GaParam gaParam = new GaParam(200, 10, 4, 0.9, 0.001);

        GeneticAlgorithm ga = new GeneticAlgorithm(t
                ,gaParam.getPopulationSize(), gaParam.getTournamentSize(), gaParam.getElitismCount()
                , gaParam.getCrossoverRate(), gaParam.getMutationRate(),maxGenerations,weight);
        Individual gaBest = ga.doGA();
        System.out.println("gaBest");
        System.out.println(gaBest.getCost()+" "+gaBest.getLengthCost()+" "+gaBest.getSpreadCost());

        EDA eda = new EDA(t,edaParam.getPopulationSize(), edaParam.getSuperiorityCount(), edaParam.getEliteCount(), edaParam.getAlpha(),
                maxGenerations,weight);
        Individual edaBest = eda.doEDA();
        System.out.println("edaBest:");
        System.out.println(edaBest.getCost()+" "+edaBest.getLengthCost()+" "+edaBest.getSpreadCost());

        LocalSearchEDA localSearchEDA = new LocalSearchEDA(t, edaParam.getPopulationSize()
                , edaParam.getSuperiorityCount(), edaParam.getEliteCount(),edaParam.getAlpha(),
                maxGenerations, edaParam.getLocalSearchTimes(),weight);
        Individual localsearchEdaBest = localSearchEDA.doEDA();
        System.out.println("localsearchEdaBest:");
        System.out.println(localsearchEdaBest.getCost()+" "+localsearchEdaBest.getLengthCost()+" "+localsearchEdaBest.getSpreadCost());


        Individual randomSolution = new Individual(t,weight);
        randomSolution.calculFitness();
        System.out.println("randomSolution:");
        System.out.println(randomSolution.getCost()+" "+randomSolution.getLengthCost()+" "+randomSolution.getSpreadCost());

        Individual zigZagSolution = zigZagSolution(t,weight);
        zigZagSolution.calculFitness();
        System.out.println("zigZagSolution:");
        System.out.println(zigZagSolution.getCost()+" " + zigZagSolution.getLengthCost()+" "+zigZagSolution.getSpreadCost());


        Individual returnSolution = returnSolution(t,weight);
        returnSolution.calculFitness();
        System.out.println("returnSolution:");
        System.out.println(returnSolution.getCost()+" "+returnSolution.getLengthCost()+" "+returnSolution.getSpreadCost());

        Individual stripesSolution = stripesSolution(t, weight);
        stripesSolution.calculFitness();
        System.out.println("stripesSolution");
        System.out.println(stripesSolution.getCost()+" "+stripesSolution.getLengthCost()+" "+stripesSolution.getSpreadCost());

    }

    /**算法对比*/
    public static void agrithmsContest(){
        int times = 10;
        double weight = 0.6;
        EdaParam edaParam = new EdaParam(200, 40, 0.3, 40, 20);
        ArrayList<ArrayList<Double>> ress = new ArrayList<>();
        long startTime;   //获取开始时间
        long endTime; //获取结束时间
        for (int i = 0; i < times; i++) {
            ArrayList<Double> res = new ArrayList<>();

            startTime = System.currentTimeMillis();
            EDA eda = new EDA(ColumnR.class,edaParam.getPopulationSize(), edaParam.getSuperiorityCount(),
                    edaParam.getEliteCount(), edaParam.getAlpha(),maxGenerations,weight);
            Individual edaBest = eda.doEDA();
            endTime = System.currentTimeMillis();
            res.add(edaBest.getLengthCost());
            res.add(edaBest.getSpreadCost());
            res.add(edaBest.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class, edaParam.getPopulationSize()
                    , edaParam.getSuperiorityCount(), edaParam.getEliteCount(),edaParam.getAlpha(),
                    maxGenerations, edaParam.getLocalSearchTimes(),weight);
            Individual localsearchEdaBest = localSearchEDA.doEDA();
            endTime = System.currentTimeMillis();
            res.add(localsearchEdaBest.getLengthCost());
            res.add(localsearchEdaBest.getSpreadCost());
            res.add(localsearchEdaBest.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            GeneticAlgorithm ga = new GeneticAlgorithm<>(ColumnR.class,200, 5,
                    2, 0.9, 0.001,maxGenerations,weight);
            Individual gaBest = ga.doGA();
            endTime = System.currentTimeMillis();
            res.add(gaBest.getLengthCost());
            res.add(gaBest.getSpreadCost());
            res.add(gaBest.getCost());
            res.add((endTime-startTime)/1000.0);
//            Individual<ColumnR> randomSolution = new Individual<>(ColumnR.class,weight);
//            randomSolution.calculFitness();
//            Individual zigZagSolution = zigZagSolution();
//            zigZagSolution.calculFitness();
//            Individual returnSolution = returnSolution();
//            returnSolution.calculFitness();
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
     * 正交实验*/
    public static <T>void zhengjiao() throws IOException {
        Class<T> t = (Class<T>) ColumnR.class;
        int times =10;
        double weight = 0.6;
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
            for (int j = 0; j < times; j++) {
                LocalSearchEDA localSearchEDA = new LocalSearchEDA(t, edaParam.getPopulationSize()
                        , edaParam.getSuperiorityCount(), edaParam.getEliteCount(),edaParam.getAlpha(),
                        maxGenerations, edaParam.getLocalSearchTimes(),weight);
                Individual localsearchEdaBest = localSearchEDA.doEDA();
                double cost = localsearchEdaBest.getCost();
                sum += cost;
                res.add(cost);
            }
            res.add(sum/times);
            ress.add(res);
        }
        for (int i = 0; i < ress.size(); i++) {
            for (int j = 0; j < ress.get(i).size(); j++) {
                System.out.print(ress.get(i).get(j)+" ");
            }
            System.out.println();
        }
        String[] tittle = new String[times + 1];
        for (int i = 0; i < times; i++) {
            tittle[i] = String.valueOf("实验"+(i+1));
        }
        tittle[tittle.length-1] = "平均值";
        String destination =baseDataDir+ "\\result\\zhengjiao\\zhangjiao.xlsx";
        myDataWriter.writeExcel(ress,tittle,destination);
    }
    public static <T>void zhengjiaoCheck(){
        EdaParam edaParam = new EdaParam(200, 40, 0.3, 40, 20);
        Class<T> t = (Class<T>) ColumnR.class;
        int times = 1;
        double weight = 0.5;
        ArrayList<Double> res = new ArrayList<>();
        double sum = 0;
        for (int j = 0; j < times; j++) {
            LocalSearchEDA localSearchEDA = new LocalSearchEDA(t, edaParam.getPopulationSize()
                    , edaParam.getSuperiorityCount(), edaParam.getEliteCount(),edaParam.getAlpha(),
                    maxGenerations, edaParam.getLocalSearchTimes(),weight);
            Individual localsearchEdaBest = localSearchEDA.doEDA();
            double cost = localsearchEdaBest.getCost();
            sum += cost;
            res.add(cost);
        }
        res.add(sum/times);
        System.out.println(res);
    }
    /**
     * @description: 文本 初始化 仓库结构
     * @Param:k 分组数目
     * */
    public static void initParams(int k)  {
        ArrayList<String>  ss = originDataReader.readTxt(baseDataDir + "\\warehouseStructure.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));
        List<Item> itemList = originDataReader.readItemList(baseDataDir+"\\itemInfo.csv");
        Params.initItemList(itemList);
        Params.calculNonEmptyProb();

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDataDir + "\\groupInfo\\groupInfo" + k + ".csv");
        Params.initGroupInfo(arrayLists);
    }

    public static <T>Individual zigZagSolution(Class<T> t,double weight){
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
        return new Individual(t,list,weight);
    }

    public static <T>Individual stripesSolution(Class<T> t,double weight){
        int index = 0;
        int[][] temp = new int[M][N];
        for (int j = 0; j < temp[0].length; j++) {
            if (j%2 == 0){
                for (int i = 0; i < temp.length; i++) {
                    temp[i][j] = index++;
                }
            }else {
               for (int i = temp.length-1;i>=0;i--){
                   temp[i][j] = index++;
               }
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                list.add(temp[i][j]);
            }
        }
        return new Individual(t,list,weight);
    }
    public static <T>Individual returnSolution(Class<T> t,double weight){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < storageCount; i++) {
            list.add(i);
        }
        return new Individual(t,list,weight);
    }

    @Deprecated
    public static void weightAnalysis(){
        int times = 1;
        double weight = 0;
        ArrayList<ArrayList<ArrayList<Double>>> resss = new ArrayList<>();
        for (;weight <= 1.0;weight += 0.1){
            //每个权重运行10次
            ArrayList<ArrayList<Double>> ress = new ArrayList<>();
            double lengthCost = 0.0;
            double spreadCost = 0.0;
            double cost=  0.0;
            for (int i = 0; i < times; i++) {
//                LocalSearchEDA localSearchEDA = new LocalSearchEDA(ColumnR.class, 150, 15 * 2, 15, 0.4,
//                        maxGenerations, 30,weight);
//                Individual localsearchEdaBest = localSearchEDA.doEDA();
//                GeneticAlgorithm<ColumnR> columnRGeneticAlgorithm = new GeneticAlgorithm<>(ColumnR.class
//                        , 100, 5, 2, 0.9, 0.001, maxGenerations, weight);
//                Individual individual = columnRGeneticAlgorithm.doGA();
                EDA<ColumnR> columnREDA = new EDA<>(ColumnR.class, 150, 15 * 2, 15, 0.4,
                        maxGenerations, weight);
                Individual individual = columnREDA.doEDA();
                ArrayList<Double> res = new ArrayList<>();
                res.add(individual.getLengthCost());
                res.add(individual.getSpreadCost());
                res.add(individual.getCost());
                ress.add(res);

                lengthCost += individual.getLengthCost();
                spreadCost += individual.getSpreadCost();
                cost += individual.getCost();
            }
            ArrayList<Double> res = new ArrayList<>();
            res.add(lengthCost/times);
            res.add(spreadCost/times);
            res.add(cost/times);
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

}

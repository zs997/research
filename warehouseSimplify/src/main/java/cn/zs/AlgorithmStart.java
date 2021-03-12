package cn.zs;
import cn.zs.algorithm.component.*;
import cn.zs.algorithm.eda.EDA;
import cn.zs.algorithm.ga.GeneticAlgorithm;
import cn.zs.algorithm.localsearcheda.LocalSearchEDA;
import cn.zs.benchmark.Bench;
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import cn.zs.pojo.*;
import cn.zs.benchmark.PickParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static cn.zs.algorithm.component.Params.*;
public class AlgorithmStart {
    static  OriginDataReader originDataReader = new OriginDataReaderImp();
    static int maxGenerations = 500;
    static String baseDataDir = "D:\\works\\data\\all";
    static MyDataWriter myDataWriter = new MyDataWriter();
    public static void main(String args[]) throws IOException {
      initParams(5);

        agrithmsContest();
     //   test();
    }

    public static<T> void test(){
        Class<T> t = (Class<T>) ColumnL.class;
        double weight = 0.5;
        EdaParam edaParam = new EdaParam(200, 40, 0.3, 40, 20);
        GaParam gaParam = new GaParam(200, 10, 4, 0.9, 0.001);
//
//        GeneticAlgorithm ga = new GeneticAlgorithm(t
//                ,gaParam.getPopulationSize(), gaParam.getTournamentSize(), gaParam.getElitismCount()
//                , gaParam.getCrossoverRate(), gaParam.getMutationRate(),maxGenerations,weight);
//        Individual gaBest = ga.doGA();
//        System.out.println("gaBest");
//        System.out.println(gaBest.getCost()+" "+gaBest.getLengthCost()+" "+gaBest.getSpreadCost());
//
//        EDA eda = new EDA(t,edaParam.getPopulationSize(), edaParam.getSuperiorityCount(), edaParam.getEliteCount(), edaParam.getAlpha(),
//                maxGenerations,weight);
//        Individual edaBest = eda.doEDA();
//        System.out.println("edaBest:");
//        System.out.println(edaBest.getCost()+" "+edaBest.getLengthCost()+" "+edaBest.getSpreadCost());
//
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

        Individual zigZagSolution1 = zigZagSolution(t, getPriorityListByCBpower(), weight);
        zigZagSolution1.calculFitness();
        System.out.println("zigzagSolution(order by power):");
        System.out.println(zigZagSolution1.getCost()+" " + zigZagSolution1.getLengthCost()+" "+zigZagSolution1.getSpreadCost());

        Individual zigZagSolution2 = zigZagSolution(t, getPriorityListByCBC(), weight);
        zigZagSolution2.calculFitness();
        System.out.println("zigzagSolution(order by turn):");
        System.out.println(zigZagSolution2.getCost()+" " + zigZagSolution2.getLengthCost()+" "+zigZagSolution2.getSpreadCost());

        Individual stripesSolution1 = stripesSolution(t,getPriorityListByCBpower(), weight);
        stripesSolution1.calculFitness();
        System.out.println("stripesSolution(order by power)");
        System.out.println(stripesSolution1.getCost()+" "+stripesSolution1.getLengthCost()+" "+stripesSolution1.getSpreadCost());

        Individual stripesSolution2 = stripesSolution(t,getPriorityListByCBC(), weight);
        stripesSolution2.calculFitness();
        System.out.println("stripesSolution(order by turn)");
        System.out.println(stripesSolution2.getCost()+" "+stripesSolution2.getLengthCost()+" "+stripesSolution2.getSpreadCost());
    }

    /**算法对比*/
    public static<T> void agrithmsContest() throws IOException {
        int times = 1;
        double weight = 0.5;
        Class<T> t = (Class<T>) ColumnL.class;
        String[] split = t.toString().split("\\.");
        String name = split[split.length - 1];
        String destination = baseDataDir+"\\result\\algorithmContest\\contest" + name + System.currentTimeMillis()+".xlsx";
        EdaParam edaParam = new EdaParam(200, 40, 0.3, 40, 20);
        GaParam gaParam = new GaParam(200, 10, 4, 0.9, 0.001);

        ArrayList<ArrayList<Double>> ress = new ArrayList<>();
        long startTime;   //获取开始时间
        long endTime; //获取结束时间
        for (int i = 0; i < times; i++) {
            ArrayList<Double> res = new ArrayList<>();

            startTime = System.currentTimeMillis();
            EDA eda = new EDA(t,edaParam.getPopulationSize(), edaParam.getSuperiorityCount(),
                    edaParam.getEliteCount(), edaParam.getAlpha(),maxGenerations,weight);
            Individual edaBest = eda.doEDA();
            endTime = System.currentTimeMillis();
            res.add(edaBest.getLengthCost());
            res.add(edaBest.getSpreadCost());
            res.add(edaBest.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            LocalSearchEDA localSearchEDA = new LocalSearchEDA(t, edaParam.getPopulationSize()
                    , edaParam.getSuperiorityCount(), edaParam.getEliteCount(),edaParam.getAlpha(),
                    maxGenerations, edaParam.getLocalSearchTimes(),weight);
            Individual localsearchEdaBest = localSearchEDA.doEDA();
            endTime = System.currentTimeMillis();
            res.add(localsearchEdaBest.getLengthCost());
            res.add(localsearchEdaBest.getSpreadCost());
            res.add(localsearchEdaBest.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            GeneticAlgorithm ga = new GeneticAlgorithm(t,gaParam.getPopulationSize(), gaParam.getTournamentSize(),
                    gaParam.getElitismCount(), gaParam.getCrossoverRate(), gaParam.getMutationRate(),maxGenerations,weight);
            Individual gaBest = ga.doGA();
            endTime = System.currentTimeMillis();
            res.add(gaBest.getLengthCost());
            res.add(gaBest.getSpreadCost());
            res.add(gaBest.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            Individual zigZagSolution1 = zigZagSolution(t, getPriorityListByCBpower(), weight);
            endTime = System.currentTimeMillis();
            zigZagSolution1.calculFitness();
            res.add(zigZagSolution1.getLengthCost());
            res.add(zigZagSolution1.getSpreadCost());
            res.add(zigZagSolution1.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            Individual zigZagSolution2 = zigZagSolution(t, getPriorityListByCBC(), weight);
            endTime = System.currentTimeMillis();
            zigZagSolution2.calculFitness();
            res.add(zigZagSolution2.getLengthCost());
            res.add(zigZagSolution2.getSpreadCost());
            res.add(zigZagSolution2.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            Individual stripesSolution1 = stripesSolution(t,getPriorityListByCBpower(), weight);
            endTime = System.currentTimeMillis();
            stripesSolution1.calculFitness();
            res.add(stripesSolution1.getLengthCost());
            res.add(stripesSolution1.getSpreadCost());
            res.add(stripesSolution1.getCost());
            res.add((endTime-startTime)/1000.0);

            startTime = System.currentTimeMillis();
            Individual stripesSolution2 = stripesSolution(t,getPriorityListByCBC(), weight);
            endTime = System.currentTimeMillis();
            stripesSolution2.calculFitness();
            res.add(stripesSolution2.getLengthCost());
            res.add(stripesSolution2.getSpreadCost());
            res.add(stripesSolution2.getCost());
            res.add((endTime-startTime)/1000.0);

            ress.add(res);
        }
        String tittle [] = {
                "edaLengthCost","edaSpreadCost","edaCost","edaTime",
                "LocalEdaLengthCost","LocalEdaSpreadCost","LocalEdaCost","LocalEdaTime",
                "GALengthCost","GASpreadCost","GACost","GATime",
                "zigzag(orderByPower)LengthCost","zigzag(orderByPower)SpreadCost","zigzag(orderByPower)Cost","zigzag(orderByPower)Time",
                "zigzag(orderByTurn)LengthCost","zigzag(orderByTurn)SpreadCost","zigzag(orderByTurn)Cost","zigzag(orderByTurn)Time",
                "stripes(orderByPower)LengthCost","stripes(orderByPower)SpreadCost","stripes(orderByPower)Cost","stripes(orderByPower)Time",
                "stripes(orderByTurn)LengthCost","stripes(orderByTurn)SpreadCost","stripes(orderByTurn)Cost","stripes(orderByTurn)Time",
        };
        myDataWriter.writeExcel(ress,tittle,destination);
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
    public static void initParams(int k)  {
        ArrayList<String>  ss = originDataReader.readTxt(baseDataDir + "\\warehouseStructure.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));
        List<Item> itemList = originDataReader.readItemList(baseDataDir+"\\itemInfo.csv");
        Params.initItemList(itemList);
        Params.calculNonEmptyProb();

        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv(baseDataDir + "\\groupInfo\\groupInfo" + k + ".csv");
        Params.initGroupInfo(arrayLists);
    }
    public static <T>Individual zigZagSolution(Class<T> t,List<Integer> priorityList,double weight)    {
        int index = 0;
        int[][] temp = new int[M][N];
        for (int i = 0; i < temp.length; i++) {
            if (i%2 == 0){
                for (int j = 0; j < temp[i].length; j++) {
                    temp[i][j] = priorityList.get(index);
                    index++;
                }
            }else {
                for (int j = temp[i].length-1; j >= 0; j--) {
                    temp[i][j] = priorityList.get(index);
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
    public static <T>Individual stripesSolution(Class<T> t,List<Integer> priorityList,double weight){
        int index = 0;
        int[][] temp = new int[M][N];
        for (int j = 0; j < temp[0].length; j++) {
            if (j%2 == 0){
                for (int i = 0; i < temp.length; i++) {
                    temp[i][j] = priorityList.get(index);
                    index++;
                }
            }else {
               for (int i = temp.length-1;i>=0;i--){
                   temp[i][j] = priorityList.get(index);
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
    public static List<Integer> getPriorityListByCBpower(){
        ArrayList<Integer> res = new ArrayList<>();
        ArrayList<ArrayList<Integer>> groupCopy = new ArrayList<>();
        for (int i = 0; i < itemGroups.size(); i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < itemGroups.get(i).size(); j++) {
                list.add(itemGroups.get(i).get(j));
            }
            Collections.sort(list);
            groupCopy.add(list);
        }
        //按照size 从小到大排序
        Collections.sort(groupCopy, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.size()-o2.size();
            }
        });
        for (int i = 0; i < groupCopy.size(); i++) {
            for (int j = 0; j < groupCopy.get(i).size(); j++) {
                res.add(groupCopy.get(i).get(j));
            }
        }
        return res;
    }
    public static List<Integer> getPriorityListByCBC(){
        ArrayList<GroupTurn> groupTurns = new ArrayList<>();
        for (int i = 0; i < itemGroups.size(); i++) {
            GroupTurn groupTurn = new GroupTurn();
            ArrayList<Integer> list = itemGroups.get(i);
            for (int j = 0; j < list.size(); j++) {
                Integer itemNo = list.get(j);
                double v = itemPickFreq[itemNo];
                ItemTurn itemTurn = new ItemTurn();
                itemTurn.setItemNo(itemNo);
                itemTurn.setItemTurn(v);
                groupTurn.addItemTurn(itemTurn);
            }
            groupTurn.calculGroupTurn();
            groupTurns.add(groupTurn);
        }
        Collections.sort(groupTurns, new Comparator<GroupTurn>() {
            @Override
            public int compare(GroupTurn o1, GroupTurn o2) {
                //组别按周转率 正序
                return (o1.getGroupTurn() - o2.getGroupTurn())>0?-1:1;
            }
        });
        for (int i = 0; i < groupTurns.size(); i++) {
            GroupTurn groupTurn = groupTurns.get(i);
            List<ItemTurn> list = groupTurn.getList();
            Collections.sort(list, new Comparator<ItemTurn>() {
                @Override
                public int compare(ItemTurn o1, ItemTurn o2) {
                    //item要 周转率大的放前面
                    return (o1.getItemTurn()-o2.getItemTurn()) >0 ? -1:1 ;
                }
            });
        }
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < groupTurns.size(); i++) {
            GroupTurn groupTurn = groupTurns.get(i);
            List<ItemTurn> list = groupTurn.getList();
            for (int j = 0; j < list.size(); j++) {
                ItemTurn itemTurn = list.get(j);
                res.add(itemTurn.getItemNo());
            }
        }
        return res;
    }

    /*
    * 老架构
    * */
    @Deprecated
    public static <T>Individual returnSolution(Class<T> t,double weight){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < storageCount; i++) {
            list.add(i);
        }
        return new Individual(t,list,weight);
    }
    @Deprecated
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
    @Deprecated
    public static <T>Individual zigZagSolution(Class<T> t,double weight)    {
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
    /**
     * 正交实验做完了*/
    @Deprecated
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
    @Deprecated
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

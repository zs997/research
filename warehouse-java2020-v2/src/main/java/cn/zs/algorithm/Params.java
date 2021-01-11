package cn.zs.algorithm;
import cn.zs.pojo.Item;
import java.util.ArrayList;
import java.util.List;

//货物编码规则：频次由大到小 0 1 2 3 4 5 6  M*N-1
public class Params {
    //拣货概率 计算所得数据 用map存比较好 以后可改进
    //作用是map 存ABC的拣货概率
    //public  static double itemPickFreq[] = new double[3];
   // public  static HashMap<String,Double> itemPickFreq = new HashMap<>();
    //假设分配的货物与库位数目一样多
    public static int storageCount;
    public static double [] itemPickFreq ;
    public static Item [] itemlist;
    public static ArrayList<ArrayList<Integer>> itemGroups;
    public static double [][] similarMatrix ;
    //原始数据
    // M排货架 每排N个库位
    public   static int M,N;
    //仓库尺寸
    public   static int f,wa;
    public   static double wc;

    //订单不为空的概率 和数据案例有关
    public static double nonEmptyProb;
    //不实例化对象
    private Params(){}
    /**
     * @description:初始化仓库数据 第 1 步
     * */
    public static void initWarehouseStructure(String data){
        data =data.trim();
        String[] datas = data.split("\\s+");
        //不必加异常处理 异常直接退出
        M = Integer.valueOf(datas[0]);
        N = Integer.valueOf(datas[1]);
        f = Integer.valueOf(datas[2]);
        wa = Integer.valueOf(datas[3]);
        wc = Double.valueOf(datas[4]);
        storageCount = M * N;
    }

    /**
     * @description: 初始化商品列表和拣货概率列表 第 2 步
     * */
    public static void initItemList(List<Item> list){
        itemlist = new Item[list.size()];
        itemPickFreq = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            itemlist[i] = list.get(i);
            itemPickFreq[i] = list.get(i).getPickfreq();
        }
    }
    /**
     * @description:生成随机概率值 用于测试
     * */
    public static void initItemList(){
        itemPickFreq = new double[storageCount];
        for (int i = 0; i < itemPickFreq.length; i++) {
            itemPickFreq[i] = Math.random();
        }
    }
    /**
     * @description: 初始化 分组信息 第3 步
     * */
    public static void initGroupInfo(ArrayList<ArrayList<String>> groupInfo){
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 1; i < groupInfo.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < groupInfo.get(i).size(); j++) {
                temp.add(Integer.valueOf(groupInfo.get(i).get(j).trim()));
            }
            res.add(temp);
        }
        Params.itemGroups = res;
    }
    /**
     * @description:计算订单非空的概率 第 4 步
     * */
    public static void calculNonEmptyProb(){
        double p = 1.0;
        for (int i = 0; i < itemPickFreq.length; i++) {
            p *= (1 - itemPickFreq[i]);
        }
        nonEmptyProb = 1 - p;
    }

    public static String getParams(){
        return  "M=" + M
                + " N=" + N
                + " f=" + f
                + " wa="+ wa
                + " wc="+ wc;

    }

}

package cn.zs.algorithm.component;
import cn.zs.pojo.Item;
import java.util.ArrayList;
import java.util.List;
//货物编码规则：频次由大到小 0 1 2 3 4 5 6  M*N-1
public class Params {
    public static int storageCount;
    public static double [] itemPickFreq ;
    public static Item [] itemList;
    public static ArrayList<ArrayList<Integer>> itemGroups;

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
     * @param warehouseStructureData  从文本读取仓库尺寸数据
     * */
    public static void initWarehouseStructure(String warehouseStructureData){
        warehouseStructureData =warehouseStructureData.trim();
        String[] datas = warehouseStructureData.split("\\s+");
        //不必加异常处理 异常直接退出
        M = Integer.valueOf(datas[0]);
        N = Integer.valueOf(datas[1]);
        f = Integer.valueOf(datas[2]);
        wa = Integer.valueOf(datas[3]);
        wc = Double.valueOf(datas[4]);
        storageCount = M * N;
    }
    /**
     * @description:初始化商品列表和拣货概率列表 第 2 步
     * @param list  从数据库查询商品信息
     * */
    public static void initItemList(List<Item> list){
        itemList = new Item[storageCount];
        itemPickFreq = new double[storageCount];
        for (int i = 0; i < storageCount; i++) {
            itemList[i] = list.get(i);
            itemPickFreq[i] = list.get(i).getPickfreq();
        }
    }
    /**
     * 使用拣货概率初始化（用于banchmark数据初始化）
     * */
    @Deprecated
    public static void inititemPickFreq(double [] pickf){
        itemPickFreq = pickf;
    }
    /**
     * @description:生成随机拣货概率值 用于测试
     * */
    @Deprecated
    public static void initItemListRandom(){
        itemPickFreq = new double[storageCount];
        for (int i = 0; i < itemPickFreq.length; i++) {
            itemPickFreq[i] = Math.random();
        }
    }
    /**
     * @description:初始化分组信息 第3 步
     * @Param groupInfo  从csv读取文本分组信息
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
        itemGroups = res;
    }
    /**
     * @description:计算订单非空的概率 第 4 步
     * */
    public static void calculNonEmptyProb(){
        double p = 1.0;
//        for (int i = 0; i < itemPickFreq.length; i++) {
//            p *= (1 - itemPickFreq[i]);
//        }
        for (int i = 0; i < storageCount; i++) {
            p *= (1 - itemPickFreq[i]);
        }
        nonEmptyProb = 1 - p;
    }
    @Deprecated
    public static void setItemPickFreq(double[] itemPickFreq) {
        Params.itemPickFreq = itemPickFreq;
    }
}

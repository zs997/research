package cn.zs.algorithm.component;
/**
 * @descrip: Return拣货策略
 * */
import java.util.ArrayList;
import java.util.HashSet;
import static  cn.zs.algorithm.Params.*;
public class ColumnR {
    //货架的库位 存放货物编号 每一种存放 表示一种库位分配
    // 存储 货物编号
     ArrayList<Integer> locations;
    //进入巷道的概率
    double enterProb;
    //该通道是最后要访问的概率
    double lastProb;
    //通道内行走长度期望
    double elr;
    //横通道的长度期望
    double elc;
    //总成本
    double cost;
    public ColumnR(){
    }
    /**
     * @description: 初始化库位
     * */
    public void setLocations(ArrayList<Integer> locations) {
        this.locations = locations;
    }
    /**
     * @description：计算通道内的距离 第 1 步
     * */
    public  void calculElr(){
        double sum = 0;
        double multi;
        for(int j = 1;j <= N;j++)
        {
            double base = (wc + j*f - 0.5*f) * (itemPickFreq[locations.get(j-1)]);
            multi = 1;
            for(int h = j + 1;h <= N;h++)
            {
                multi = multi * (1-itemPickFreq[locations.get(h-1)]);
            }
            sum += 2*multi*base;
        }
        elr = sum;
    }
    /**
     * @description：计算进入该巷道的概率 第 2步
     * */
    public void calculEnterProb(){
        double multi = 1;
        for(int i = 0;i < N; i++){
            multi = multi * (1-itemPickFreq[locations.get(i)]);
        }
        enterProb = 1 - multi;
    }
    /**
     * @description：计算该通道是最后一个要访问的概率，与分配状态有关 第 3 步
     * 其实就是知道分配了该通道之后，还剩啥货物，计算都不拣选的概率
     * @param：分到通道1到该通道（包括）一共用的ABC个数。
     * @param:set 在此之前 已经使用过的货物
     * */
    public void calculLastProb(HashSet<Integer> usedSet){
        lastProb = 1.0;
        for (int i = 0; i < itemPickFreq.length; i++) {
            if (!usedSet.contains(i)){
                lastProb *= (1- itemPickFreq[i]);
            }
        }
    }
    /**
     * @param:no 是第几排货架 1~ m
     * @description：计算横通道距离 第 4 步
     * */
    public void calculElc(int no){
        elc=2 * wa * (no-1) * enterProb * lastProb;
    }
    /**
     * @description：第1巷道初始库位分配时，可以将所有单个通道内的库位分配情况穷举
     * 所以单个通道内路径长度只需要计算一次。
     * */
    public void calculCost(int no,HashSet<Integer> usedSet){
        calculElr();
        calculEnterProb();
        //需要知道其他参数
        calculLastProb(usedSet);
        calculElc(no);
        cost=(elr+elc)/nonEmptyProb;
    }
    public ArrayList<Integer> getLocations() {
        return locations;
    }
    public double getCost() {
        return cost;
    }
}

package cn.zs.algorithm.component;

/**
 * @version: V1.0
 * @author: zs
 * @className: ColumnS
 * @packageName: cn.zs.model
 * @data: 2020-11-28 17:04
 * @description: S型拣货策略
 **/

import java.util.HashSet;

import static cn.zs.algorithm.ga.Params.*;
public class ColumnS extends ColumnR{
    double evenProb;
    double els;

    void calculEvenProb(double lastEvenProb,double lastEnterProb){
        evenProb = lastEvenProb * (1 - lastEnterProb) + (1 - lastEvenProb) * lastEnterProb;
    }
    /**
     * 要先知道 三个概率 和 elr
     * */
    void calculEls(){
        els = (1 - evenProb * lastProb) * enterProb * (2 * wc  + N * f) + evenProb * lastProb * elr ;
    }
    /**
     * 从库位分配开始计算 第一个通道中 evenProb = 1
     * @param : lastEvenProb 某一状态下 该通道的某一分配 会有上一个状态 上一状态的最优分配已经计算了，就是上一状态的最优化情况下 上一个通道的概率
     * */
    public void calculCost(int no,HashSet<Integer> usedSet,double lastEvenProb, double lastEnterProb,double lastFirstProb){
        //前三组 只需要该巷道内的库位分配情况
       // assignABC(numA,numB,numC);
        calculElr();
        calculEnterProb();

        //需要知道其他参数
        calculLastProb(usedSet);
        calculElc(no);

        //需要其他参数
        calculEvenProb(lastEvenProb,lastEnterProb);
        calculEls();
        cost = (els + elc)/nonEmptyProb;
    }
    public double getEvenProb() {
        return evenProb;
    }
}
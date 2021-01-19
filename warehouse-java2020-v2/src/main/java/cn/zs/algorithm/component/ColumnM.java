/**
 * projectName: warehouse2020
 * fileName: ColumnM.java
 * packageName: cn.zs.model
 * date: 2020-11-30 14:33
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.component;

/**
 * @version: V1.0
 * @author: zs
 * @className: ColumnM
 * @packageName: cn.zs.model
 * @data: 2020-11-30 14:33
 **/

import java.util.HashSet;

import static cn.zs.algorithm.component.Params.*;

public class ColumnM extends ColumnR {
    double firstProb;
    double elm1;
    double elm2;
    double elm;
    /*
    * 计算Eli1  只需要单个通道库位分配情况 不需要其他数据
    * */
    void calculElm1(){
        double sum = 0;
        int halfN = (int) Math.ceil( N/2.0);
        double multi;
        double base;
        for(int j = halfN + 1;j <= N;j++) //起点应为 halfN
        {
            base=(wc + (N-j) * f + 0.5*f)*(itemPickFreq[locations.get(j-1)]);
            multi=1;
            for(int h = halfN + 1;h < j;h++) //起点应为 halfN
            {
                multi = multi * (1 - itemPickFreq[locations.get(h - 1)]);
            }
            sum += 2*multi*base;
        }
        elm1 = sum;
    }
    /**
     * 计算Elm2 只需要单个通道库位分配情况 不需要其他数据
     * */
    void calculElm2(){
        double sum = 0;
        int halfN = (int)Math.ceil(N/2.0);
        double base;
        double multi;
        for(int j = 1;j <= halfN;j++) //起点为0
        {
            base = (wc + j*f - 0.5*f)*(itemPickFreq[locations.get(j-1)]);
            multi=1;
            for(int h = j+1;h <= halfN;h++) //起点为j
            {
                multi = multi*(1-itemPickFreq[locations.get(h-1)]);
            }
            sum += 2*multi*base;
        }
        elm2 = sum;
    }

    /**
     * @description: 计算该通道之前没有进入通道的概率
     * 需要已知上一个通道的进入概率，及上一个通道之前没有进入的概率
     * */
    void calculFirstProb(double lastFirstProb,double lastEnterProb)
    {
        firstProb = lastFirstProb * (1 - lastEnterProb);
    }

    void calculElm(){
        elm = lastProb * firstProb * elr
                + (lastProb + firstProb - 2 * lastProb * firstProb) * enterProb * (2 * wc + N * f)
                + (1 - lastProb) * (1 - firstProb) * (elm1 + elm2);
    }

    /**
     * @description：第1巷道初始库位分配时，可以将所有单个通道内的库位分配情况穷举
     * 所以单个通道内路径长度只需要计算一次。
     * 计算成本 从库位分配开始计算
     * */
    public void calculCost(int no,HashSet<Integer> usedSet,double lastEvenProb, double lastEnterProb,double lastFirstProb){
        //前三组 只需要该巷道内的库位分配情况
        calculElr();
        calculEnterProb();
        calculElm1();
        calculElm2();
        //需要知道其他参数
         calculLastProb(usedSet);
        calculElc(no);
        calculFirstProb(lastFirstProb,lastEnterProb);

        calculElm();
        cost = (elm + elc)/nonEmptyProb;
    }
    public double getFirstProb() {
        return firstProb;
    }
}
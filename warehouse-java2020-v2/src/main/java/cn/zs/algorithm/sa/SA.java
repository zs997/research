/**
 * projectName: TSP
 * fileName: SA.java
 * packageName: cn.cn.cn.zs
 * date: 2021-01-17 16:59
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.sa;

import cn.zs.algorithm.component.Column;

/**
 * @version: V1.0
 * @author: cn.cn.zs
 * @className: SA
 * @packageName: cn.cn.cn.zs
 * @data: 2021-01-17 16:59
 **/
public class SA<T extends Column> {
    private double T0;  // 起始温度
    private double Tn;  // 终止温度
    private int iterator_num;  // 迭代次数
    private double alpha;  // 温度下降速率
    private Assignment assignment;
    private int s;
    private Class<T> t;
    public SA(double t0, double tn, int iterator_num, double alpha,Class<T> t) throws Exception {
        this.T0 = t0;
        this.Tn = tn;
        this.iterator_num = iterator_num;
        this.alpha = alpha;
        this.assignment = new Assignment(t);
        this.s = 0;
    }

    // 模拟退火算法
    public void SimulatedAnnealing() throws Exception {
        double t0 = this.T0;
        int bChange;
        while (t0 > this.Tn) {
            bChange = 0;
            for (int i = 0; i < iterator_num; i++) {
                assignment.generateNeighour();
                double delta = assignment.getTempCost() - assignment.getBestCost();
                if (delta <= 0) {
                    assignment.setBestCost(assignment.getTempCost());
                    assignment.setBestAssignment(assignment.getTempAssignment());
                    bChange = 1;
                } else {
                    double random = Math.random();
                    double eps = Math.exp(-delta / t0);
                    if (eps > random && eps < 1) {
                        assignment.setBestCost(assignment.getTempCost());
                        assignment.setBestAssignment(assignment.getTempAssignment());
                        bChange = 1;
                    }
                }
            }
            System.out.println(t0 + "  "+ assignment.getBestCost());
            t0 *= alpha;
            //没有更新解
            if (bChange == 0)
                s++;
            else
                s = 0;
            //连续两次没有更新解
            if (s == 2)
                break;
        }
    }



    private SA(){

    }
    public void doSA() throws Exception {
        long begin_time = System.currentTimeMillis();
        SimulatedAnnealing();
        long end_time = System.currentTimeMillis();

        System.out.print("最低成本为：");
        System.out.println(assignment.getBestCost());
        System.out.print("分配方案：");
        int[] best_path = assignment.getBestAssignment();
        for (int i = 0; i < best_path.length; i++) {
            System.out.print(best_path[i]+",");
        }
        System.out.println();
        System.out.println("运行时间：" + ((end_time - begin_time) / 1000.0));
    }

}
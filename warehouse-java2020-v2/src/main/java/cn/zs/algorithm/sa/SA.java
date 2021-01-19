/**
 * projectName: TSP
 * fileName: SA.java
 * packageName: cn.zs
 * date: 2021-01-17 16:59
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.sa;

/**
 * @version: V1.0
 * @author: zs
 * @className: SA
 * @packageName: cn.zs
 * @data: 2021-01-17 16:59
 **/
public class SA {
    private double T0;  // 起始温度
    private double Tn;  // 终止温度
    private int iterator_num;  // 迭代次数
    private double alpha;  // 温度下降速率
    private Path path;
    private int s;

    public SA(double t0, double tn, int iterator_num, double alpha) {
        this.T0 = t0;
        this.Tn = tn;
        this.iterator_num = iterator_num;
        this.alpha = alpha;
        this.path = new Path();
        this.s = 0;
    }
    int count = 0;
    // 模拟退火算法
    public void SimulatedAnnealing() {
        double t0 = this.T0;
        int bChange;
        while (t0 > this.Tn) {
            bChange = 0;
            for (int i = 0; i < iterator_num; i++) {
                path.generateNeighour();
                double delta = path.getTemp_res() - path.getRes();
                if (delta <= 0) {
                    path.setRes(path.getTemp_res());
                    path.setBest_path(path.getTemp_path());
                    bChange = 1;
                } else {
                    double random = Math.random();
                    double eps = Math.exp(-delta / t0);
                    if (eps > random && eps < 1) {
                        path.setRes(path.getTemp_res());
                        path.setBest_path(path.getTemp_path());
                        bChange = 1;
                    }
                }
                System.out.println(count + "  "+ path.getRes());
                count++;
            }
            t0 *= alpha;
            if (bChange == 0)
                s++;
            else
                s = 0;
            if (s == 2)
                break;
        }
    }

    public static void doSA() {
        long begin_time = System.currentTimeMillis();

        SA sa = new SA(100, 0.000000005, 100, 0.95);
        sa.SimulatedAnnealing();

        long end_time = System.currentTimeMillis();

        System.out.print("最佳路径长度为：");
        System.out.println(sa.path.getRes());
        System.out.print("路径顺序：");
        int[] best_path = sa.path.getBest_path();
        for (int i = 0; i < best_path.length; i++) {
            System.out.print(best_path[i]+",");
        }
        System.out.println();
        double res = sa.path.getRes();
        System.out.println(res+"fdsfsg");
        System.out.println();
        System.out.println("运行时间：" + ((end_time - begin_time) / 1000.0));

//        System.out.println();
//        double dist = 0.0;
//        for (int i = 0; i < sa.path.getCity().getCity_num() - 1; i++)
//            dist += sa.path.getCity().getDistance(sa.path.getBest_path()[i],sa.path.getBest_path()[i+1]);
//        dist += sa.path.getCity().getDistance(sa.path.getBest_path()[sa.path.getCity().getCity_num() - 1],sa.path.getBest_path()[0]);
//        System.out.println(dist);
    }
}
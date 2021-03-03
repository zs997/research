/**
 * projectName: TSP
 * fileName: SA.java
 * packageName: cn.cn.zs
 * date: 2021-01-17 16:59
 * copyright(c) 2019-2021 hust
 */
package tspbenchmark.tspsa;



/**
 * @version: V1.0
 * @author: cn.zs
 * @className: SA
 * @packageName: cn.cn.zs
 * @data: 2021-01-17 16:59
 **/
public class SA{
    private double T0;  // 起始温度
    private double Tn;  // 终止温度
    private int iterator_num;  // 迭代次数
    private double alpha;  // 温度下降速率
    private Route route;
    private int s;
    public SA(double t0, double tn, int iterator_num, double alpha)   {
        this.T0 = t0;
        this.Tn = tn;
        this.iterator_num = iterator_num;
        this.alpha = alpha;
        this.route = new Route();
        this.s = 0;
    }

    // 模拟退火算法
    public void SimulatedAnnealing()  {
        double t0 = this.T0;
        int bChange;
        while (t0 > this.Tn) {
            bChange = 0;
            for (int i = 0; i < iterator_num; i++) {
                route.generateNeighour();
                double delta = route.getTempCost() - route.getBestCost();
                if (delta <= 0) {
                    route.setBestCost(route.getTempCost());
                    route.setBestRoute(route.getTempRoute());
                    bChange = 1;
                } else {
                    double random = Math.random();
                    double eps = Math.exp(-delta / t0);
                    if (eps > random && eps < 1) {
                        route.setBestCost(route.getTempCost());
                        route.setBestRoute(route.getTempRoute());
                        bChange = 1;
                    }
                }
            }
            System.out.println(t0 + "  "+ route.getBestCost());
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

    public static void main(String[] args) {
        new SA(100, 0.005, 100, 0.95).doSA();
    }
    public void doSA()   {
        long begin_time = System.currentTimeMillis();
        SimulatedAnnealing();
        long end_time = System.currentTimeMillis();

        System.out.print("最低成本为：");
        System.out.println(route.getBestCost());
        System.out.print("分配方案：");
        int[] best_path = route.getBestRoute();
        for (int i = 0; i < best_path.length; i++) {
            System.out.print(best_path[i]+",");
        }
        System.out.println();
        System.out.println("运行时间：" + ((end_time - begin_time) / 1000.0));
    }

}
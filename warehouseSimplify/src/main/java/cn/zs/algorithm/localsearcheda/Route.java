/**
 * projectName: TSP
 * fileName: Path.java
 * packageName: cn.cn.cn.zs
 * date: 2021-01-17 16:58
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.localsearcheda;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.Individual;
import java.util.ArrayList;
import static cn.zs.algorithm.component.Params.storageCount;
import static cn.zs.algorithm.component.Params.wa;

/**
 * @version: V1.0
 * @author: cn.cn.zs
 * @className: Path
 * @packageName: cn.cn.cn.zs
 * @data: 2021-01-17 16:58
 **/
public class Route <T extends Column>{
    //下标库位编号 值为存储的货物
    private int[] bestRoute;
    private int[] tempRoute;
    private double bestCost;
    private double tempCost;   // 临时存放路径及其及其长度
    private Class<T> t;
    private double weight;
    public Route(Individual individual){
        this.t = individual.getT();
        ArrayList<Integer> chromosome = individual.getChromosome();
        bestRoute = new int[chromosome.size()];
        for (int i = 0; i < bestRoute.length; i++) {
            bestRoute[i] = chromosome.get(i);
        }
        individual.calculFitness();
        bestCost = individual.getCost();
        tempRoute = new int[chromosome.size()];
        tempCost = 0.0;
        this.weight = individual.getWeight();
    }
    public void generateNeighour()   {
        setTempRoute(bestRoute);
        int i = 0;
        int j = 0;
        while (i == j) {
            i = (int) (storageCount * Math.random());
            j = (int) (storageCount * Math.random());
            if (i > j) {
                int x = i;
                i = j;
                j = x;
            }
        }
        while (i < j) {
            int exchange = tempRoute[i];
            tempRoute[i] = tempRoute[j];
            tempRoute[j] = exchange;
            i++;
            j--;
        }
        Individual tempIndividual = new Individual(t, tempRoute,weight);
        tempIndividual.calculFitness();
        double res = tempIndividual.getCost();
        tempCost = res;
    }

    public int[] getBestRoute() {
        return bestRoute;
    }

    public void setBestRoute(int[] bestRoute) {
        this.bestRoute = new int[bestRoute.length];
        for (int i = 0; i < bestRoute.length; i++) {
            this.bestRoute[i] = bestRoute[i];
        }
    }

    public int[] getTempRoute() {
        return tempRoute;
    }

    public void setTempRoute(int[] tempRoute) {
        this.tempRoute = new int[tempRoute.length];
        for (int i = 0; i < tempRoute.length; i++) {
            this.tempRoute[i] = tempRoute[i];
        }
    }

    public double getBestCost() {
        return bestCost;
    }

    public void setBestCost(double bestCost) {
        this.bestCost = bestCost;
    }

    public double getTempCost() {
        return tempCost;
    }

    public void setTempCost(double tempCost) {
        this.tempCost = tempCost;
    }


}
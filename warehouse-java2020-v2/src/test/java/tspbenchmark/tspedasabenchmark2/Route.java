/**
 * projectName: TSP
 * fileName: Path.java
 * packageName: cn.cn.zs
 * date: 2021-01-17 16:58
 * copyright(c) 2019-2021 hust
 */
package tspbenchmark.tspedasabenchmark2;
import tspbenchmark.DistanceInstance;

import java.util.ArrayList;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Path
 * @packageName: cn.cn.zs
 * @data: 2021-01-17 16:58
 **/
public class Route {

    //下标库位编号 值为存储的货物
    private int[] bestRoute;
    private int[] tempRoute;
    private double bestCost;
    private double tempCost;   // 临时存放路径及其及其长度
    private DistanceInstance distanceInstance;

    public Route(Individual individual, DistanceInstance distanceInstance){
        this.distanceInstance = distanceInstance;
        ArrayList<Integer> chromosome = individual.getChromosome();
        bestRoute = new int[chromosome.size()];
        for (int i = 0; i < bestRoute.length; i++) {
            bestRoute[i] = chromosome.get(i);
        }
        bestCost = calculDistance(bestRoute, distanceInstance);

        tempRoute = new int[chromosome.size()];
        tempCost = 0.0;
    }
    public void generateNeighour()   {
        setTempRoute(bestRoute);
        int i = 0;
        int j = 0;
        while (i == j) {
            i = (int) (distanceInstance.getCityNum() * Math.random());
            j = (int) (distanceInstance.getCityNum() * Math.random());
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
        double res = calculDistance(tempRoute, distanceInstance);
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

    public static double calculDistance(Individual individual, DistanceInstance distanceInstance){
        double[][] distanceMatrix = distanceInstance.getDistanceMatrix();
        ArrayList<Integer> chromosome = individual.getChromosome();
        double distance = 0.0;
        for (int i = 0; i < chromosome.size()-1; i++) {
            distance += distanceMatrix[chromosome.get(i)][chromosome.get(i+1)];
        }
        distance += distanceMatrix[chromosome.get(chromosome.size()-1)][chromosome.get(0)];
        return  distance;
    }
    public static double calculDistance(int [] route,DistanceInstance distanceInstance){
        double[][] distanceMatrix = distanceInstance.getDistanceMatrix();

        double distance = 0.0;
        for (int i = 0; i < route.length-1; i++) {
            distance += distanceMatrix[route[i]][route[i+1]];
        }
        distance += distanceMatrix[route[route.length-1]][route[0]];
        return  distance;
    }
}
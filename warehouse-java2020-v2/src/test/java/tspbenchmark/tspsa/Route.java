/**
 * projectName: TSP
 * fileName: Path.java
 * packageName: cn.cn.zs
 * date: 2021-01-17 16:58
 * copyright(c) 2019-2021 hust
 */
package tspbenchmark.tspsa;
import tspbenchmark.City;
import tspbenchmark.CityInstance;
/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Path
 * @packageName: cn.cn.zs
 * @data: 2021-01-17 16:58
 **/
public class Route{

    //下标库位编号 值为存储的货物
    private int[] bestRoute;
    private int[] tempRoute;
    private double bestCost;
    private double tempCost;   // 临时存放路径及其及其长度
    private CityInstance cityInstance ;
    public Route() {
        cityInstance = new CityInstance();
        bestCost = 0.0;
        tempCost = 0.0;
        bestRoute = new int[cityInstance.getNumCities()];
        tempRoute = new int[cityInstance.getNumCities()];
        // 初始化最佳路径以及路径长度
        for (int i = 0; i < cityInstance.getNumCities(); i++) {
            //先按编号形成初始解
            bestRoute[i] = i;
        }
        City[] cities = cityInstance.getCities();
        double res = 0;
        for (int i = 0; i < bestRoute.length-1; i++) {
            res += cities[bestRoute[i]].distanceFrom(cities[bestRoute[i+1]]);
        }
        res += cities[bestRoute[bestRoute.length-1]].distanceFrom(cities[bestRoute[0]]);
        bestCost = res;
    }
    public void generateNeighour()   {
        setTempRoute(bestRoute);
        int i = 0;
        int j = 0;
        while (i == j) {
            i = (int) (cityInstance.getNumCities() * Math.random());
            j = (int) (cityInstance.getNumCities() * Math.random());
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

        City[] cities = cityInstance.getCities();
        double res = 0;
        for (int k = 0; k < tempRoute.length-1; k++) {
            res += cities[tempRoute[k]].distanceFrom(cities[tempRoute[k+1]]);
        }
        res += cities[tempRoute[tempRoute.length-1]].distanceFrom(cities[tempRoute[0]]);
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

    public CityInstance getCityInstance() {
        return cityInstance;
    }

    public void setCityInstance(CityInstance cityInstance) {
        this.cityInstance = cityInstance;
    }
}
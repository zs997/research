/**
 * projectName: research
 * fileName: LocalSearchEDA.java
 * packageName: cn.zs.algorithm.localsearcheda
 * date: 2021-03-02 10:52
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.localsearcheda;
import cn.zs.algorithm.component.Individual;
import cn.zs.algorithm.component.Population;
import cn.zs.algorithm.eda.EDA;
import static cn.zs.algorithm.component.Params.storageCount;

/**
 * @version: V1.0
 * @author: zs
 * @className: LocalSearchEDA
 * @packageName: cn.zs.algorithm.localsearcheda
 * @data: 2021-03-02 10:52
 **/
public class LocalSearchEDA extends EDA {
    //局部搜索次数
    private int localSearchTimes ;
    /**
     * @param t
     * @param populationSize
     * @param superiorityCount
     * @param eliteCount
     * @param alpha
     * @param maxGenerations
     * @Parm: t 拣货策略
     * @Parm：populationSize：种群大小
     * @Parm：superiorityCount：优势群体数目
     * @Parm：eliteCount：精英数目
     * @Parm：alpha：学习率
     * @Parm：maxGenerations：最大迭代次数
     * @Parm:localSearchTimes:局部搜索次数     *
     */
    public LocalSearchEDA(Class t, int populationSize, int superiorityCount,
                          int eliteCount, double alpha, int maxGenerations,int localSearchTimes,double weight) {
        super(t, populationSize, superiorityCount, eliteCount, alpha, maxGenerations,weight);
        this.localSearchTimes = localSearchTimes;
    }
    @Override
    public Individual doEDA(){
        // Create cities
        Population population = new Population(this.populationSize, t,weight);

        int generation = 1;
        //初始化概率矩阵
        double[][] prob = new double[storageCount][storageCount];
        for (int i = 0; i < storageCount; i++) {
            for (int j = 0; j < storageCount; j++) {
                prob[i][j] = 1.0/storageCount;
            }
        }
        while (isTerminationConditionMet(generation, maxGenerations) == false) {
            evalPopulation(population);
            Individual fittest = population.getFittest(0);
            System.out.println("LocalSearch EDA:G"+generation+" Best distance: "
                    + fittest.getCost());
            Individual individual = localSearchIndividualByReverse(fittest);
            population.setIndividual(0,individual);
            Individual[] superiorityIndividuals = getSuperiorityIndividuals(population);
            prob = updateProbMatrix(superiorityIndividuals, prob);
            gerateNewPopution(population,prob);
            generation++;
        }
        evalPopulation(population);
        System.out.println("LocalSearch EDA:Stopped after " + maxGenerations + " generations.");
        System.out.println("LocalSearch EDA:Best : " + population.getFittest(0).getCost());
        return population.getFittest(0);
    }

    public Individual localSearchIndividualByReverse(Individual individual){
        Route route = new Route(individual);
        for (int i = 0; i < localSearchTimes; i++) {
            route.generateNeighour();
            double delta = route.getTempCost() - route.getBestCost();
            if (delta < 0) {
                route.setBestCost(route.getTempCost());
                route.setBestRoute(route.getTempRoute());
                System.out.println("localSearchByReverse works");
            }
        }
        int[] bestRoute = route.getBestRoute();
        Individual res = new Individual(t,bestRoute,weight);
        res.setCost(route.getBestCost());
        res.setFitness(1.0/route.getBestCost());
        return res;
    }
    public void localSearchEliteByReverse(Population population){
        Individual[] individuals = population.getIndividuals();
        for (int i = 0; i < eliteCount; i++) {
            Individual individual = individuals[i];
            Individual res = localSearchIndividualByReverse(individual);
            population.setIndividual(i,res);
        }
    }

}
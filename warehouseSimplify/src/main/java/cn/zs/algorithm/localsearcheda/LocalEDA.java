package cn.zs.algorithm.localsearcheda;

import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.Individual;
import cn.zs.algorithm.component.Population;

import java.util.ArrayList;
import java.util.Random;

import static cn.zs.algorithm.component.Params.storageCount;
@Deprecated
public class LocalEDA<T extends Column>{
    protected int populationSize;
    protected int maxGenerations;
    //精英数目
    protected int eliteCount;
    //概率矩阵学习率
    protected double alpha;
    //优势个体个数
    protected int superiorityCount;
    //模板
    protected Class<T> t;
    //局部搜索次数
    protected int localSearchTimes ;
    /**
     * @Parm: t 拣货策略
     * @Parm：populationSize：种群大小
     * @Parm：superiorityCount：优势群体数目
     * @Parm：eliteCount：精英数目
     * @Parm：alpha：学习率
     * @Parm：maxGenerations：最大迭代次数
     * @param:localSearchTimes: 局部搜索次数
     * */
    public LocalEDA(Class<T> t,int populationSize, int superiorityCount, int eliteCount, double alpha, int maxGenerations,int localSearchTimes) {
        this.t=t;
        this.populationSize = populationSize;
	    this.superiorityCount =superiorityCount;
        this.eliteCount = eliteCount;
	    this.alpha  = alpha;
	    this.maxGenerations = maxGenerations;
	    this.localSearchTimes = localSearchTimes;
	}
	public Individual doEDA(){
        // Create cities
        Population population = new Population(this.populationSize, t);

        int generation = 1;
        //初始化概率矩阵
        double[][] prob = new double[storageCount][storageCount];
        for (int i = 0; i < storageCount; i++) {
            for (int j = 0; j < storageCount; j++) {
                prob[i][j] = 1.0/storageCount;
            }
        }
//        double lamda = 0;
        // Start evolution loop
        while (isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            evalPopulation(population);
            Individual fittest = population.getFittest(0);
            System.out.println("Local EDA:G"+generation+" Best distance: "
                    + fittest.getCost());
//            lamda = Math.exp(-generation*2.0/maxGenerations);
//            if(Math.random() > lamda){
//                Individual individual = localSearchIndividualByReverse(fittest);
//                population.setIndividual(0,individual);
//              //   localSearchEliteByReverse(population);
//            }
            Individual individual = localSearchIndividualByReverse(fittest);
            population.setIndividual(0,individual);
            Individual[] superiorityIndividuals = getSuperiorityIndividuals(population);
            prob = updateProbMatrix(superiorityIndividuals, prob);
            gerateNewPopution(population,prob);
           // evalPopulation(population);
            generation++;
        }
        evalPopulation(population);
        System.out.println("Stopped after " + maxGenerations + " generations.");
        System.out.println("Best distance: " + population.getFittest(0).getCost());
        return population.getFittest(0);
    }

    public double calcFitness(Individual individual){
        double fitness = individual.calculFitness();
        return fitness;
    }
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}
    public void evalPopulation(Population population){
        for (Individual individual : population.getIndividuals()) {
           this.calcFitness(individual);
        }
    }
    /**
     * 选择种群中最适应的几个
     * */
    public Individual[] getSuperiorityIndividuals(Population population){
        //先排序
        population.getFittest(0);
        Individual[] individuals = new Individual[superiorityCount];
        for (int i = 0; i < superiorityCount; i++) {
            individuals[i] = population.getIndividual(i);
        }
        return individuals;
    }
    /**
     * 计算概率
     * */
    public double[][] updateProbMatrix(Individual[] superiorityIndividuals, double[][] lastProbMatrix){
        int n = superiorityIndividuals[0].getChromosomeLength();
        int [][] countMatrix = new int[n][n];
        for (int i = 0; i < superiorityIndividuals.length; i++) {
            Individual individual = superiorityIndividuals[i];
            ArrayList<Integer> chromosome = individual.getChromosome();
            for (int pos = 0; pos < chromosome.size(); pos++) {
                Integer cityNo = chromosome.get(pos);
                countMatrix[cityNo][pos] += 1;
            }
        }
        //行 表示城市；  列 表示位置
        double [][] res = new double[n][n];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = ((countMatrix[i][j]*1.0)/superiorityCount) * alpha + (1-alpha)*lastProbMatrix[i][j];
            }
        }

        return res;
    }
    //抽样生成新种群
    public void gerateNewPopution(Population population, double [][] prob){
        //n个城市
        int n = prob.length;
        Random random = new Random();
        //排序
        population.getFittest(0);
        //抽样新个体  适应度高的个体保留
        for (int individualIndex = eliteCount;individualIndex < populationSize;individualIndex++){
            double[][] tempProb = new double[n][n];
            ArrayList<Integer> list = new ArrayList<>();
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    tempProb[u][v] = prob[u][v];
                }
            }
            //首位置
            int firstCity = random.nextInt(n);
            list.add(firstCity);
            //ints[0] = firstCity;
            for (int i = 1; i < n; i++) {
                tempProb[firstCity][i] = 0;
            }
            for (int position = 1; position < n;position++){
                /*****************归一化***************/
                double allProb = 0.0;
                for (int i = 0; i < n; i++) {
                    allProb += tempProb[i][position];
                }
                if (allProb == 0){
                    System.out.println(list);
                    int cityNo = random.nextInt(n);
                   while (list.contains(cityNo)){
                       cityNo = random.nextInt(n);
                    }
                   list.add(cityNo);
                    for (int i = position+1;i <n;i++){
                        tempProb[cityNo][i] = 0;
                    }
                }else {
                   // double addProb = 0.0;
                    for (int i = 0; i < n; i++) {
                        tempProb[i][position] = tempProb[i][position]/allProb;
                       // addProb += tempProb[i][position];
                       // System.out.print(tempProb[i][position]+",");
                    }
                 //   System.out.println();
                  //  tempProb[n-1][position] = 1-addProb;
                    /********************************/
                    double probPoint = random.nextDouble();
                    double sumProb = 0.0;
                    for (int cityNo = 0; cityNo <n;cityNo++){
                        sumProb += tempProb[cityNo][position];
                        if (sumProb >= probPoint ){
                            list.add(cityNo);
                          //  ints[position] = cityNo;
                            for (int i = position+1;i <n;i++){
                                tempProb[cityNo][i] = 0;
                            }
                            break;
                        }
                    }
                }
            }
            Individual individual = new Individual(t,list);
            population.setIndividual(individualIndex,individual);
        }
    }

    public void localSearchEliteByReverse(Population population){
        Individual[] individuals = population.getIndividuals();
        for (int i = 0; i < eliteCount; i++) {
            Individual individual = individuals[i];
            Individual res = localSearchIndividualByReverse(individual);
            population.setIndividual(i,res);
        }
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
        Individual res = new Individual(t,bestRoute);
        res.setCost(route.getBestCost());
        res.setFitness(1.0/route.getBestCost());
        return res;
    }
}

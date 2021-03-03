package tspbenchmark.tspedasabenchmark2;
import tspbenchmark.DistanceInstance;

import java.util.ArrayList;
import java.util.Random;

public class EDA {
    private int populationSize;
    private int maxGenerations;
    private int eliteCount;
    //概率矩阵学习率
    private double alpha;
    //优势个体个数
    private int superiorityCount;
    private DistanceInstance distanceInstance;
    //模拟退火迭代次数
    private int iteratorNum;
    //模拟退火 初始温度
    private double t0;
    //模拟退火 终止温度
    private double tn;
    //退温系数
    private double belta;
    //用以判断进行sa还是eda
    private double lamda = 1;
    private int hillCoefficient = 2;
    private int generation;
	public EDA(int populationSize,int superiorityCount,double alpha
            ,int maxGenerations,int eliteCount,int iteratorNum
            ,double t0,double tn,double belta) {
	    this.maxGenerations = maxGenerations;
	    this.alpha  = alpha;
	    this.superiorityCount =superiorityCount;
        this.populationSize = populationSize;
        this.eliteCount = eliteCount;
        this.iteratorNum = iteratorNum;
        this.t0 = t0;
        this.tn = tn;
        this.belta = belta;
	}
	public void doEDA(DistanceInstance distanceInstance){
        // Create cities
        this.distanceInstance = distanceInstance;
        int numCities = distanceInstance.getCityNum();
        Population population = initPopulation(numCities);


         generation = 1;
        //初始化概率矩阵
        double[][] prob = new double[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                prob[i][j] = 1.0/numCities;
            }
        }
        // Start evolution loop
        while (isTerminationConditionMet(generation, maxGenerations) == false) {
            lamda = Math.exp(-generation*1.0/maxGenerations);
            evalPopulation(population);
            double bestDistance = Route.calculDistance(population.getFittest(0), distanceInstance);
            System.out.println("G"+generation+" Best distance: "
                    + bestDistance);
            Individual[] superiorityIndividuals = getSuperiorityIndividuals(population);
            prob = updateProbMatrix(superiorityIndividuals, prob);
            System.out.println(1.1-lamda);
            //前面的代尽量使用eda
            if(Math.random() <= lamda){
                // System.out.println("eda");
                 gerateNewPopution(population,prob);
            }else {
                System.out.println("SA");
                 updatePopulationBySA(population);
            }
            // Print fittest individual from population
            //选择优势群体

            generation++;
        }

        evalPopulation(population);
        double distance = Route.calculDistance(population.getFittest(0), distanceInstance);
        System.out.println("improved sa:" + distance);
    }

    public Population initPopulation(int chromosomeLength){
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }
    public double calcFitness(Individual individual){
        double distance = Route.calculDistance(individual, distanceInstance);
        individual.setCost(distance);
        double fitness = 1.0/distance;
        individual.setFitness(fitness);
        return fitness;
    }
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}
	public boolean isTerminationConditionMet(int generationsCount,double t0){
	    return (generationsCount > maxGenerations) ||(t0 < tn);
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
        int n =lastProbMatrix.length;
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
    /**
     * 计算概率
     * */
    public double[][] updateProbMatrix(Individual[] superiorityIndividuals, double[][] lastProbMatrix,double alpha){
        int n =lastProbMatrix.length;
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
                for (int i = 0; i < n; i++) {
                    tempProb[i][position] = tempProb[i][position]/allProb;
                }
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
            Individual individual = new Individual(list);
            population.setIndividual(individualIndex,individual);
        }
    }

    //模拟退火 更新优势群体
    public Individual[] updateIndividualsBySA(Individual[] individuals){
        for (int individualIndex = 0;individualIndex <individuals.length;individualIndex++) {
            Individual individual = individuals[individualIndex];
            individuals[individualIndex] = updateIndividualBySA(individual);
        }
        return individuals;
    }
    /**
     * 模拟退火 更新种群
     * */
    public void updatePopulationBySA(Population population){
        Individual[] individuals = population.getIndividuals();
        for (int individualIndex = eliteCount;individualIndex <superiorityCount;individualIndex++) {
            Individual individual = updateIndividualBySA(individuals[individualIndex]);
            population.setIndividual(individualIndex,individual);
        }

    }

    public Individual updateIndividualBySA(Individual oldIndividual){
        double t = t0;
        int bChange = 0;
        int s = 0;
        Route route = new Route(oldIndividual, distanceInstance);
        while (t >= tn){
            bChange = 0;
            for (int i = 0; i < iteratorNum; i++) {
                route.generateNeighour();
                double delta = route.getTempCost() - route.getBestCost();
                if (delta <= 0) {
                    route.setBestCost(route.getTempCost());
                    route.setBestRoute(route.getTempRoute());
                } else {
                    double random = Math.random();
                    double eps = Math.exp(-delta / t);
                    if (eps > random && eps < 1) {
                        route.setBestCost(route.getTempCost());
                        route.setBestRoute(route.getTempRoute());
                    }
                }
            }
          //  t *= belta;
            t = belta*(Math.pow(t0,hillCoefficient)
                    /(Math.pow(t0,hillCoefficient)+Math.pow(generation,hillCoefficient)));
            //没有更新解
            if (bChange == 0)
                s++;
            else
                s = 0;
            //连续两次没有更新解
            if (s == 2)
                break;
        }
        int[] bestRoute = route.getBestRoute();
        return new Individual(bestRoute);
    }
}

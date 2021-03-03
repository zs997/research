package tspbenchmark.tspedagabenchmark;
import jnr.ffi.annotations.In;
import tspbenchmark.DistanceInstance;

import java.security.Policy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class EDA {
    private int populationSize;
    private int maxGenerations;
    //精英数目
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
    //模拟退火 温度函数中的参数  爬山系数
    private int hillCoefficient = 2;
    //退温系数
    private double belta;

    //用以判断进行sa还是eda
    private double lamda = 1;
    private int generation;

    //遗传算法 选择操作中作为父亲2的备选数目
    protected int tournamentSize=5;
    //交叉
    private double crossoverRate = 0.9;
    //编译
    private double mutationRate = 0.01;

    //局部搜索 次数
    private int localSearchTimes = 20;
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
            lamda = Math.exp(-generation*2.0/maxGenerations);

            evalPopulation(population);
            Individual fittest = population.getFittest(0);
            double bestDistance = Route.calculDistance(fittest, distanceInstance);
            System.out.println("G"+generation+" Best distance: "
                    + bestDistance);
            //可以对最优个体进行 localsearch
          //  population.setIndividual(0, localSearchIndividualByReverse(population.getIndividual(0)));
            localSearchEliteByReverse(population);
            Individual[] superiorityIndividuals = getSuperiorityIndividuals(population);


            prob = updateProbMatrix(superiorityIndividuals, prob);
            gerateNewPoputionByProbMatrix(population,prob);

            //前面的代尽量使用eda
//            if(Math.random() <= lamda){
//                // System.out.println("eda");
//                 gerateNewPopution(population,prob);
//            }else {
//                System.out.println("ga");
//                population = crossoverPopulation(population);
//                population = mutatePopulation(population);
//            }

            generation++;
        }

        evalPopulation(population);
        double distance = Route.calculDistance(population.getFittest(0), distanceInstance);
        System.out.println("final distance:" + distance);
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
     * 选择优势群体
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
    public double[][] updateProbMatrix(Individual[] superiorityIndividuals, double[][] lastProbMatrix, double alpha){
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
    public void gerateNewPoputionByProbMatrix(Population population, double [][] prob){
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
                    // System.out.println(list);
                    int cityNo = random.nextInt(n);
                    while (list.contains(cityNo)){
                        cityNo = random.nextInt(n);
                    }
                    list.add(cityNo);
                    for (int i = position+1;i <n;i++){
                        tempProb[cityNo][i] = 0;
                    }
                }else {
                    for (int i = 0; i < n; i++) {
                        tempProb[i][position] = tempProb[i][position] / allProb;
                    }
                    double probPoint = random.nextDouble();
                    double sumProb = 0.0;
                    for (int cityNo = 0; cityNo < n; cityNo++) {
                        sumProb += tempProb[cityNo][position];
                        if (sumProb >= probPoint) {
                            list.add(cityNo);
                            //  ints[position] = cityNo;
                            for (int i = position + 1; i < n; i++) {
                                tempProb[cityNo][i] = 0;
                            }
                            break;
                        }
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

    public Population mutatePopulation(Population population){
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            // Skip mutation if this is an elite individual
            if (populationIndex >= this.eliteCount) {
                // System.out.println("Mutating population member "+populationIndex);
                // Loop over individual's genes
                for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                    // System.out.println("\tGene index "+geneIndex);
                    // Does this gene need mutation?
                    if (this.mutationRate > Math.random()) {
                        // Get new gene position
                        int newGenePos = (int) (Math.random() * individual.getChromosomeLength());
                        // Get genes to swap
                        int gene1 = individual.getGene(newGenePos);
                        int gene2 = individual.getGene(geneIndex);
                        // Swap genes
                        individual.setGene(geneIndex, gene1);
                        individual.setGene(newGenePos, gene2);
                    }
                }
            }

            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }
    public void mutateIndividuals(Individual [] individuals){
        for (int i = eliteCount; i < individuals.length; i++) {
            mutateIndividual(individuals[i]);
        }
    }
    public void mutateIndividual(Individual individual){
        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            // System.out.println("\tGene index "+geneIndex);
            // Does this gene need mutation?
            if (this.mutationRate > Math.random()) {
                // Get new gene position
                int newGenePos = (int) (Math.random() * individual.getChromosomeLength());
                // Get genes to swap
                int gene1 = individual.getGene(newGenePos);
                int gene2 = individual.getGene(geneIndex);
                // Swap genes
                individual.setGene(geneIndex, gene1);
                individual.setGene(newGenePos, gene2);
            }
        }
    }
    public void localSearchBestByMutation(Individual individual){
//        for (int i = 0; i < 10; i++) {
//
//        }
        Individual temp = new Individual(individual.getChromosome());
        for (int i = 0; i < 10; i++) {

            for (int geneIndex = 0; geneIndex < temp.getChromosomeLength(); geneIndex++) {
                // System.out.println("\tGene index "+geneIndex);
                // Does this gene need mutation?
                if (this.mutationRate > Math.random()) {
                    // Get new gene position
                    int newGenePos = (int) (Math.random() * temp.getChromosomeLength());
                    // Get genes to swap
                    int gene1 = temp.getGene(newGenePos);
                    int gene2 = temp.getGene(geneIndex);
                    // Swap genes
                    temp.setGene(geneIndex, gene1);
                    temp.setGene(newGenePos, gene2);
                }
            }
            double distance = Route.calculDistance(temp, distanceInstance);
            if (distance < individual.getCost()){
                System.out.println("localsearch works");
                for (int j = 0; j < temp.getChromosome().size(); j++) {
                    individual.setGene(j,temp.getChromosome().get(j));
                }
                individual.setCost(distance);
                individual.setFitness(1.0/distance);
            }
        }
    }
    public Individual localSearchIndividualByReverse(Individual individual){
        Route route = new Route(individual, distanceInstance);
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
        Individual res = new Individual(bestRoute);
        for (int i = 0; i < bestRoute.length; i++) {
            res.setGene(i,bestRoute[i]);
        }
        res.setCost(route.getBestCost());
        res.setFitness(1.0/route.getBestCost());
        return res;
    }
    /**
     * 对精英中每个个体进行局部搜索 产生了2*elitecount的群体  选出精英
     * */
    public void localSearchEliteByReverseCandidate(Population population){
        Individual[] individuals = population.getIndividuals();
        Individual[] candidateIndividuals = new Individual[2 * eliteCount];
        for (int i = 0; i < eliteCount; i++) {
            Individual individual = individuals[i];
            candidateIndividuals[i] = individual;
            Individual res = localSearchIndividualByReverse(individual);
            candidateIndividuals[i+eliteCount] = res;
        }
        // Order population by fitness
        Arrays.sort(candidateIndividuals, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                } else if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                }
                return 0;
            }
        });
        for (int i = 0; i < eliteCount; i++) {
            population.setIndividual(i,candidateIndividuals[i]);
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
    public Individual selectParent(Population population) {
        // Create tournament
        Population tournament = new Population(tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }



    public Population crossoverPopulation(Population population){
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // Get parent1
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.eliteCount) {
                // Find parent2 with tournament selection
                Individual parent2 = this.selectParent(population);

                // Create blank offspring chromosome
                int offspringChromosome[] = new int[parent1.getChromosomeLength()];
                Arrays.fill(offspringChromosome, -1);
                Individual offspring = new Individual(offspringChromosome);

                // Get subset of parent chromosomes
                int substrPos1 = (int) (Math.random() * parent1.getChromosomeLength());
                int substrPos2 = (int) (Math.random() * parent1.getChromosomeLength());

                // make the smaller the start and the larger the end
                final int startSubstr = Math.min(substrPos1, substrPos2);
                final int endSubstr = Math.max(substrPos1, substrPos2);

                // Loop and add the sub tour from parent1 to our child
                for (int i = startSubstr; i < endSubstr; i++) {
                    offspring.setGene(i, parent1.getGene(i));
                }

                // Loop through parent2's city tour
                for (int i = 0; i < parent2.getChromosomeLength(); i++) {
                    int parent2Gene = i + endSubstr;
                    if (parent2Gene >= parent2.getChromosomeLength()) {
                        parent2Gene -= parent2.getChromosomeLength();
                    }

                    // If offspring doesn't have the city add it
                    if (offspring.containsGene(parent2.getGene(parent2Gene)) == false) {
                        // Loop to find a spare position in the child's tour
                        for (int ii = 0; ii < offspring.getChromosomeLength(); ii++) {
                            // Spare position found, add city
                            if (offspring.getGene(ii) == -1) {
                                offspring.setGene(ii, parent2.getGene(parent2Gene));
                                break;
                            }
                        }
                    }
                }

                // Add child
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

}

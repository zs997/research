package cn.zs.algorithm.eda;

import cn.zs.algorithm.component.Column;

import java.util.ArrayList;
import java.util.Random;
import  static cn.zs.algorithm.component.Params.*;
public class EDA <T extends Column>{
    private int populationSize;
    private int maxGenerations;
    private int eliteCount;
    //概率矩阵学习率
    private double alpha;
    //优势个体个数
    private int superiorityCount;
    private Class<T> t;
	public EDA(int populationSize,int superiorityCount,double alpha,int maxGenerations,int eliteCount,Class<T> t) {
	    this.maxGenerations = maxGenerations;
	    this.alpha  = alpha;
	    this.superiorityCount =superiorityCount;
        this.populationSize = populationSize;
        this.eliteCount = eliteCount;
        this.t=t;
	}
	public void doEDA(){
        // Create cities
        Population population = new Population(this.populationSize, t);
        evalPopulation(population);
        Individual fittest = population.getFittest(0);
        System.out.println("Start cost: " + fittest.getCost());
        int generation = 1;
        //初始化概率矩阵
        double[][] prob = new double[storageCount][storageCount];
        for (int i = 0; i < storageCount; i++) {
            for (int j = 0; j < storageCount; j++) {
                prob[i][j] = 1.0/storageCount;
            }
        }
        // Start evolution loop
        while (isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            System.out.println("G"+generation+" Best distance: " + population.getFittest(0).getCost());
            // Apply crossover
            Individual[] superiorityIndividuals = getSuperiorityIndividuals(population);
            prob = updateProbMatrix(superiorityIndividuals, prob);
            gerateNewPopution(population,prob);
            evalPopulation(population);
            generation++;
        }
        Individual fittest1 = population.getFittest(0);
        System.out.println("Stopped after " + maxGenerations + " generations.");
        System.out.println("Best distance: " + fittest1.getCost());
        System.out.println(fittest1.getChromosome());
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
    public double[][] updateProbMatrix(Individual[] superiorityIndividuals,double[][] lastProbMatrix){
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
//        System.out.println("-------------countMatrix-------------------");
//        for (int i = 0; i < countMatrix.length; i++) {
//            for (int j = 0; j < countMatrix[i].length; j++) {
//                System.out.print(countMatrix[i][j]+",");
//            }
//            System.out.println();
//        }
//        System.out.println("-----------------lastprobMatrix---------------------------");
//        for (int i = 0; i < lastProbMatrix.length; i++) {
//            for (int j = 0; j < lastProbMatrix[i].length; j++) {
//                System.out.print(lastProbMatrix[i][j]+",");
//            }
//            System.out.println();
//        }
        //行 表示城市；  列 表示位置
        double [][] res = new double[n][n];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = ((countMatrix[i][j]*1.0)/superiorityCount) * alpha + (1-alpha)*lastProbMatrix[i][j];
            }
        }
//        System.out.println("-----------------resMatrix---------------------------");
//        for (int i = 0; i < res.length; i++) {
//            for (int j = 0; j < res[i].length; j++) {
//                System.out.print(res[i][j]+",");
//            }
//            System.out.println();
//        }
        return res;
    }
    //抽样生成新种群
    public void gerateNewPopution(Population population,double [][] prob){
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
}

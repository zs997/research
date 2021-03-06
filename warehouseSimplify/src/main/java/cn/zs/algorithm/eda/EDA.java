package cn.zs.algorithm.eda;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.Individual;
import cn.zs.algorithm.component.Population;

import java.util.ArrayList;
import java.util.Random;

import static cn.zs.algorithm.component.Params.storageCount;

public class EDA <T extends Column>{
    protected Class<T> t;
    protected int populationSize;
    //优势个体个数
    protected int superiorityCount;
    protected int eliteCount;
    //概率矩阵学习率
    protected double alpha;
    protected int maxGenerations;
    protected  double weight;
    /**
     * @Parm: t 拣货策略
     * @Parm：populationSize：种群大小
     * @Parm：superiorityCount：优势群体数目
     * @Parm：eliteCount：精英数目
     * @Parm：alpha：学习率
     * @Parm：maxGenerations：最大迭代次数
     * */
	public EDA(Class<T> t,int populationSize,int superiorityCount,int eliteCount,double alpha,int maxGenerations,double weight) {
        this.t=t;
        this.populationSize = populationSize;
	    this.superiorityCount =superiorityCount;
        this.eliteCount = eliteCount;
	    this.alpha  = alpha;
	    this.maxGenerations = maxGenerations;

	    this.weight =weight;
	}
	public Individual doEDA(){
        // Create cities
        Population population = new Population(this.populationSize,t,weight);
        evalPopulation(population);
        System.out.println("EDA Start cost: " +  population.getFittest(0).getCost());
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
            System.out.println("EDA:G"+generation+" Best distance: " + population.getFittest(0).getCost());
            // Apply crossover
            Individual[] superiorityIndividuals = getSuperiorityIndividuals(population);
            prob = updateProbMatrix(superiorityIndividuals, prob);
            gerateNewPopution(population,prob);
            evalPopulation(population);
            generation++;
        }
        Individual fittest = population.getFittest(0);
        System.out.println("EDA Stopped after " + maxGenerations + " generations.");
        System.out.println("EDA Best distance: " + fittest.getCost());
        System.out.println(fittest.getChromosome());
        return fittest;
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
            //对库位分配问题 pos表示库位  cityNo表示货物
            for (int pos = 0; pos < chromosome.size(); pos++) {
                Integer cityNo = chromosome.get(pos);
                countMatrix[cityNo][pos] += 1;
            }
        }
        double [][] res = new double[n][n];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = ((countMatrix[i][j]*1.0)/superiorityCount) * alpha + (1-alpha)*lastProbMatrix[i][j];
            }
        }
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
            //保证有效个体
//            if(list.size() != storageCount){
//                MyDataWriter myDataWriter = new MyDataWriter();
//                System.out.println(list);
//                CommonData commonData = new CommonData();
//                commonData.setPath("d:\\works\\data\\error\\");
//
//                CsvContent csvContent = new CsvContent();
//                csvContent.setTitile(list.toString());
//                csvContent.setCsvDataMatrix(new String[0][0]);
//                commonData.setData(csvContent);
//
//                myDataWriter.write(commonData);
//                continue;
//            }
            if(list.size() != storageCount){
                System.out.println("error:"+list);
            }
            Individual individual = new Individual(t,list,weight);
            population.setIndividual(individualIndex,individual);
        }
    }
}

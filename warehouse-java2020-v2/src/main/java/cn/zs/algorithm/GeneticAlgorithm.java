package cn.zs.algorithm;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.ColumnR;
import cn.zs.view.LineView;

import java.util.ArrayList;
import java.util.Arrays;
public class GeneticAlgorithm <T extends Column>{
    public static int maxGenerations = 300;
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
	protected int tournamentSize;

    public GeneticAlgorithm() {

    }

    /**
     * 遗传算法步骤
     * */
    public  void doGA(Class<T> t) {
        // Initial GA
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.9, 2, 5);
        // Initialize population
        Population population = ga.initPopulation();
        // Evaluate population
        ga.evalPopulation(population,t);
        // Keep track of current generation
        int generation = 1;
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<ArrayList<Double>> y = new ArrayList<>();
        ArrayList<Double> y1 = new ArrayList<>();
        ArrayList<String> stackBar = new ArrayList<>();
        stackBar.add("one");
        y.add(y1);
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            x.add(generation);
            Individual fittest = population.getFittest(0);
            double cost = fittest.getCost();
            System.out.println(generation+"  "+ cost);
            y1.add(cost);
            // Apply crossover
            population = ga.crossoverPopulation(population);
            // Apply mutation
            population = ga.mutatePopulation(population);
            // Evaluate population
            ga.evalPopulation(population,t);
            // Increment the current generation
            generation++;
        }
        Individual fittest = population.getFittest(0);
        ArrayList<Integer> chromosome = fittest.getChromosome();
        System.out.println(chromosome);
        LineView.printPic(x,y,stackBar);
        System.out.println("Stopped after " + maxGenerations + " generations.");
    }

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
                            int tournamentSize) {
		
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}
    /**
     * Initialize population
     * @return population The initial population generated
     */
    public Population initPopulation(){
        // Initialize population
        Population population = new Population(populationSize);
        return population;
    }
	/**
	 * Check if population has met termination condition -- this termination
	 * condition is a simple one; simply check if we've exceeded the allowed
	 * number of generations.
	 *
	 * @param generationsCount
	 *            Number of generations passed
	 * @param maxGenerations
	 *            Number of generations to terminate after
	 * @return boolean True if termination condition met, otherwise, false
	 */
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}

    /**
     * Evaluate population -- basically run calcFitness on each individual.     *
     * @param population the population to evaluate     *
     */
    public void evalPopulation(Population population,Class<T> t)  {
        double populationFitness = 0;
        // Loop over population evaluating individuals and summing population fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += individual.calculFitness(t);
        }
        double avgFitness = populationFitness / population.size();
        population.setPopulationFitness(avgFitness);
    }
	/** Selects parent for crossover using tournament selection
	 * Tournament selection was introduced in Chapter 3
	 * @param population
	 * @return The individual selected as a parent
	 */
	public Individual selectParent(Population population) {
		// Create tournament
		Population tournament = new Population(this.tournamentSize);
		// Add random individuals to the tournament
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			Individual tournamentIndividual = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndividual);
		}

		// Return the best
		return tournament.getFittest(0);
	}


    /**
	 * Ordered crossover mutation
	 *
	 * Chromosomes in the TSP require that each city is visited exactly once.
	 * Uniform crossover can break the chromosome by accidentally selecting a
	 * city that has already been visited from a parent; this would lead to one
	 * city being visited twice and another city being skipped altogether.
	 *
	 * Additionally, uniform or random crossover doesn't really preserve the
	 * most important aspect of the genetic information: the specific order of a
	 * group of cities.
	 *
	 * We need a more clever crossover algorithm here. What we can do is choose
	 * two pivot points, add chromosomes from one parent for one of the ranges,
	 * and then only add not-yet-represented cities to the second range. This
	 * ensures that no cities are skipped or visited twice, while also
	 * preserving ordered batches of cities.
	 *
	 * @param population
	 * @return The new population
	 */
    public Population crossoverPopulation(Population population){
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // Get parent1
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
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

    /**
	 * Apply mutation to population
	 *
	 * Because the traveling salesman problem must visit each city only once,
	 * this form of mutation will randomly swap two genes instead of
	 * bit-flipping a gene like in earlier examples.
	 *
	 * @param population
	 *            The population to apply mutation to
	 * @return The mutated population
	 */
    public Population mutatePopulation(Population population){
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);
        
        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);
            // Skip mutation if this is an elite individual
            if (populationIndex >= this.elitismCount) {   
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
}

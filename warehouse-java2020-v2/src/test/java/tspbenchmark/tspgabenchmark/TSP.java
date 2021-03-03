package tspbenchmark.tspgabenchmark;

import cn.zs.tools.LineView;
import tspbenchmark.DistanceInstance;

import java.util.ArrayList;

/**
 * Main, executive class for the Traveling Salesman Problem.
 * 
 * We don't have a real list of cities, so we randomly generate a number of them
 * on a 100x100 map.
 * 
 * The TSP requires that each city is visited once and only once, so we have to
 * be careful when initializing a random Individual and also when applying
 * crossover and mutation. Check out the GeneticAlgorithm class for
 * implementations of crossover and mutation for this problem.
 * 
 * @author bkanber
 *
 */
public class TSP {
	public static int maxGenerations = 5000;
	public static void main(String[] args) {
		
		// Create cities
		DistanceInstance distanceInstance = DistanceInstance.getInstance48();
		int cityNum = distanceInstance.getCityNum();
		double[][] distanceMatrix = distanceInstance.getDistanceMatrix();
		// Initial GA
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 10, 5);

		// Initialize population
		Population population = ga.initPopulation(cityNum);

		// Evaluate population
		ga.evalPopulation(population, distanceMatrix);

		System.out.println("Start Distance: " + Tool.calculDistance(population.getFittest(0), distanceMatrix));

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
			double dis = Tool.calculDistance(population.getFittest(0), distanceMatrix);
			System.out.println("G"+generation+" Best distance: " + dis);
            x.add(generation);
            y1.add(dis);
			// Apply crossover
			population = ga.crossoverPopulation(population);

			// Apply mutation
			population = ga.mutatePopulation(population);

			// Evaluate population
			ga.evalPopulation(population, distanceMatrix);

			// Increment the current generation
			generation++;
		}
        LineView.printPic(x,y,stackBar);
		System.out.println("Stopped after " + maxGenerations + " generations.");
		System.out.println("Best distance: " + Tool.calculDistance(population.getFittest(0),distanceMatrix));

	}
}

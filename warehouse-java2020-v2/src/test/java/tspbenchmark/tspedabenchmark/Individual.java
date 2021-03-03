package tspbenchmark.tspedabenchmark;
import java.util.ArrayList;
import java.util.Collections;

public class Individual {
	private ArrayList<Integer> chromosome;
	private double fitness = -1;
	public Individual(int[] chromosome) {
		// Create individualchromosome
		this.chromosome = new ArrayList<>();
		for (int i = 0; i < chromosome.length; i++) {
			this.chromosome.add(chromosome[i]);
		}
	}
	public Individual(int chromosomeLength){
		this.chromosome = new ArrayList<>();
		for (int i = 0; i < chromosomeLength; i++) {
			this.chromosome.add(i);
		}
		Collections.shuffle(this.chromosome);
	}
	public Individual(ArrayList<Integer> chromosome) {
		// Create random individual
		this.chromosome = new ArrayList<>();
		for (int i = 0; i < chromosome.size(); i++) {
			this.chromosome.add(chromosome.get(i));
		}
	}
	public ArrayList<Integer> getChromosome() {
		return this.chromosome;
	}
	public int getChromosomeLength() {
		return this.chromosome.size();
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getFitness() {
		return this.fitness;
	}
}

package cn.zs.algorithm.eda;
import cn.zs.algorithm.component.Column;
import java.util.Arrays;
import java.util.Comparator;
public class Population <T extends Column> {
//	private Class<T> t;
	private Individual population[];
	public Population(int populationSize,Class<T> t) {
		//this.t = t;
		population = new Individual[populationSize];
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			Individual individual = new Individual(t);
			population[individualCount] = individual;
		}
	}
	public Individual[] getIndividuals() {
		return population;
	}
	public Individual getFittest(int offset) {
		// Order population by fitness
		Arrays.sort(this.population, new Comparator<Individual>() {
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
		return this.population[offset];
	}
	public Individual setIndividual(int offset, Individual individual) {
		return population[offset] = individual;
	}
	public Individual getIndividual(int offset) {
		return population[offset];
	}
}
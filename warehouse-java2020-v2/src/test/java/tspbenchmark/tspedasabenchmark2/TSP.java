package tspbenchmark.tspedasabenchmark2;

import tspbenchmark.DistanceInstance;

public class TSP {
	public static void main(String[] args) {
		DistanceInstance instance = DistanceInstance.getInstance48();
		EDA eda = new EDA(90, 9*4
				, 0.1, 100000,2
				,100,100, 0.005,0.95);
		eda.doEDA(instance);
	}
}

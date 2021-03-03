package tspbenchmark.tspedagabenchmark;

import tspbenchmark.DistanceInstance;

public class TSP {
	public static void main(String[] args) {
		DistanceInstance instance = DistanceInstance.getInstance24();
		EDA eda = new EDA(100, 40
				, 1, 5000,10
				,100,100, 0.005,0.95);
		eda.doEDA(instance);
	}
}

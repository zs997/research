package tspbenchmark.tspedasabenchmark;

import tspbenchmark.DistanceInstance;

public class TSP {
	public static void main(String[] args) {
		DistanceInstance instance = DistanceInstance.getInstance48();
		EDA eda = new EDA(100, 10*4
				, 1, 10000,5
				,100,100, 0.0005,0.95);
		eda.doEDA(instance);
	}
}

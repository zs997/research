package tspbenchmark.tspedabenchmark;

public class TSP {
	public static void main(String[] args) {
		EDA eda = new EDA(100, 10*4, 1, 10000,10);
		eda.doEDA();
	}
}

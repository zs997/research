package tspbenchmark.tspreda;

public class TSP {
	public static void main(String[] args) {
		EDA eda = new EDA(150, 15*4, 0.1, 800,15);
		eda.doEDA();
	}
}

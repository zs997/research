package tspbenchmark.tspgabenchmark;

public class Tool {
    public static double calculDistance(Individual individual,double distanceMatrix[][]){
        int[] chromosome = individual.getChromosome();

        double distance = 0.0;
        for (int i = 0; i < chromosome.length-1; i++) {
            distance += distanceMatrix[chromosome[i]][chromosome[i+1]];
        }
        distance += distanceMatrix[chromosome[chromosome.length-1]][chromosome[0]];
        return  distance;
    }
}

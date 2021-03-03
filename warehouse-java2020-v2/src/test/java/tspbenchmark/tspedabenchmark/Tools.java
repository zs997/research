package tspbenchmark.tspedabenchmark;

import java.util.ArrayList;

public class Tools {
    public static double calculDistance(Individual individual, double distanceMatrix[][]){
        ArrayList<Integer> chromosome = individual.getChromosome();
        double distance = 0.0;
        for (int i = 0; i < chromosome.size()-1; i++) {
            distance += distanceMatrix[chromosome.get(i)][chromosome.get(i+1)];
        }
        distance += distanceMatrix[chromosome.get(chromosome.size()-1)][chromosome.get(0)];
        return  distance;
    }
}

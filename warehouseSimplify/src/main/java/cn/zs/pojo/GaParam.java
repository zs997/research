/**
 * projectName: research
 * fileName: GaParam.java
 * packageName: cn.zs.pojo
 * date: 2021-03-09 20:27
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

/**
 * @version: V1.0
 * @author: zs
 * @className: GaParam
 * @packageName: cn.zs.pojo
 * @data: 2021-03-09 20:27
 **/
public class GaParam {

    private final int tournamentSize;
    private final int populationSize;
    private final int elitismCount;
    private final double crossoverRate;
    private final double mutationRate;
    public GaParam(int populationSize, int tournamentSize, int elitismCount,
                   double crossoverRate, double mutationRate) {
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.elitismCount = elitismCount;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getElitismCount() {
        return elitismCount;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }
}
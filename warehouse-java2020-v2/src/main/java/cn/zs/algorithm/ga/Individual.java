/**
 * projectName: research
 * fileName: GAEntity.java
 * packageName: cn.zs.algorithm
 * date: 2021-01-04 14:42
 * copyright(c) 2019-2021 hust
 * 库位编号：
 * 4 9
 * 3 8
 * 2 7
 * 1 6
 * 0 5  ...
 */
package cn.zs.algorithm.ga;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.Coordinate;

import java.util.*;

import static cn.zs.algorithm.component.Params.*;
/**
 * @version: V1.0
 * @author: zs
 * @className: GAEntity
 * @packageName: cn.zs.algorithm
 * @data: 2021-01-04 14:42
 * @description:基因个体 基因个体
 **/
public class Individual<T extends Column>{
    //计算目标函数用
    private ArrayList<Column> columns ;
    //长度目标
    private double lengthCost;
    //离散目标
    private double spreadCost;
    //下标是库位号码  值是该库位存放的货物编号
    private ArrayList<Integer> chromosome ;
    //key是货物编号  value是库位坐标 编号
    private HashMap<Integer, Coordinate> coordinateMap ;
    private double fitness;
    private double cost;
    private Class<T> t;
    /**
     * 产生随机染色体
     * 由基因 同步其他库位分配数据
     * */
    public  Individual(Class<T> t){
        this.t = t;
        ArrayList<Integer> g = new ArrayList<>();
        for (int i = 0; i < storageCount; i++) {
            g.add(i);
        }
       // Collections.shuffle(g);
        chromosome = g;
    }
    /**
     * 产生指导性染色体
     * */
    public Individual(Class<T> t,ArrayList<Integer> chromosome){
        this.chromosome = chromosome;
        this.t = t;
    }
    public Individual(Class<T> t,int [] chromosome){
        this.chromosome = new ArrayList<>();
        this.t = t;
        for (int i = 0; i < chromosome.length; i++) {
            this.chromosome.add(chromosome[i]);
        }
    }
    /**
     * 通过染色体 同步其他数据
     * */
    private  void synchronizGene() throws Exception {
        columns = new ArrayList<>();
        coordinateMap = new HashMap<>();
        //遍历库位  查看基因分配的货物编号
        for (int i = 0; i < M; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < N; j++) {
                Coordinate coordinate = new Coordinate();
                coordinate.setColNo(i);
                coordinate.setRowNo(j);
                coordinate.calibrationByCoordinate();
                //库位编号
                int no = coordinate.getNo();
                //货物编号
                Integer itemNo = chromosome.get(no);
                temp.add(itemNo);
               // temp[j] = itemNo;
                coordinateMap.put(itemNo,coordinate);
            }
            Column column = t.newInstance();
            column.setLocations(temp);
            this.columns.add(column);
        }
    }
    /**
     * 计算路径期望
     * */
    private void calculLengthCost(){
        double res = 0;
        HashSet<Integer> usedSet = new HashSet<>();
        double lastEvenProb = 1;
        double lastEnterProb = 0;
        double lastFirstProb = 1;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            ArrayList<Integer> locations = column.getLocations();
            for (int j = 0; j < locations.size(); j++) {
                usedSet.add(locations.get(j));
            }
            column.calculCost(i+1,usedSet,lastEvenProb,lastEnterProb,lastFirstProb);

            lastFirstProb =  lastFirstProb * (1 - lastEnterProb);
            lastEnterProb = column.getEnterProb();
            lastEvenProb = column.getEvenProb();
            res += column.getCost();
        }
        lengthCost = res;
    }
    /**
     * 计算离散度
     * */
    private void calculSpreadCost(){
        //总离散度
        double spreads = 0;
        for (int i = 0; i < itemGroups.size(); i++) {
            //每个分组的离散度
            ArrayList<Integer> items = itemGroups.get(i);
            double spread = 0;
            for (int j = 0; j < items.size(); j++) {
                Integer item1 = items.get(j);
                Coordinate c1 = coordinateMap.get(item1);
                for (int k = j+1; k < items.size(); k++) {
                    Integer item2 = items.get(k);
                    Coordinate c2 = coordinateMap.get(item2);
                    spread += c1.calculDistance(c2);
                }
            }
            spread = spread / items.size();

            spreads += spread;
        }
        spreadCost = spreads;
    }
    public double calculFitness() {
        try {
            synchronizGene();
        } catch (Exception e) {
            e.printStackTrace();
        }
        calculLengthCost();
        calculSpreadCost();
        cost = lengthCost + spreadCost;
        fitness = 1/cost;
        return fitness;

//        try {
//            synchronizGene();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        calculSpreadCost();
//        cost = spreadCost;
//        fitness = 1/cost;
//        return fitness;

//        try {
//            synchronizGene();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        calculLengthCost();
//        cost = lengthCost;
//        fitness =1/cost;
//        return fitness;
    }
    /**
     * 判断有无某基因
     * */
    public boolean containsGene(int gene) {
        for (int i = 0; i < chromosome.size(); i++) {
            if (chromosome.get(i) == gene) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    public void setChromosome(ArrayList<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setGene(int offset, int gene) {
        chromosome.set(offset,gene);
    }
    public int getGene(int offset) {
        return chromosome.get(offset);
    }

    public double getLengthCost() {
        return lengthCost;
    }

    public double getSpreadCost() {
        return spreadCost;
    }

    public int getChromosomeLength() {
        return chromosome.size();
    }

    public double getCost() {
        return cost;
    }
}
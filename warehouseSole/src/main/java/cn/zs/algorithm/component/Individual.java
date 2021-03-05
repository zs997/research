package cn.zs.algorithm.component;

import cn.zs.dao.CsvDataWriter;
import cn.zs.dao.MyDataWriter;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import org.python.antlr.ast.Str;

import java.util.*;

import static cn.zs.algorithm.component.Params.*;

public class Individual<T extends Column> {
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
	private double fitness=-1;
	private double cost;
	private Class<T> t;
	private double weight;
	//使用模板构造新个体 不计算值
	public Individual(Individual temp,double weight){
		this(temp.t,temp.getChromosome(),weight);
	}
	/**
	 * 产生随机染色体
	 * 由基因 同步其他库位分配数据
	 * */
	public Individual(Class<T> t,double weight){
		this.weight = weight;
		this.t = t;
		ArrayList<Integer> g = new ArrayList<>();
		for (int i = 0; i < storageCount; i++) {
			g.add(i);
		}
		 Collections.shuffle(g);
		chromosome = g;

//		int index = 0;
//		int[][] temp = new int[M][N];
//		for (int j = 0; j < temp[0].length; j++) {
//			if (j%2 == 0){
//				for (int i = 0; i < temp.length; i++) {
//					temp[i][j] = index++;
//				}
//			}else {
//				for (int i = temp.length-1;i>=0;i--){
//					temp[i][j] = index++;
//				}
//			}
//		}
//		ArrayList<Integer> list = new ArrayList<>();
//		for (int i = 0; i < temp.length; i++) {
//			for (int j = 0; j < temp[i].length; j++) {
//				list.add(temp[i][j]);
//			}
//		}
//		chromosome = list;
	}


	/**
	 * 产生指导性染色体
	 * */
	public Individual(Class<T> t, ArrayList<Integer> chromosome,double weight){
		this.weight = weight;
		// Create individualchromosome
		this.chromosome = new ArrayList<>();
		for (int i = 0; i < chromosome.size(); i++) {
			this.chromosome.add(chromosome.get(i));
		}
		this.t = t;
	}
	public Individual(Class<T> t, int [] chromosome,double weight){
		this.weight = weight;
		this.t = t;
		this.chromosome = new ArrayList<>();
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
				try {
					Integer itemNo = chromosome.get(no);
					temp.add(itemNo);
					// temp[j] = itemNo;
					coordinateMap.put(itemNo,coordinate);
				}catch (Exception e){
					MyDataWriter dataWriter = new CsvDataWriter();
					CommonData commonData = new CommonData();
					CsvContent csvContent = new CsvContent();
					commonData.setData(csvContent);
					commonData.setPath("d:\\works\\data\\chromosomeerror.csv");
					csvContent.setTitile("error log");

					ArrayList<String> list = new ArrayList<>();
					list.add(String.valueOf(no));
					list.add(this.chromosome.toString());
					String[][] matrix = new String[list.size()][1];
					for (int index = 0; index < list.size(); index++) {
						matrix[index][0] = list.get(index);
					}
					csvContent.setCsvDataMatrix(matrix);
					dataWriter.write(commonData);
					break;
				}
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
//		Set<Integer> integers = coordinateMap.keySet();
//		for (Integer integer : integers) {
//			System.out.println(integer+":"+coordinateMap.get(integer));
//		}
		boolean excpFlag = false;
		//总离散度
		double spreads = 0;
		for (int i = 0; i < itemGroups.size(); i++) {
			//每个分组的离散度
			ArrayList<Integer> items = itemGroups.get(i);
			double spread = 0;
			for (int j = 0; j < items.size(); j++) {
				Integer item1 = items.get(j);
				try {
					Coordinate c1 = coordinateMap.get(item1);
					for (int k = j+1; k < items.size(); k++) {
						Integer item2 = items.get(k);
						Coordinate c2 = coordinateMap.get(item2);
						spread += c1.calculDistance(c2);
					}
				}catch (Exception e){
					excpFlag = true;
					MyDataWriter dataWriter = new CsvDataWriter();
					CommonData commonData = new CommonData();
					CsvContent csvContent = new CsvContent();
					commonData.setData(csvContent);
					commonData.setPath("d:\\works\\data\\spreaderror.csv");
					csvContent.setTitile("error log");

					ArrayList<String> list = new ArrayList<>();
					list.add(this.chromosome.toString());
					Set<Integer> integers = coordinateMap.keySet();
					for (Integer integer : integers) {
						list.add(integer+":"+coordinateMap.get(integer));
					}
					String[][] matrix = new String[list.size()][1];
					for (int index = 0; index < list.size(); index++) {
						matrix[index][0] = list.get(index);
					}
					csvContent.setCsvDataMatrix(matrix);
					dataWriter.write(commonData);
					break;
				}
			}
			spread = spread / ((items.size())*(items.size()-1)/2);
			spreads += spread;
		}
		spreadCost = spreads/itemGroups.size();
		if (excpFlag){
			spreadCost = Double.MAX_VALUE;
		}
	}
	public double calculFitness() {
		try {
			synchronizGene();
		} catch (Exception e) {
			e.printStackTrace();
		}
		calculLengthCost();
		calculSpreadCost();
		cost = weight*lengthCost +(1-weight)*spreadCost;
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
	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Class<T> getT() {
		return t;
	}
}

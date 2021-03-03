/**
 * projectName: TSP
 * fileName: Path.java
 * packageName: cn.cn.zs
 * date: 2021-01-17 16:58
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.sa;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.Coordinate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import static cn.zs.algorithm.component.Params.*;
/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Path
 * @packageName: cn.cn.zs
 * @data: 2021-01-17 16:58
 **/
public class Assignment<T extends Column>{
    //计算目标函数用
    private ArrayList<Column> columns ;
    //key是货物编号  value是库位坐标 编号
    private HashMap<Integer, Coordinate> coordinateMap;
    //下标库位编号 值为存储的货物
    private int[] bestAssignment;
    private int[] tempAssignment;
    private double lengthCost;
    private double spreadCost;
    private double bestCost;
    private double tempCost;   // 临时存放路径及其及其长度
    private Class<T> t;
    public Assignment(Class<T> t) throws Exception {
        this.t = t;
        bestCost = 0.0;
        bestAssignment = new int[storageCount];
        tempCost = 0.0;
        tempAssignment = new int[storageCount];
        // 初始化最佳路径以及路径长度
        for (int i = 0; i < storageCount; i++) {
            //先按编号形成初始解
            bestAssignment[i] = i;
        }
        synchronizColumn(bestAssignment);
        calculLengthCost();
        calculSpreadCost();
        bestCost = lengthCost + spreadCost;
    }
    /**
     * 通过染色体 同步其他数据
     * */
    private  void synchronizColumn(int [] route) throws Exception {
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
                Integer itemNo = route[no];
                temp.add(itemNo);
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

    public void generateNeighour() throws Exception {
        setTempAssignment(bestAssignment);
        int i = 0;
        int j = 0;
        while (i == j) {
            i = (int) (storageCount * Math.random());
            j = (int) (storageCount * Math.random());
            if (i > j) {
                int x = i;
                i = j;
                j = x;
            }
        }
        while (i < j) {
            int exchange = tempAssignment[i];
            tempAssignment[i] = tempAssignment[j];
            tempAssignment[j] = exchange;
            i++;
            j--;
        }
        synchronizColumn(tempAssignment);
        calculLengthCost();
        calculSpreadCost();
        tempCost = lengthCost+spreadCost;
    }


    public int[] getBestAssignment() {
        return bestAssignment;
    }
    public void setBestAssignment(int[] bestAssignment) {
        this.bestAssignment = new int[bestAssignment.length];
        for (int i = 0; i < bestAssignment.length; i++) {
            this.bestAssignment[i] = bestAssignment[i];
        }
    }
    public int[] getTempAssignment() {
        return tempAssignment;
    }
    public void setTempAssignment(int[] tempAssignment) {
        this.tempAssignment = new int[tempAssignment.length];
        for (int i = 0; i < tempAssignment.length; i++) {
            this.tempAssignment[i] = tempAssignment[i];
        }
    }
    public double getTempCost() {
        return tempCost;
    }
    public void setTempCost(double tempCost) {
        this.tempCost = tempCost;
    }

    public double getBestCost() {
        return bestCost;
    }

    public void setBestCost(double bestCost) {
        this.bestCost = bestCost;
    }
}
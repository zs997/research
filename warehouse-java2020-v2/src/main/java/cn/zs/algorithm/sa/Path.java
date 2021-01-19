/**
 * projectName: TSP
 * fileName: Path.java
 * packageName: cn.zs
 * date: 2021-01-17 16:58
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.sa;
import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.ColumnR;
import cn.zs.algorithm.component.Coordinate;
import java.util.ArrayList;
import java.util.HashSet;

import static cn.zs.algorithm.ga.Params.*;
/**
 * @version: V1.0
 * @author: zs
 * @className: Path
 * @packageName: cn.zs
 * @data: 2021-01-17 16:58
 **/
public class Path {
    //计算目标函数用
    private ArrayList<ColumnR> columns ;

    private double lengthCost;
    //下标库位编号 值为存储的货物
    private int[] best_path;
    //
    private double temp_lengthCost;   // 临时存放路径及其及其长度
    private int[] temp_path;

    public Path() {
        lengthCost = 0.0;
        best_path = new int[storageCount];
        temp_lengthCost = 0.0;
        temp_path = new int[storageCount];
        // 初始化最佳路径以及路径长度
        for (int i = 0; i < storageCount; i++) {
            //先按编号形成初始解
            best_path[i] = i;
        }
        synchronizColumn(best_path);
        lengthCost =  calculLengthCost();
    }
    /**
     * 通过染色体 同步其他数据
     * */
    private  void synchronizColumn(int [] route)  {
        columns = new ArrayList<ColumnR>();
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
            }
            ColumnR column = new ColumnR();
            column.setLocations(temp);
            this.columns.add(column);
        }
    }
    /**
     * 计算路径期望
     * */
    private double calculLengthCost(){
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
        //lengthCost = res;
        System.out.println(res);
        return res;
    }
    public void generateNeighour() {
        setTemp_path(best_path);
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
            int exchange = temp_path[i];
            temp_path[i] = temp_path[j];
            temp_path[j] = exchange;
            i++;
            j--;
        }
        synchronizColumn(temp_path);
        double v = calculLengthCost();
        setTemp_res(v);
    }
    public int[] getBest_path() {
        return best_path;
    }
    public double getRes() {
        return lengthCost;
    }
    public void setRes(double res) {
        this.lengthCost = res;
    }
    public void setBest_path(int[] best_path) {
        int length = best_path.length;
        for(int i = 0;i < length; i++)
            this.best_path[i] = best_path[i];
    }
    public double getTemp_res() {
        return temp_lengthCost;
    }
    public void setTemp_res(double temp_res) {
        this.temp_lengthCost = temp_res;
    }
    public int[] getTemp_path() {
        return temp_path;
    }
    public void setTemp_path(int[] temp_path) {
        int length = temp_path.length;
        for(int i = 0; i< length ;i++)
            this.temp_path[i] = temp_path[i];
    }
}
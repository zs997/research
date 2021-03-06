/**
 * projectName: research
 * fileName: Bench.java
 * packageName: cn.zs.benchmark
 * date: 2021-03-06 21:27
 * copyright(c) 2019-2021 hust
 */
package cn.zs.benchmark;

import cn.zs.algorithm.component.ColumnL;
import cn.zs.algorithm.component.Individual;
import cn.zs.algorithm.component.Params;
import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import java.util.ArrayList;

import static cn.zs.algorithm.component.Params.itemPickFreq;
import static cn.zs.algorithm.component.Params.storageCount;

/**
 * @version: V1.0
 * @author: zs
 * @className: Bench
 * @packageName: cn.zs.benchmark
 * @data: 2021-03-06 21:27
 **/
public class Bench {
    static OriginDataReader originDataReader = new OriginDataReaderImp();
    String baseDataDir ="D:\\works\\data\\all";
    @Deprecated
    public  void  testIndivual(){
        int [][] assignMatrix = new int[15][4];
        //第四维 是每行的分配方式  为0 正常分配 为1 对称分配
        assignMatrix[0][0] = 24;
        assignMatrix[0][1] = 0;
        assignMatrix[0][2] = 0;
        assignMatrix[0][3] = 0;

        assignMatrix[1][0] = 2;
        assignMatrix[1][1] = 10;
        assignMatrix[1][2] = 12;
        assignMatrix[1][3] = 1;

        assignMatrix[2][0] = 2;
        assignMatrix[2][1] = 8;
        assignMatrix[2][2] = 14;
        assignMatrix[2][3] = 1;

        assignMatrix[3][0] = 2;
        assignMatrix[3][1] = 8;
        assignMatrix[3][2] = 14;
        assignMatrix[3][3] = 1;

        assignMatrix[4][0] = 2;
        assignMatrix[4][1] = 8;
        assignMatrix[4][2] = 14;
        assignMatrix[4][3] = 1;

        assignMatrix[5][0] = 2;
        assignMatrix[5][1] = 8;
        assignMatrix[5][2] = 14;
        assignMatrix[5][3] = 1;

        assignMatrix[6][0] = 2;
        assignMatrix[6][1] = 8;
        assignMatrix[6][2] = 14;
        assignMatrix[6][3] = 1;

        assignMatrix[7][0] = 2;
        assignMatrix[7][1] = 8;
        assignMatrix[7][2] = 14;
        assignMatrix[7][3] = 1;

        assignMatrix[8][0] = 2;
        assignMatrix[8][1] = 8;
        assignMatrix[8][2] = 14;
        assignMatrix[8][3] = 1;

        assignMatrix[9][0] = 2;
        assignMatrix[9][1] = 8;
        assignMatrix[9][2] = 14;
        assignMatrix[9][3] = 1;

        assignMatrix[10][0] = 2;
        assignMatrix[10][1] = 8;
        assignMatrix[10][2] = 14;
        assignMatrix[10][3] = 1;

        assignMatrix[11][0] = 2;
        assignMatrix[11][1] = 8;
        assignMatrix[11][2] = 14;
        assignMatrix[11][3] = 1;

        assignMatrix[12][0] = 2;
        assignMatrix[12][1] = 8;
        assignMatrix[12][2] = 14;
        assignMatrix[12][3] = 1;

        assignMatrix[13][0] = 0;
        assignMatrix[13][1] = 10;
        assignMatrix[13][2] = 14;
        assignMatrix[13][3] = 1;

        assignMatrix[14][0] = 24;
        assignMatrix[14][1] = 0;
        assignMatrix[14][2] = 0;
        assignMatrix[14][3] = 0;
        ArrayList<String> ss = originDataReader.readTxt(baseDataDir+"\\warehouseStructureBenchMark.txt");
        Params.initWarehouseStructure(ss.get(ss.size() - 1));
        PickParam pickParam = getBenchmarkPickData(20, 0.5, 0.3, 0.2);
        Params.setItemPickFreq(pickParam.getPickf());
        Params.calculNonEmptyProb();
        int storageA = pickParam.getStorageA();
        int storageB = pickParam.getStorageB();
        int storageC = pickParam.getStorageC();
        int indexA = 0;
        int indexB = storageA;
        int indexC = storageA + storageB;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < assignMatrix.length; i++) {
            if (assignMatrix[i][3] == 0){
                //每行的A
                for (int j = 0; j < assignMatrix[i][0]; j++) {
                    list.add(indexA);
                    indexA++;
                }
                //每行B
                for (int k = 0; k < assignMatrix[i][1]; k++) {
                    list.add(indexB);
                    indexB++;
                }
                //每行C
                for (int m = 0; m < assignMatrix[i][2]; m++) {
                    list.add(indexC);
                    indexC++;
                }
            }else {
                int numA = assignMatrix[i][0];
                int numB = assignMatrix[i][1];
                int halfA = (int)Math.ceil(numA/2.0);
                int halfB = (int)Math.ceil(numB/2.0);
                for (int j = 0; j < halfA; j++) {
                    list.add(indexA);
                    indexA++;
                }
                for (int j = 0; j < halfB; j++) {
                    list.add(indexB);
                    indexB++;
                }
                for (int j = 0; j < assignMatrix[i][2]; j++) {
                    list.add(indexC);
                    indexC++;
                }
                for (int j = 0; j < numB - halfB; j++) {
                    list.add(indexB);
                    indexB++;
                }
                for (int j = 0; j < numA - halfA; j++) {
                    list.add(indexA);
                    indexA++;
                }
            }
        }


        Individual<ColumnL> individual = new Individual<>(ColumnL.class,1);
        individual.setChromosome(list);
        System.out.println(list);
        individual.calculFitness();
        System.out.println(storageCount);
        for (int i = 0; i < itemPickFreq.length; i++) {
            System.out.println(itemPickFreq[i]);
        }
        System.out.println(individual.getCost());
    }

    @Deprecated
    public  PickParam getBenchmarkPickData(int orderLength,double aOfOrder,double bOfOrder,double cOfOrder) {
        //各类货物所占库位数目
        int storageA = (int)Math.round(storageCount * 0.2);
        int storageB = (int)Math.round(storageCount * 0.3);
        int storageC =  storageCount - storageA - storageB;
        double pickA = orderLength * aOfOrder / storageA;
        double pickB = orderLength * bOfOrder / storageB;
        double pickC = orderLength * cOfOrder / storageC;
        int i = 0;
        double [] pickf = new double[storageCount];
        for (; i < storageA; i++) {
            pickf[i] = pickA;
        }
        for (;i < storageA + storageB;i++){
            pickf[i] = pickB;
        }
        for (;i< pickf.length;i++ ){
            pickf[i] = pickC;
        }
        PickParam pickParam = new PickParam();
        pickParam.setPickf(pickf);
        pickParam.setStorageA(storageA);
        pickParam.setStorageB(storageB);
        pickParam.setStorageC(storageC);
        return pickParam;
    }
}
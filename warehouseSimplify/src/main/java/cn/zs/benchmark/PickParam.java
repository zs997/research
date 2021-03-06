/**
 * projectName: research
 * fileName: PickParam.java
 * packageName: cn.cn.cn.zs.pojo
 * date: 2021-01-16 20:15
 * copyright(c) 2019-2021 hust
 */
package cn.zs.benchmark;

/**
 * @version: V1.0
 * @author: cn.cn.zs
 * @className: PickParam
 * @packageName: cn.cn.cn.zs.pojo
 * @data: 2021-01-16 20:15
 * 使用标准banchmark数据时 用到
 **/
public class PickParam {
    int storageA ;
    int storageB ;
    int storageC;
    double [] pickf;

    public int getStorageA() {
        return storageA;
    }

    public void setStorageA(int storageA) {
        this.storageA = storageA;
    }

    public int getStorageB() {
        return storageB;
    }

    public void setStorageB(int storageB) {
        this.storageB = storageB;
    }

    public int getStorageC() {
        return storageC;
    }

    public void setStorageC(int storageC) {
        this.storageC = storageC;
    }

    public double[] getPickf() {
        return pickf;
    }

    public void setPickf(double[] pickf) {
        this.pickf = pickf;
    }
}
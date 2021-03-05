/**
 * projectName: research
 * fileName: Silhouette.java
 * packageName: cn.zs.pojo
 * date: 2021-03-05 14:37
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

import java.util.ArrayList;

/**
 * @version: V1.0
 * @author: zs
 * @className: Silhouette
 * @packageName: cn.zs.pojo
 * @data: 2021-03-05 14:37
 **/
public class SilhouetteParam {
    double avgSilhouette;
    ArrayList<ArrayList<Double>> itemSilhouette;
    ArrayList<Double> avgGroupSilhouette;

    public double getAvgSilhouette() {
        return avgSilhouette;
    }

    public void setAvgSilhouette(double avgSilhouette) {
        this.avgSilhouette = avgSilhouette;
    }

    public ArrayList<ArrayList<Double>> getItemSilhouette() {
        return itemSilhouette;
    }

    public void setItemSilhouette(ArrayList<ArrayList<Double>> itemSilhouette) {
        this.itemSilhouette = itemSilhouette;
    }

    public ArrayList<Double> getAvgGroupSilhouette() {
        return avgGroupSilhouette;
    }

    public void setAvgGroupSilhouette(ArrayList<Double> avgGroupSilhouette) {
        this.avgGroupSilhouette = avgGroupSilhouette;
    }
}
/**
 * projectName: research
 * fileName: CsvData.java
 * packageName: cn.cn.cn.cn.zs.pojo
 * date: 2020-12-09 21:31
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

/**
 * @version: V1.0
 * @author: cn.cn.cn.zs
 * @className: CsvData
 * @packageName: cn.cn.cn.cn.zs.pojo
 * @data: 2020-12-09 21:31
 **/
public class CsvContent {
    String titile;
    String [][] csvDataMatrix;

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String [][] getCsvDataMatrix() {
        return csvDataMatrix;
    }

    public void setCsvDataMatrix(String [][] csvDataMatrix) {
        this.csvDataMatrix = csvDataMatrix;
    }
}
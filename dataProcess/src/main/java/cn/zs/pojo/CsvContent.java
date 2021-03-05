/**
 * projectName: research
 * fileName: CsvData.java
 * packageName: cn.cn.cn.zs.pojo
 * date: 2020-12-09 21:31
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

/**
 * @version: V1.0
 * @author: cn.cn.zs
 * @className: CsvData
 * @packageName: cn.cn.cn.zs.pojo
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
    public void setCsvDataMatrix(double [][] csvDataMatrix) {
        String[][] strings = new String[csvDataMatrix.length][csvDataMatrix[0].length];
        for (int i = 0; i < csvDataMatrix.length; i++) {
            for (int j = 0; j < csvDataMatrix[i].length; j++) {
                strings[i][j] = String.valueOf(csvDataMatrix[i][j]);
            }
        }
        this.csvDataMatrix = strings;
    }
}
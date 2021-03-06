/**
 * projectName: research
 * fileName: SimilarDataWriter.java
 * packageName: cn.cn.cn.cn.zs.daoImp
 * date: 2020-12-09 21:13
 * copyright(c) 2019-2021 hust
 */
package cn.zs.dao;

import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @version: V1.0
 * @author: cn.cn.cn.zs
 * @className: SimilarDataWriter
 * @packageName: cn.cn.cn.cn.zs.daoImp
 * @data: 2020-12-09 21:13
 **/

public class MyDataWriter   {

    public void write(CommonData data) {
      write(data,false);
    }

    public void write(CommonData data, boolean append) {
        try {
            String path = data.getPath();
            File file=new File(path);
            Object data1 = data.getData();
            if (data1 instanceof CsvContent){
                CsvContent csvContent =  (CsvContent)data1;
                FileOutputStream fos=new FileOutputStream(file,append);
                OutputStreamWriter osw=new OutputStreamWriter(fos, "GBK");
                BufferedWriter bw=new BufferedWriter(osw);
                bw.write(csvContent.getTitile()+"\t\n");
                String[][] csvDataMatrix = csvContent.getCsvDataMatrix();
                for (int i = 0; i < csvDataMatrix.length; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < csvDataMatrix[i].length-1; j++) {
                        sb.append(csvDataMatrix[i][j]+",");
                    }
                    sb.append(csvDataMatrix[i][csvDataMatrix[i].length-1]);
                    bw.write(sb.toString()+"\t\n");
                }
                //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
                bw.close();
                osw.close();
                fos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
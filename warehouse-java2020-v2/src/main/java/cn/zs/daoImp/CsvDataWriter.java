/**
 * projectName: research
 * fileName: SimilarDataWriter.java
 * packageName: cn.zs.daoImp
 * date: 2020-12-09 21:13
 * copyright(c) 2019-2021 hust
 */
package cn.zs.daoImp;

import cn.zs.dao.MyDataWriter;
import cn.zs.pojo.CommonData;
import cn.zs.pojo.CsvContent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @version: V1.0
 * @author: zs
 * @className: SimilarDataWriter
 * @packageName: cn.zs.daoImp
 * @data: 2020-12-09 21:13
 **/
public class CsvDataWriter implements MyDataWriter {
    @Override
    public void write(CommonData data) {
        try {
            String path = data.getPath();
            File file=new File(path);
            Object data1 = data.getData();
           if (data1 instanceof CsvContent){
                CsvContent csvContent =  (CsvContent)data1;
                FileOutputStream fos=new FileOutputStream(file,true);
                OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
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
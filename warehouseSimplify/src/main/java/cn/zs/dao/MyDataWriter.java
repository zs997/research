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
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

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
    public void writeExcel(ArrayList<ArrayList<Double>> data, String destination) throws IOException {
        //创建Excel文件薄
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建工作表sheeet
        Sheet sheet=workbook.createSheet();
        //追加数据
        for (int i=0;i<data.size();i++){
            Row nextrow=sheet.createRow(i);
            ArrayList<Double> list = data.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell2=nextrow.createCell(j);
                cell2.setCellValue(list.get(j));
            }
        }
        //创建一个文件
        File file=new File(destination);
        file.createNewFile();
        FileOutputStream stream= FileUtils.openOutputStream(file);
        workbook.write(stream);
        stream.close();
    }
    public void writeExcel(ArrayList<ArrayList<Double>> data, String[] titile,String destination) throws IOException {
        //创建Excel文件薄
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建工作表sheeet
        Sheet sheet=workbook.createSheet();
        //追加数据
        Row row = sheet.createRow(0);
        for (int i = 0; i < titile.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titile[i]);
        }
        for (int i=0;i<data.size();i++){
            Row nextrow=sheet.createRow(i+1);
            ArrayList<Double> list = data.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell2=nextrow.createCell(j);
                cell2.setCellValue(list.get(j));
            }
        }
        //创建一个文件
        File file=new File(destination);
        file.createNewFile();
        FileOutputStream stream= FileUtils.openOutputStream(file);
        workbook.write(stream);
        stream.close();
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
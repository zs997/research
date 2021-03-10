/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-01 21:41
 * copyright(c) 2019-2021 hust
 */

import cn.zs.dao.OriginDataReader;
import cn.zs.dao.OriginDataReaderImp;
import jnr.ffi.annotations.In;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.python.antlr.ast.Str;
import sun.print.DocumentPropertiesUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-03-01 21:41
 **/

public class Test1 {
    @Test
    public  void ss(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("D:\\works\\data\\all\\SilhouetteTest\\brandDistance2.csv");
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (int i = 1; i < arrayLists.size(); i++) {
            ArrayList<Double> list = new ArrayList<>();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                list.add(Double.valueOf(arrayLists.get(i).get(j).trim()));
            }
            res.add(list);
        }
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                System.out.print(res.get(i).get(j)+",");
            }
            System.out.println();
        }
    }

    @Test
    public void rtrt(){
        OriginDataReader originDataReader = new OriginDataReaderImp();
        ArrayList<ArrayList<String>> arrayLists = originDataReader.readCsv("D:\\works\\data\\all\\SilhouetteTest\\brandDistance2.csv");
        double[][] res = new double[arrayLists.size()-1][];
        for (int i = 1; i < arrayLists.size(); i++) {
            res[i-1] = new double[arrayLists.get(i).size()];
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                res[i-1][j] = Double.valueOf(arrayLists.get(i).get(j).trim());
            }
        }
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                System.out.print(res[i][j]+ " ");
            }
            System.out.println();
        }
    }
    @Test
    public void asdf() throws IOException {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Double> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                double random = Math.random();
                list.add(random);
            }
            res.add(list);
        }
        System.out.println(res);
        String titile []= {"a","b","c","d","e"};
        //"D:/poi_test.xlsx"
        writeExcel(res,titile,"D:/poi_test.xlsx");
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


}
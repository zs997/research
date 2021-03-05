package cn.zs.dao;
import cn.zs.pojo.Item;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import jnr.ffi.annotations.In;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OriginDataReaderImp implements OriginDataReader {
    @Override
    public ArrayList<ArrayList<String>> readCsv(String path) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, "GBK"), CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                data.add(new ArrayList<String>(Arrays.asList(strs)));
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public ArrayList<String> readTxt(String filePath) {
        ArrayList<String> res = new ArrayList<>();
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while((lineTxt = bufferedReader.readLine()) != null){
                   res.add(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Item> readItemList(String path) {
        ArrayList<ArrayList<String>> arrayLists = readCsv(path);
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i < arrayLists.size(); i++) {
            Item item = new Item();
            ArrayList<String> strings = arrayLists.get(i);
            item.setId(Long.valueOf(strings.get(0)));
            item.setBrandNo(strings.get(1));
            item.setBrandName(strings.get(2));
            item.setTimes(Long.valueOf(strings.get(3)));
            item.setPickfreq(Double.valueOf(strings.get(4)));
            items.add(item);
        }
        return items;
    }
}

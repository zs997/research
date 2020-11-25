package cn.zs.serviceImp;

import cn.zs.service.OriginDataReader;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
@Service
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
}

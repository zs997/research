package cn.zs.dao;

import java.util.ArrayList;

public interface OriginDataReader {
      ArrayList<ArrayList<String>> readCsv(String path) ;
      ArrayList<String> readTxt(String path);
}

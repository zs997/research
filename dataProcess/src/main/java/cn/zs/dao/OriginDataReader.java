package cn.zs.dao;

import cn.zs.pojo.Item;

import java.util.ArrayList;
import java.util.List;

public interface OriginDataReader {
      ArrayList<ArrayList<String>> readCsv(String path) ;
      ArrayList<String> readTxt(String path);
      List<Item> readItemList(String path);
      ArrayList<ArrayList<Integer>> readGroupInfo(String path);
      double[][] readDistanceMatrix(String path);
}

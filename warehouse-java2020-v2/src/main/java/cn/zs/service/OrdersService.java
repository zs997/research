package cn.zs.service;
import cn.zs.pojo.Item;
import java.util.ArrayList;
import java.util.List;
public interface OrdersService {
    int countRows();
    int countOrders();
    int countBrands();
    List<String> selectByorderNo(Integer orderNo);
    void save2database(ArrayList<ArrayList<String>> data);
    void generateItemList(String path);
    List<Item> getItemList();
    void generateSupportCount(String path);
    void generateSimilarMatrix(String path);
    void generateDistanceMatrix(String path);
    void groupItem(String source,String destination,int n);
}

package cn.zs.mapper;
import cn.zs.pojo.Item;
import cn.zs.pojo.Orders;
import cn.zs.pojo.OrdersExample;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
public interface OrdersMapper {
    int countByExample(OrdersExample example);
    int deleteByExample(OrdersExample example);
    int deleteByPrimaryKey(Integer oid);
    int insert(Orders record);
    int insertSelective(Orders record);
    List<Orders> selectByExample(OrdersExample example);
    //查询不同订单编号
    List<Map<String,Integer>> selectDiffOrders();
    //查询订单总数
    Map<String,Long> selectOrdersNum();
    //查询不同商品
    List<Map<String,String>> selectDiffBrands();
    //查询商品信息
    List<Item> selectItemsInfo();
    Orders selectByPrimaryKey(Integer oid);
    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);
    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);
    int updateByPrimaryKeySelective(Orders record);
    int updateByPrimaryKey(Orders record);
}
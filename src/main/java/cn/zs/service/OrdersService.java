package cn.zs.service;

import cn.zs.pojo.Orders;

import java.util.List;
public interface OrdersService {
    void insertList(List<Orders> data);
    void insert(Orders orders);
    int countRows();
    int countOrders();
    int countBrands();
}

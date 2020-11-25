package cn.zs.serviceImp;

import cn.zs.mapper.OrdersMapper;
import cn.zs.pojo.Orders;
import cn.zs.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImp implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Override
    public void insertList(List<Orders> data) {
        for (Orders datum : data) {
            ordersMapper.insert(datum);
        }
    }

    @Override
    public void insert(Orders orders) {
        ordersMapper.insert(orders);
    }

    @Override
    public int countRows() {
        return 0;
    }

    @Override
    public int countOrders() {
        return 0;
    }

    @Override
    public int countBrands() {
        return 0;
    }
}

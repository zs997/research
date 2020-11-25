package cn.zs.controller;

import cn.zs.pojo.Orders;
import cn.zs.service.OrdersService;
import cn.zs.service.OriginDataReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;

public class DataController {
    static OriginDataReader originDataReader;
    static OrdersService ordersService;
    public static void main(String args[]){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
         originDataReader = ac.getBean(OriginDataReader.class);
         ordersService = ac.getBean(OrdersService.class);
        ArrayList<String> ss = new ArrayList<>();

        DataController dataController = new DataController();
      // dataController.init();
        dataController.test();
    }
    public void test(){
        ArrayList<ArrayList<String>> data = originDataReader.readCsv("D:\\works\\data\\data2.csv");
        for (int i = 0; i < data.size(); i+=500) {
            System.out.println(data.get(i).get(0)+","+data.get(i).get(1)+","+data.get(i).get(2));

        }
    }
    public void init(){
        ArrayList<ArrayList<String>> data = originDataReader.readCsv("D:\\works\\data\\data.csv");
        int baditems = 0;
        // HashSet<String> set = new HashSet<>();
        //第0行数据是 数据格式 从第一行正式开始的数据
        int lastIndex = data.size()-1;
        Orders o = new Orders();
        o.setOid(null);
        for (int i = 1; i < data.size(); i++) {
            try {
                ArrayList<String> item = data.get(i);
                Integer orderNo = Integer.valueOf(item.get(0));
                String brandno = item.get(1);
                String brandname = item.get(2);
                Integer num = Integer.valueOf(item.get(3));
                String createtime = item.get(4);
                o.setOrderno(orderNo);
                o.setBrandno(brandno);
                o.setBrandname(brandname);
                o.setNum(num);
                o.setCreatetime(new Date(createtime));
            }catch (Exception e){
                System.out.println(e);
                baditems++;
                continue;
            }
            ordersService.insert(o);
        }
        System.out.println("坏数据个数"+baditems);
    }
}

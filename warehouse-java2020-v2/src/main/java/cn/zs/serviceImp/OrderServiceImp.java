package cn.zs.serviceImp;
import cn.zs.dao.MyDataWriter;
import cn.zs.daoImp.CsvDataWriter;
import cn.zs.mapper.OrdersMapper;
import cn.zs.pojo.*;
import cn.zs.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import static cn.zs.algorithm.ga.Params.*;
@Service
public class OrderServiceImp implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Override
    public int countRows() {
        return 0;
    }
    @Override
    public int countOrders() {
         //返回不同订单数目
        return 0;
    }
    @Override
    public int countBrands() {
        return 0;
    }
    @Override
    public List<String> selectByorderNo(Integer orderNo) {
        List<String> res = new ArrayList<>();
        //查询某订单编号的 事务
        OrdersExample ordersExample = new OrdersExample();
        OrdersExample.Criteria criteria = ordersExample.createCriteria();
        criteria.andOrdernoEqualTo(orderNo);
        List<Orders> orders = ordersMapper.selectByExample(ordersExample);
        for (Orders order : orders) {
            res.add(order.getBrandno());
        }
        return res;
    }
    @Override
    /*
    step 1 存储数据到数据库
    * */
    public void save2database(ArrayList<ArrayList<String>> data) {
        int baditems = 0;
        // HashSet<String> set = new HashSet<>();
        //第0行数据是 数据格式 从第一行正式开始的数据
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
            ordersMapper.insert(o);
        }
        System.out.println("坏数据个数"+baditems);
    }
    /**
     * step2 生成货物信息表  保存到csv  按照拣货概率 由大到小 排序
     *
     * */
    @Override
    public void generateItemList(String path){
        //查询订单总数
        Map<String, Long> stringLongMap = ordersMapper.selectOrdersNum();
        Long ordersNum = stringLongMap.get("ordersNum");
        double v = ordersNum * 1.0;
        List<Item> items = ordersMapper.selectItemsInfo();
        CommonData commonData = new CommonData();
        commonData.setPath(path);
        CsvData csvData = new CsvData();
        commonData.setData(csvData);
        csvData.setTitile("id,brandNo,brandName,times,pickFreq");
        String[][] csvDataMatrix = new String[items.size()][5];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            csvDataMatrix[i][0] = String.valueOf(i);
            csvDataMatrix[i][1] = item.getBrandNo();
            csvDataMatrix[i][2] = item.getBrandName();
            csvDataMatrix[i][3] = String.valueOf(item.getTimes());
            csvDataMatrix[i][4] = String.valueOf(item.getTimes()/v);
        }
        csvData.setCsvDataMatrix(csvDataMatrix);
        MyDataWriter writer = new CsvDataWriter();
        writer.write(commonData);
    }
    @Override
    public  List<Item> getItemList() {
        //查询订单总数
        Map<String, Long> stringLongMap = ordersMapper.selectOrdersNum();
        Long ordersNum = stringLongMap.get("ordersNum");
        double v = ordersNum * 1.0;
        List<Item> items = ordersMapper.selectItemsInfo();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.setId((long)i);
            item.setPickfreq(item.getTimes()/v);
        }
      return items;
    }
    @Override
    /*
    @description: 生成支持数矩阵  按拣货概率由大到小 排序
    矩阵生成比较费时间  应该先放到csv 读取就使用
    * */
    public void generateSupportCount(String path) {
        //查询不同订单编号
        List<Map<String, Integer>> diffOrders = ordersMapper.selectDiffOrders();
//       diffOrders.get(1).get("orderNo");
        //订单编号集合 放入集合 去重
        Set<Integer> orderSet = new HashSet<>();
        for (Map<String, Integer> diffOrder : diffOrders) {
            Integer orderNo = diffOrder.get("orderNo");
            orderSet.add(orderNo);
        }
        Map<String,Integer> commonMap = new HashMap<>();
        for (Integer integer : orderSet) {
            //每个订单  所有商品编号
            List<String> strings = selectByorderNo(integer);
            for (int i = 0; i < strings.size(); i++) {
                //单独出现
                String key = strings.get(i);
                if(commonMap.containsKey(key)){
                    commonMap.put(key,commonMap.get(key)+1);
                }else {
                    commonMap.put(key,1);
                }
                for (int j = i+1;j<strings.size();j++){
                    if(strings.get(i).equals(strings.get(j))){
                        System.out.println(strings.get(i));
                        continue;
                    }
                    String key1 = strings.get(i)+","+strings.get(j);
                    String key2 = strings.get(j)+","+strings.get(i);
                    if(commonMap.containsKey(key1)){
                        commonMap.put(key1,commonMap.get(key1)+1);
                        commonMap.put(key2,commonMap.get(key2)+1);
                    }else {
                        commonMap.put(key1,1);
                        commonMap.put(key2,1);
                    }
                }
            }
        }
        //所有的品牌
       // List<Map<String, Long>> brands = ordersMapper.selectItemTimes();
        List<Item> items = ordersMapper.selectItemsInfo();
        String[][] res = new String[items.size()][items.size()];
        StringBuilder title = new StringBuilder();
        for (int i = 0; i < items.size()-1; i++) {
            String brandNo = items.get(i).getBrandNo();
            title.append(brandNo+",");
        }
        title.append(items.get(items.size() - 1).getBrandNo());
        for (int i = 0; i < items.size(); i++) {
            String brandNo1 = items.get(i).getBrandNo();
            if (commonMap.containsKey(brandNo1)){
                res[i][i] = String.valueOf(commonMap.get(brandNo1));
            }
            for (int j = i+1; j < items.size(); j++) {
                String brandNo2 = items.get(j).getBrandNo();
                String key = brandNo1+","+brandNo2;
                if (commonMap.containsKey(key)){
                    res[i][j] = String.valueOf(commonMap.get(key));
                    res[j][i] = String.valueOf(commonMap.get(key));
                }
            }
        }
        CommonData mydata = new CommonData();
//      mydata.setPath("d:\\works\\data\\brandSupportCount.csv");
        mydata.setPath(path);
        CsvData csvData = new CsvData();
        csvData.setCsvDataMatrix(res);
        csvData.setTitile(title.toString());
        mydata.setData(csvData);
        MyDataWriter writer = new CsvDataWriter();
        writer.write(mydata);
//        for (int i = 0; i < res.length; i++) {
//            for (int j = 0; j < res[i].length; j++) {
//                res[i][j] = orderSet.size() - res[i][j];
//                if(i == j){
//                    res[i][j] = 0;
//                }
//            }
//        }
//        mydata.setPath("d:\\works\\data\\brandDistance.csv");
//        writer.write(mydata);
    }
    @Override
    public void generateSimilarMatrix(String path) {
    }
    @Override
    public void generateDistanceMatrix(String path) {
    }
    /**
     * @description: 聚类算法 source为原始数据，相似度（聚类矩阵） 输出到文件
     * 前n号货物聚类  现在先瞎搞 一个
     * */
    @Override
    public void groupItem(String source, String destination,int n) {
        ArrayList<ArrayList<Integer>> groupInfo = new ArrayList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        //0~399
        for (int i = 0; i < n; i++) {
            arrayList.add(i);
        }
        Collections.shuffle(arrayList);
        Random random = new Random();
        int base = random.nextInt(20) + 30;
        int i = 0;
        while (i < n && base < n){
            ArrayList<Integer> temp = new ArrayList<>();
            while (i < base){
                temp.add(arrayList.get(i));
                i++;
            }
            groupInfo.add(temp);
            base +=  random.nextInt(20) + 30;
        }
        if(i < n){
            ArrayList<Integer> arrayList1 = new ArrayList<>();
            while (i < n){
                arrayList1.add(arrayList.get(i));
                i++;
            }
            groupInfo.add(arrayList1);
        }
        String res [][] = new String[groupInfo.size()][];
        for (int p = 0; p < groupInfo.size(); p++) {
            res[p] = new String[groupInfo.get(p).size()];
            for (int q = 0; q < groupInfo.get(p).size(); q++) {
                res[p][q] = String.valueOf(groupInfo.get(p).get(q));
            }
        }
        CsvData csvData = new CsvData();
        csvData.setTitile("nothing but a title");
        csvData.setCsvDataMatrix(res);
        CommonData commonData = new CommonData();
        commonData.setPath(destination);
        commonData.setData(csvData);
        CsvDataWriter csvDataWriter = new CsvDataWriter();
        csvDataWriter.write(commonData);
    }

    @Override
    public PickParam generateBenchmarkPickData(int orderLength,double aOfOrder,double bOfOrder,double cOfOrder) {
        //各类货物所占库位数目
       int storageA = (int)Math.round(storageCount * 0.2);
       int storageB = (int)Math.round(storageCount * 0.3);
       int storageC =  storageCount - storageA - storageB;
       double pickA = orderLength * aOfOrder / storageA;
       double pickB = orderLength * bOfOrder / storageB;
       double pickC = orderLength * cOfOrder / storageC;
       int i = 0;
       itemPickFreq = new double[storageCount];
        for (; i < storageA; i++) {
            itemPickFreq[i] = pickA;
        }
        for (;i < storageA + storageB;i++){
            itemPickFreq[i] = pickB;
        }
        for (;i< itemPickFreq.length;i++ ){
            itemPickFreq[i] = pickC;
        }
        PickParam pickParam = new PickParam();
        pickParam.setPickf(itemPickFreq);
        pickParam.setStorageA(storageA);
        pickParam.setStorageB(storageB);
        pickParam.setStorageC(storageC);
        return pickParam;
    }
}

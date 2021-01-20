package cn.zs.service;
import cn.zs.dao.MyDataWriter;
import cn.zs.dao.OriginDataReader;
import cn.zs.daoImp.CsvDataWriter;
import cn.zs.daoImp.OriginDataReaderImp;
import cn.zs.mapper.OrdersMapper;
import cn.zs.pojo.*;
import cn.zs.tools.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import static cn.zs.algorithm.component.Params.*;
@Service
public class DataService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OriginDataReader originDataReader;
    /**
    step 1 保存原始订单数据csv到数据库
    @param:data 订单数据
    * */
    private void csv2database(ArrayList<ArrayList<String>> data) {
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
     * step1
     * @description: 初始化数据 将csv订单数据存到数据库
     * @param：path 具体到文件名
     * */
    public  void csv2databse(String path){
        //"D:\\works\\data\\data.csv"
        ArrayList<ArrayList<String>> data = originDataReader.readCsv(path);
        csv2database(data);
    }

    /**
     *  根据数据库订单表  生成货物信息表  保存到csv(实际使用时候 可以不保存 从数据库查询)
     *  id,   brandNo,  brandName,times,    pickFreq
     * 名次，商品编号，商品名称，拣货频次，拣货概率
     *  按照拣货概率由大到小 排序
     * @parm：path 保存结果路径  具体到文件名
     * */
    @Deprecated
    public void generateItemList(String path){
        //查询商品明细
        List<Item> items = getItemList();
        CommonData commonData = new CommonData();
        commonData.setPath(path);
        CsvContent csvContent = new CsvContent();
        commonData.setData(csvContent);
        csvContent.setTitile("id,brandNo,brandName,times,pickFreq");
        String[][] csvDataMatrix = new String[items.size()][5];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            csvDataMatrix[i][0] = String.valueOf(item.getId());
            csvDataMatrix[i][1] = item.getBrandNo();
            csvDataMatrix[i][2] = item.getBrandName();
            csvDataMatrix[i][3] = String.valueOf(item.getTimes());
            csvDataMatrix[i][4] = String.valueOf(item.getPickfreq());
        }
        csvContent.setCsvDataMatrix(csvDataMatrix);
        MyDataWriter writer = new CsvDataWriter();
        writer.write(commonData);
    }
    /**
     * 从数据库订单表查询商品明细，速度很快，可以不用csv，随用随取
     * @return: 商品明细
     * */
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

    /**
        生成支持数矩阵  按拣货概率由大到小 排序
        矩阵生成比较费时间  应该先放到csv 需要使用时候，读取csv
     @param：path  具体到目录 不具体到文件名 生成多个文件
    * */
    public void generateSupportCount(String path) {
        //查询不同订单编号
        List<Map<String, Integer>> diffOrders = ordersMapper.selectDiffOrders();

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
                    }else {
                        commonMap.put(key1,1);
                    }
                    if (commonMap.containsKey(key2)){
                        commonMap.put(key2,commonMap.get(key2)+1);
                    }else {
                        commonMap.put(key2,1);
                    }
                }
            }
        }
        //所有的品牌
        List<Item> items = ordersMapper.selectItemsInfo();
        String[][] res = new String[items.size()][items.size()];
        int [][] temp = new int[items.size()][items.size()];

        StringBuilder title = new StringBuilder();
        for (int i = 0; i < items.size()-1; i++) {
            String brandNo = items.get(i).getBrandNo();
            title.append(brandNo+",");
        }
        title.append(items.get(items.size() - 1).getBrandNo());
        for (int i = 0; i < items.size(); i++) {
            String brandNo1 = items.get(i).getBrandNo();
            if (commonMap.containsKey(brandNo1)){
                temp[i][i] = commonMap.get(brandNo1);
            }
            for (int j = i+1; j < items.size(); j++) {
                String brandNo2 = items.get(j).getBrandNo();
                String key = brandNo1+","+brandNo2;
                if (commonMap.containsKey(key)){
                    temp[i][j] =commonMap.get(key);
                    temp[j][i] =commonMap.get(key);
                }
            }
        }
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = String.valueOf(temp[i][j]);
            }
        }
        CommonData mydata = new CommonData();
//      mydata.setPath("d:\\works\\data\\brandSupportCount.csv");
        mydata.setPath(path+"\\brandSupportCount.csv");
        CsvContent csvContent = new CsvContent();
        csvContent.setCsvDataMatrix(res);
        csvContent.setTitile(title.toString());
        mydata.setData(csvContent);
        MyDataWriter writer = new CsvDataWriter();
        writer.write(mydata);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = String.valueOf(orderSet.size() - temp[i][j]);
                if(i == j){
                    res[i][j] = "0";
                }
            }
        }
        mydata.setPath(path+"\\brandDistance.csv");
        writer.write(mydata);
    }

    /**
     * 其他模块用到
     * 根据订单编号 查询货物编号
     * */
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
    /**
     * 将前n号货物随机分组
     * 将分组结果保存到csv，destination路径
     * */
    @Deprecated
    public void generateItemGroupRandom(int n,String destination) {
        ArrayList<ArrayList<Integer>> groupInfo = getItemGroupRandom(n);
        String[][] res = DataConverter.list2Matrix(groupInfo);
        CsvContent csvContent = new CsvContent();
        csvContent.setTitile("nothing but a title");
        csvContent.setCsvDataMatrix(res);
        CommonData commonData = new CommonData();
        commonData.setPath(destination);
        commonData.setData(csvContent);
        CsvDataWriter csvDataWriter = new CsvDataWriter();
        csvDataWriter.write(commonData);
    }

    /**
     * 将前n号货物随机分组
     * 返回分组信息
     * */
    @Deprecated
    public ArrayList<ArrayList<Integer>> getItemGroupRandom(int n){
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
        return groupInfo;
    }
    @Deprecated
    public PickParam getBenchmarkPickData(int orderLength,double aOfOrder,double bOfOrder,double cOfOrder) {
        //各类货物所占库位数目
       int storageA = (int)Math.round(storageCount * 0.2);
       int storageB = (int)Math.round(storageCount * 0.3);
       int storageC =  storageCount - storageA - storageB;
       double pickA = orderLength * aOfOrder / storageA;
       double pickB = orderLength * bOfOrder / storageB;
       double pickC = orderLength * cOfOrder / storageC;
       int i = 0;
       double [] pickf = new double[storageCount];
        for (; i < storageA; i++) {
            pickf[i] = pickA;
        }
        for (;i < storageA + storageB;i++){
            pickf[i] = pickB;
        }
        for (;i< pickf.length;i++ ){
            pickf[i] = pickC;
        }
        PickParam pickParam = new PickParam();
        pickParam.setPickf(pickf);
        pickParam.setStorageA(storageA);
        pickParam.setStorageB(storageB);
        pickParam.setStorageC(storageC);
        return pickParam;
    }



}

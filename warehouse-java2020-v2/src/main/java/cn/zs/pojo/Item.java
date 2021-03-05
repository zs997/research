/**
 * projectName: research
 * fileName: Item.java
 * packageName: cn.cn.cn.zs.pojo
 * date: 2021-01-06 14:32
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

/**
 * @version: V1.0
 * @author: cn.cn.zs
 * @className: Item
 * @packageName: cn.cn.cn.zs.pojo
 * @data: 2021-01-06 14:32
 * @description: 商品信息表
 **/
public class Item {
    Long id;
    String brandNo;
    String brandName;
    //频次
    Long times;
    //拣货频率（概率）
    Double pickfreq;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBrandNo() {
        return brandNo;
    }
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public Long getTimes() {
        return times;
    }
    public void setTimes(Long times) {
        this.times = times;
    }
    public Double getPickfreq() {
        return pickfreq;
    }
    public void setPickfreq(Double pickfreq) {
        this.pickfreq = pickfreq;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", brandNo='" + brandNo + '\'' +
                ", brandName='" + brandName + '\'' +
                ", times=" + times +
                ", pickfreq=" + pickfreq +
                '}';
    }
}
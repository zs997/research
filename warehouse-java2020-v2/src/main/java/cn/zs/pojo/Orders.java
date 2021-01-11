package cn.zs.pojo;

import java.util.Date;

public class Orders {
    private Integer oid;

    private Integer orderno;

    private String brandno;

    private String brandname;

    private Integer num;

    private Date createtime;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getBrandno() {
        return brandno;
    }

    public void setBrandno(String brandno) {
        this.brandno = brandno == null ? null : brandno.trim();
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid=" + oid +
                ", orderno=" + orderno +
                ", brandno='" + brandno + '\'' +
                ", brandname='" + brandname + '\'' +
                ", num=" + num +
                ", createtime=" + createtime +
                '}';
    }
}
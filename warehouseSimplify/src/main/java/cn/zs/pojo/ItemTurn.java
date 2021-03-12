/**
 * projectName: research
 * fileName: ItemTurn.java
 * packageName: cn.zs.pojo
 * date: 2021-03-11 5:15
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

/**
 * @version: V1.0
 * @author: zs
 * @className: ItemTurn
 * @packageName: cn.zs.pojo
 * @data: 2021-03-11 5:15
 **/
public class ItemTurn {
    int itemNo;
    double itemTurn;

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public double getItemTurn() {
        return itemTurn;
    }

    public void setItemTurn(double itemTurn) {
        this.itemTurn = itemTurn;
    }

    @Override
    public String toString() {
        return "ItemTurn{" +
                "itemNo=" + itemNo +
                ", itemTurn=" + itemTurn +
                '}';
    }
}
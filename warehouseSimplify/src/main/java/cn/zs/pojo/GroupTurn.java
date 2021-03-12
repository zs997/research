/**
 * projectName: research
 * fileName: GroupTurn.java
 * packageName: cn.zs.pojo
 * date: 2021-03-11 5:15
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: zs
 * @className: GroupTurn
 * @packageName: cn.zs.pojo
 * @data: 2021-03-11 5:15
 **/
public class GroupTurn {
    int groupNo;
    double groupTurn;
    List<ItemTurn> list;
    public void addItemTurn(ItemTurn itemTurn) {
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(itemTurn);
    }
    public void calculGroupTurn(){
        double sum = 0.0;
        for (int i = 0; i < list.size(); i++) {
            ItemTurn itemTurn = list.get(i);
            sum += itemTurn.getItemTurn();
        }
        groupTurn = sum/list.size();
    }
    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public double getGroupTurn() {
        return groupTurn;
    }

    public void setGroupTurn(double groupTurn) {
        this.groupTurn = groupTurn;
    }

    public List<ItemTurn> getList() {
        return list;
    }

    public void setList(List<ItemTurn> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GroupTurn{" +
                "groupNo=" + groupNo +
                ", groupTurn=" + groupTurn +
                ", list=" + list +
                '}';
    }
}
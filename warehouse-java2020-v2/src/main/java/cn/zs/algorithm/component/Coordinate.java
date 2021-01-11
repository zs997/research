/**
 * projectName: research
 * fileName: Coordinate.java
 * packageName: cn.zs.algorithm.component
 * date: 2021-01-04 17:02
 * copyright(c) 2019-2021 hust
 */
package cn.zs.algorithm.component;

import static cn.zs.algorithm.Params.*;

/**
 * @version: V1.0
 * @author: zs
 * @className: Coordinate
 * @packageName: cn.zs.algorithm.component
 * @data: 2021-01-04 17:02
 **/
public class Coordinate {
   //库位 排 列  第一维度
   private int colNo;
   //库位 每排 行 第二维度
   private int rowNo;
   //库位编号
   private int no;
   //通过库位编号 求解坐标
   public void calibrationByNo(){
      colNo = no/N;
      rowNo = no%N;
   }
   //通过坐标 求解库位编号
   public void calibrationByCoordinate(){
       no = colNo * N + rowNo;
   }
   public double calculDistance(Coordinate c2){
      double res = 0;
      if(this.colNo == c2.getColNo()){
         int abs = Math.abs(c2.getRowNo() -this.rowNo);
         res = abs * f;
      }else {
         //j1f+j2f-f+2wc+|i2-i1|wa
         double v = this.rowNo * f + c2.getRowNo() * f - f + 2 * wc + Math.abs(this.colNo - c2.getColNo()) * wa;
         //(n-j_(1 ) )f+(n-j_2 )f+f+2w_c+|i_2-i_1 | w_(a   )
         double u = (N - this.rowNo) * f + (N - c2.getRowNo()) * f + f + 2 * wc + Math.abs(this.colNo - c2.getColNo()) * wa;
         res = Math.min(u,v);
      }
      return  res;
   }
   public int getColNo() {
      return colNo;
   }

   public void setColNo(int colNo) {
      this.colNo = colNo;
   }

   public int getRowNo() {
      return rowNo;
   }

   public void setRowNo(int rowNo) {
      this.rowNo = rowNo;
   }

   public int getNo() {
      return no;
   }

   public void setNo(int no) {
      this.no = no;
   }
}
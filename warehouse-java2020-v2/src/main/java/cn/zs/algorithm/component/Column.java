package cn.zs.algorithm.component;

import java.util.ArrayList;
import java.util.HashSet;

public class Column {
    public double getEnterProb(){
        return -1;
    }
    public double getEvenProb(){
        return -1;
    }
    public void setLocations(ArrayList<Integer> temp) {
     }
     public ArrayList<Integer> getLocations() {
          return null;
     }
     public double getCost() {
          return -1;
     }
    public void calculCost(int no,HashSet<Integer> usedSet,double lastEvenProb, double lastEnterProb,double lastFirstProb){

    }
//     void calculCost(int no, int usedA, int usedB, int usedC, int numA, int numB, int numC);
////     void calculCost(int numA, int numB, int numC);
////     void calculCost(int no, int usedA, int usedB, int usedC);
////     void calculCost(int no, int usedA, int usedB, int usedC, int numA, int numB, int numC, int assignmentMode, double lastFirstProb, double lastEnterProb);
////     void calculCost(int no, int usedA, int usedB, int usedC, double lastEvenProb, double lastEnterProb);
////     void calculCost(int no, int usedA, int usedB, int usedC, int numA, int numB, int numC, double lastEvenProb, double lastEnterProb);
////     double getCost();
////     double getEnterProb();
////     double getEvenProb();
////     double getFirstProb();
}

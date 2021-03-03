package tspbenchmark;


import jnr.ffi.annotations.In;

import java.util.ArrayList;
import java.util.HashSet;

public class TspBackTrack {
    static DistanceInstance distanceInstance = DistanceInstance.getInstance6();
    static double bestCost= 9999999;
    public static void main(String[] args) {
        track(0,new ArrayList<Integer>(),0);

    }
    public static void track(int index, ArrayList<Integer> list,double currCost){
        if (index == distanceInstance.getCityNum()){
            double delta = distanceInstance.getDistanceMatrix()[list.get(list.size() - 1)][list.get(0)];
            if(delta+currCost < bestCost){
                bestCost = delta+currCost;
                System.out.println(list);
                System.out.println(bestCost);
            }

        }else {
            if (currCost < bestCost ){

                for (int i = 0; i <distanceInstance.getCityNum();i++){
                    if (!list.contains(i)){
                        double delta = 0;
                        if (list.size() >= 1){
                            Integer lastCity = list.get(list.size() - 1);
                            delta = distanceInstance.getDistanceMatrix()[lastCity][i];
                        }
                        list.add(i);
                        track(index+1,list,currCost+delta);
                        list.remove(list.size()-1);
                    }
                }
            }
        }
    }

}

/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-01-10 14:41
 * copyright(c) 2019-2021 hust
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @version: V1.0
 * @author: zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-01-10 14:41
 **/
public class Test1 {
    @Test
    public void ss(){
        System.out.println("saddsa");
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(10);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(20);
        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(3);
        list3.add(30);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        System.out.println(lists);
        Collections.shuffle(lists);
        System.out.println(lists);

        System.out.println(Math.random());

    }
}
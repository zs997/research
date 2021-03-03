/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-01 21:41
 * copyright(c) 2019-2021 hust
 */

import org.junit.Test;

/**
 * @version: V1.0
 * @author: zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-03-01 21:41
 **/

public class Test1 {
    @Test
    public  void ss(){
        StringBuilder sb = new StringBuilder();
        sb.append("adsaadfas,");
        String substring = sb.substring(0, sb.length() - 1);
        System.out.println(substring);
    }
}
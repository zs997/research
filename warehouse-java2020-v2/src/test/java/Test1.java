/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-01-17 15:27
 * copyright(c) 2019-2021 hust
 */

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @version: V1.0
 * @author: zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-01-17 15:27
 **/
public class Test1 {
    @Test
    public void testSolution(){
        int [] temp = {117, 330, 216, 187, 341, 119, 1, 124, 33, 170, 129, 217, 175, 343, 77,
                112, 82, 196, 293, 258, 174, 355, 11, 106, 173, 281, 271, 205, 23, 245, 97, 0,
                229, 243, 314, 213, 20, 26, 350, 251, 141, 189, 253, 98, 318, 224, 186, 56, 4,
                114, 90, 10, 285, 50, 30, 180, 181, 67, 51, 291, 35, 284, 111, 215, 131, 133,
                123, 339, 194, 109, 252, 179, 351, 31, 287, 62, 74, 43, 232, 81, 19, 12, 316,
                132, 320, 238, 40, 325, 348, 25, 255, 145, 297, 301, 121, 298, 269, 130, 151,
                89, 286, 156, 306, 265, 24, 188, 71, 122, 236, 190, 84, 168, 241, 266, 313, 18,
                214, 310, 250, 107, 322, 108, 100, 45, 152, 22, 349, 203, 207, 249, 110, 8, 183,
                345, 72, 115, 184, 198, 354, 263, 332, 352, 292, 37, 47, 218, 149, 85, 16, 278,
                182, 52, 64, 319, 294, 126, 125, 39, 338, 321, 335, 83, 289, 144, 155, 342, 210,
                104, 128, 73, 222, 137, 79, 282, 158, 277, 103, 283, 6, 233, 200, 13, 176, 160,
                21, 331, 113, 120, 27, 223, 307, 92, 227, 275, 69, 221, 60, 340, 32, 270, 274,
                165, 159, 311, 248, 5, 127, 279, 95, 136, 57, 138, 317, 161, 267, 353, 268, 202,
                356, 101, 88, 231, 326, 38, 172, 15, 254, 276, 195, 36, 63, 70, 139, 220, 309,
                49, 337, 240, 312, 166, 178, 329, 290, 358, 346, 150, 86, 96, 261, 17, 296, 193,
                226, 357, 347, 44, 28, 264, 247, 259, 204, 235, 302, 295, 42, 135, 208, 118, 256,
                185, 93, 199, 143, 225, 359, 157, 2, 87, 300, 142, 146, 246, 191, 171, 303, 344, 34,
                209, 201, 59, 237, 53, 14, 167, 80, 242, 48, 7, 41, 75, 308, 66, 234, 288, 3, 29,
                327, 102, 244, 134, 304, 162, 99, 177, 94, 228, 76, 280, 219, 197, 334, 46, 140,
                212, 164, 65, 9, 328, 239, 153, 147, 78, 323, 105, 336, 273, 262, 260, 206, 54,
                58, 257, 68, 55, 211, 169, 61, 91, 333, 272, 148, 116, 230, 305, 163, 299, 154, 315, 324, 192};
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            list.add(temp[i]);
        }
        Collections.sort(list);
        System.out.println(list);
    }
    @Test
    public void testR() throws Exception {
            //建立连接
            RConnection rc=new RConnection();
    //构建数据
            int[] datas={314,451,56,24,631};
    //声明变量，相当于在R命令行中输入data<-datas命令
            rc.assign("data",datas);
    //执行R语句，相当于在R命令行中输max(data)命令
            REXP rexp=rc.eval("max(data)");
    //REXP. asXxx()返回相应类型的数据，如果结果类型不符会出错
            System.out.println(rexp.asInteger());
            rc.close();
    }

}
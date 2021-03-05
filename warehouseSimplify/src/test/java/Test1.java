/**
 * projectName: research
 * fileName: Test1.java
 * packageName: PACKAGE_NAME
 * date: 2021-03-02 18:16
 * copyright(c) 2019-2021 hust
 */

import cn.zs.algorithm.component.Column;
import cn.zs.algorithm.component.Coordinate;
import cn.zs.algorithm.component.Params;
import org.junit.Test;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static cn.zs.algorithm.component.Params.M;
import static cn.zs.algorithm.component.Params.N;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: Test1
 * @packageName: PACKAGE_NAME
 * @data: 2021-03-02 18:16
 **/
public class Test1 {
    @Test
    public void ss(){
       int [] data = {367, 137, 382, 148, 110, 11, 393, 396, 63, 208, 227, 55, 24, 25, 215, 311, 183, 214, 69, 103, 107, 275, 79, 190, 142, 15, 338, 209, 284,
                356, 175, 295, 321, 95, 132, 104, 298, 317, 43, 242, 350, 173, 221, 264, 395, 212, 255, 217, 22, 386, 392, 124, 117, 93, 155, 126,
                160, 237, 389, 341, 270, 64, 207, 147, 198, 91, 140, 196, 2, 231, 111, 75, 218, 197, 328, 267, 151, 390, 343, 247, 108, 78, 256, 59,
                96, 54, 141, 187, 224, 88, 177, 194, 56, 27, 21, 391, 119, 304, 286, 188, 37, 335, 184, 305, 339, 133, 299, 46, 115, 26, 167, 127,
                261, 329, 3, 222, 36, 89, 65, 33, 31, 287, 398, 272, 269, 149, 74, 312, 258, 50, 47, 250, 82, 229, 368, 216, 145, 94, 375, 373, 246,
                60, 84, 351, 243, 16, 28, 359, 158, 191, 41, 383, 307, 249, 40, 379, 315, 277, 300, 166, 53, 165, 362, 369, 290, 364, 268, 14, 223, 67,
                106, 334, 232, 235, 211, 273, 357, 0, 9, 377, 174, 100, 199, 164, 387, 135, 97, 288, 12, 365, 289, 331, 388, 168, 226, 203, 92, 204, 7,
                281, 352, 370, 254, 81, 318, 83, 182, 302, 178, 278, 260, 213, 205, 131, 384, 159, 259, 347, 378, 73, 301, 49, 355, 344, 358, 98, 282, 202,
                121, 265, 118, 323, 39, 210, 35, 333, 310, 330, 308, 30, 10, 327, 320, 219, 130, 245, 342, 71, 280, 206, 326, 180, 240, 236, 363, 316, 99, 51,
                176, 68, 152, 179, 169, 252, 18, 8, 346, 52, 353, 399, 125, 285, 154, 313, 374, 380, 44, 120, 112, 309, 144, 20, 185, 293, 139, 360, 72, 189, 1,
                6, 138, 143, 225, 239, 303, 319, 102, 122, 283, 251, 354, 123, 263, 76, 105, 109, 394, 381, 172, 314, 324, 66, 376, 156, 90, 332, 153, 200, 276,
                62, 58, 57, 146, 322, 266, 45, 233, 4, 162, 274, 345, 186, 193, 262, 17, 337, 114, 29, 157, 171, 87, 80, 297, 13, 340, 181, 234, 195, 101, 192, 325,
                279, 77, 296, 257, 5, 128, 170, 32, 34, 372, 134, 244, 348, 349, 291, 136, 230, 150, 248, 397, 201, 86, 371, 23, 366, 113, 85, 306, 228, 361, 19, 70,
            294, 271, 238, 253, 385, 241, 38, 292, 220, 61, 336, 163, 42, 116, 48, 129, 161 };
        Arrays.sort(data);
        for (int datum : data) {
            System.out.println(datum);
        }

    }

    @Test
    public void sss(){
        Params.N = 20;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Coordinate coordinate = new Coordinate();
                coordinate.setColNo(i);
                coordinate.setRowNo(j);
                coordinate.calibrationByCoordinate();
                //库位编号
                int no = coordinate.getNo();
                //货物编号
                System.out.println(no);
            }

        }
    }
    @Test
    public void s1() throws RserveException, REXPMismatchException {
        //建立连接
        RConnection rc = new RConnection();
//        rc.assign("functionPath","D:\\works\\R\\backup\\cluster.R");
//        rc.eval("source(functionPath)");

        rc.assign("n",String.valueOf(10));
        rc.assign("k",String.valueOf(3));
        rc.assign("dataPath","D:\\works\\data\\all\\brandDistance.csv");

        REXP eval = rc.eval("cluster(dataPath,n,k)");
        //返回结果是 组别 每一位s[i]是第i个属于那个组别
        String s[] = eval.asStrings();
        for (int i = 0; i < s.length; i++) {
            System.out.print(s[i]+" ");
        }
    }

}
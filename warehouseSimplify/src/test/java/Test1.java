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
import java.util.HashMap;
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
    public void asdfsa(){
        String s = "123.19121475936127 123.24970533885138 123.01773219523956 122.97673244527584 123.03413742470839 123.21677343347233 122.9106816255931 122.60039963610085 122.51025184517513 123.24734222877711 122.9954970932555 \n" +
                "122.0661930019517 122.10059660516461 122.62360161054603 122.03803961217955 122.08533861227573 122.52998587541782 122.41102290946452 122.26116201076151 122.16108631805032 122.34055722120208 122.26175837770138 \n" +
                "122.07724662521314 122.2794955150106 122.0239693392004 121.73311639315929 122.04371986553652 122.19904418041537 122.03304659348167 122.0288769496982 121.94667984318927 121.98892269861626 122.03541180035208 \n" +
                "121.81910248353856 121.60766788571314 121.87790037839926 122.93458335107672 121.92486659026778 121.5934968818177 122.25298121518058 121.61373327838685 122.18451571181183 121.81851596560551 121.96273637417978 \n" +
                "121.98122526346566 121.77185241834684 121.79548199115554 121.49135369448295 121.64745745183238 122.00955554887005 122.25889652640036 121.71345144992046 121.24628704713545 121.94384095350995 121.78594023451197 \n" +
                "121.89965751433544 121.98514883672398 122.24608381265072 121.9380293776686 122.34323046586788 121.69934361509686 121.84002073889599 121.81752415692067 122.33559563116611 122.23721259486462 122.03418467441907 \n" +
                "121.67669260759737 121.74100153772793 121.49242961120203 121.88083410497879 121.70129165024613 121.72085067622598 121.6152299448609 121.2870783890948 121.80685612217066 121.70239024592205 121.66246548900267 \n" +
                "121.48551359615607 121.0333817060873 121.98535009434049 121.6906883676817 121.48455706743637 121.43452200605803 121.64906082543074 121.45108173888605 121.39327652147159 121.3262043361167 121.49336362596651 \n" +
                "120.55936722007097 121.07518461931224 121.40370903110778 120.6406807106157 120.63714198035987 121.33074584742059 120.90184617174991 121.38241168804575 120.72382710118964 120.85155191027081 120.95064662801433 \n" +
                "120.36548726670101 121.15840457321885 121.05822565755243 121.12987027771294 120.98695419525724 120.58868232234862 120.89817577296725 120.8239886140352 120.95160859184044 120.79604706420017 120.87574443358339 \n" +
                "121.9470730739394 121.7955193805591 121.8752558986262 122.31827875029364 122.09854112290648 122.26850614538412 121.90238583905148 122.05749904730774 122.28432980020104 122.28841816476137 122.08358072230305 \n" +
                "121.09083924324985 121.25746339233532 121.66013845124002 121.4318956575289 121.8553985373862 121.05307009129695 121.24653872256768 121.55605494763364 121.60364447082802 121.32074298217293 121.40757864962396 \n" +
                "121.37709516495846 121.41327662839888 121.03372548555285 121.03311680391995 121.2051377438577 121.51155352355286 120.66247593061907 121.08975869670803 121.36839670079273 121.50914108584557 121.2203677764206 \n" +
                "120.9740851671921 120.95980818178514 120.85033730810906 121.09828471342611 121.99771505319539 120.94331026351628 120.58482603646536 121.48881101421162 120.36710083389939 121.27792760428822 121.05422061760885 \n" +
                "120.66555256366007 120.72382596033691 120.79187414381072 120.61056423622729 121.13785010039228 120.80430283638906 120.8531472621031 121.10346729367018 120.72347357047855 120.62672630953251 120.80407842766007 \n" +
                "122.37794669590903 121.58935308910834 121.53290401931514 122.2190930674868 121.56393184246218 121.84458592608416 121.79369325016762 121.94892012580925 122.02696624970122 121.74766037239522 121.8645054638439 ";
        String[] split = s.split("\n");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        for (int i = 0; i < split.length; i++) {
            String s1 = split[i];
            String[] split1 = s1.split("\\s+");
            double  sum = 0.0;
            for (int j = 0; j < split1.length-1; j++) {
                sum += Double.valueOf(split1[j]);

            }
            sum /= (split1.length-1);
            System.out.println(sum);
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
    @Test
    public void assss(){
        System.out.println("asda");
    }
    @Test
    public void awsda(){
        HashMap<Integer, String> integerStringHashMap = new HashMap<>();
        integerStringHashMap.put(1,"da");
        integerStringHashMap.put(2,"da");
        integerStringHashMap.put(54,"da");
        System.out.println(integerStringHashMap);
    }
}
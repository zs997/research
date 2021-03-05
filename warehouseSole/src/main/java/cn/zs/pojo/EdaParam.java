/**
 * projectName: research
 * fileName: EdaParam.java
 * packageName: cn.cn.zs.pojo
 * date: 2021-03-03 15:06
 * copyright(c) 2019-2021 hust
 */
package cn.zs.pojo;

/**
 * @version: V1.0
 * @author: cn.zs
 * @className: EdaParam
 * @packageName: cn.cn.zs.pojo
 * @data: 2021-03-03 15:06
 **/
public class EdaParam {
    private final int populationSize;
    private final int superiorityCount;
    private final int eliteCount;
    private final double alpha;
    private final int localSearchTimes;

    public EdaParam(int populationSize, int superiorityCount, double alpha, int localSearchTimes, int eliteCount){
      this.populationSize = populationSize;
      this.superiorityCount =superiorityCount;
      this.eliteCount = eliteCount;
      this.alpha  = alpha;
      this.localSearchTimes = localSearchTimes;
  }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getSuperiorityCount() {
        return superiorityCount;
    }

    public int getEliteCount() {
        return eliteCount;
    }

    public double getAlpha() {
        return alpha;
    }

    public int getLocalSearchTimes() {
        return localSearchTimes;
    }
}
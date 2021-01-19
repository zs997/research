package cn.zs.view;
import java.awt.*;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import javax.swing.*;
public class LineView {
    /**
     * 创建JFreeChart Line Chart（折线图）
     */
    public static  void main(String args[]){
        int x [] ={1,2,3,4,5};
        double y1[] = {10,12,45,66,25};
        double y [][] = new double[1][];
        y[0] = y1;
        String bar [] = {"ss"};
        printPic(x,y,bar);
    }
    public static void printPic(int x [],double y[][],String bar []) {
        //步骤1：创建CategoryDataset对象（准备数据）
        CategoryDataset dataset = createDataset(x,y,bar);
        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
        JFreeChart freeChart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(freeChart, true);
        JFrame frame=new JFrame("");
        frame.setLayout(new GridLayout(2,2,10,10));
        frame.add(chartPanel);    //加入折线图
        frame.setBounds(50, 50, 800, 600);
        frame.setVisible(true);
    }
    public static void printPic(ArrayList<Integer> x,ArrayList<ArrayList<Double>> y,ArrayList<String> stackBar) {
        //步骤1：创建CategoryDataset对象（准备数据）
        CategoryDataset dataset = createDataset(x,y,stackBar);
        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
        JFreeChart freeChart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(freeChart, true);
        JFrame frame=new JFrame("");
        frame.setLayout(new GridLayout(2,2,10,10));
        frame.add(chartPanel);    //加入折线图
        frame.setBounds(50, 50, 800, 600);
        frame.setVisible(true);
    }
    // 根据CategoryDataset创建JFreeChart对象
    public static JFreeChart createChart(CategoryDataset categoryDataset) {
        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart("evolution curve", // 标题ge
                "", // categoryAxisLabel （category轴，横轴，X轴标签）
                "", // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDataset, // dataset
                PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs

        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);
        // 其他设置 参考 CategoryPlot类
        return jfreechart;
    }
    /**
     * 创建CategoryDataset对象
     *
     */
    public static CategoryDataset createDataset(int [] x,double [][] y,String [] stackBar) {
        String[] rowKeys = stackBar;
        String[] colKeys = new String[x.length];
        for (int i = 0; i < x.length; i++) {
            colKeys[i] = String.valueOf(x[i]);
        }
        double[][] data = y;
        // 或者使用类似以下代码
        // DefaultCategoryDataset categoryDataset = new
        // DefaultCategoryDataset();
        // categoryDataset.addValue(10, "rowKey", "colKey");
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
    }
    public static CategoryDataset createDataset(ArrayList<Integer> x,ArrayList<ArrayList<Double>> y,ArrayList<String> stackBar) {
        String[] rowKeys = new String[stackBar.size()];
        for (int i = 0; i < stackBar.size(); i++) {
            rowKeys[i] = stackBar.get(i);
        }
        String[] colKeys = new String[x.size()];

        for (int i = 0; i < x.size(); i++) {
            colKeys[i] = String.valueOf(x.get(i));
        }
        double[][] data = new double[y.size()][];
        for (int i = 0; i < y.size(); i++) {
            data[i] = new double[y.get(i).size()];
            for (int j = 0; j < y.get(i).size(); j++) {
                data[i][j] = y.get(i).get(j);
            }
        }
        // 或者使用类似以下代码
        // DefaultCategoryDataset categoryDataset = new
        // DefaultCategoryDataset();
        // categoryDataset.addValue(10, "rowKey", "colKey");
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
    }
}
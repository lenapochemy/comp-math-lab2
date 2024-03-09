package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleFunction;

public class Chart {

    public Chart(){
    }

    public void draw(String methodName, Map<Double, Double> map){
        XYSeries series = new XYSeries("f(x)");
        for(Double x : map.keySet()){
            series.add(x, map.get(x));
        }
        XYDataset xyDataset = new XYSeriesCollection(series);
        show(xyDataset, methodName);
    }

    public static void drawGraph(Map<Integer, DoubleFunction<Double>> function_map, int num){
        Map<Double, Double> map = new HashMap<>();
        int b;
        if(num == 5) b = 17;
            else b = 7;
        for(double i = -5; i <= b ; i += 0.1){
            double f = function_map.get(num).apply(i);
            map.put(i, f);
        }
        Chart chart = new Chart();
        chart.draw("f(x)", map);
    }

    public void drawTwoGraphics(Map<Double, Double> map1, Map<Double, Double> map2, String methodName){
        XYSeries series1 = new XYSeries("1");
        for(Double x : map1.keySet()){
            series1.add(x, map1.get(x));
        }
        XYSeries series2 = new XYSeries("2");
        for(Double x : map2.keySet()){
            series2.add(x, map2.get(x));
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);

        show(xyDataset, methodName);
    }

    public void show(XYDataset xyDataset, String  methodName){
        JFreeChart chart = ChartFactory
                .createXYLineChart(methodName, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(600, 600);
        frame.show();
    }

    public void drawGraphics(double[] x1, double[] y1, double[] x2, double[] y2, double[] x3, double[] y3, String methodName){
        XYSeries series1 = new XYSeries("1");
        for(int i = 0; i < x1.length; i++){
            series1.add(x1[i], y1[i]);
        }
        XYSeries series2 = new XYSeries("2");
        for(int i = 0; i < x2.length; i++){
            series2.add(x2[i], y2[i]);
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        if(x3 != null){
            XYSeries series3 = new XYSeries("3");
            for(int i = 0; i < x3.length; i++){
                series3.add(x3[i], y3[i]);
            }
            xyDataset.addSeries(series3);
        }
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);

        show(xyDataset, methodName);
    }


    public static void drawForSystem(int num){

        switch (num){
            case 1 -> {
                double[] x1 = new double[41];
                double[] y1 = new double[41];
                double[] x2 = new double[41];
                double[] y2 = new double[41];
                double[] x3 = new double[41];
                double[] y3 = new double[41];
                int j = 0;
                for(double i = -2.0; i < 2.1; i+=0.1, j++){
                    x1[j] = i;
                    y1[j] = 2 * i * i;
                    x2[j] = i;
                    x3[j] = i;
                    y2[j] = Math.sqrt(4 - i * i);
                    y3[j] = 0 - Math.sqrt(4 - i * i);
                }
                y2[40] = 0.0;
                y3[40] = 0.0;
                Chart chart = new Chart();
                chart.drawGraphics(x1, y1, x2, y2, x3, y3, "aaa");
            }
            case 2 -> {
                Map<Double, Double> map1 = new HashMap<>();
                Map<Double, Double> map2 = new HashMap<>();
                int j = 0;
                for(double i = -2.0; i < 2.1; i+=0.1, j++){
                    if(i >= -1.4 && i<= 0.6) {
                        map1.put(i, Math.asin(-0.4 - i));
                    }
                    map2.put(i, Math.cos(i+1) / 2);
                }
                Chart chart = new Chart();
                chart.drawTwoGraphics(map1, map2, "Метод Ньютона");
            }
            default -> {
                Map<Double, Double> map1 = new HashMap<>();
                Map<Double, Double> map2 = new HashMap<>();
                int j = 0;
                for(double i = 0.0; i < 4.1; i+=0.1, j++){
                    map1.put(i, 0 - i - i * i);
                    map2.put(i, 10 - i * i *i);
                }
                Chart chart = new Chart();
                chart.drawTwoGraphics(map1, map2, "Метод Ньютона");
            }
        }
    }
}

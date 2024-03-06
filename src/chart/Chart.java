package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Map;

public class Chart {

//    private

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
}

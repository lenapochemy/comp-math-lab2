package methods;

import chart.Chart;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class SystemNewtonMethod {

    public SystemNewtonMethod(BiFunction<Double, Double, Double> func1, BiFunction<Double, Double, Double> func2,
                              double eps, double x_0, double y_0, int num){
        this.func1 = func1;
        this.func2 = func2;
        this.eps = eps;
        this.x_0 = x_0;
        this.y_0 = y_0;
        this.num = num;
    }

    private final BiFunction<Double, Double, Double> func1, func2;
    private boolean solveMode, outputMode;

    private final double eps, x_0, y_0;
    private double dx, dy, x_k, y_k, x_k_next, y_k_next;
    private final int num;
    private FileWriter file;
    public void setFile(FileWriter file){
        this.file = file;
    }

    public void setSolveMode(boolean solveMode){
        this.solveMode = solveMode;
    }
    public void setOutputMode(boolean outputMode){
        this.outputMode = outputMode;
    }

    public BiFunction<Double, Double, Double> partialDeriveX(BiFunction<Double, Double, Double> f){
        double dx = 0.0001;
        return (x, y) -> ((f.apply(x + dx, y)) - (f.apply(x, y)) ) / dx;
    }

    public BiFunction<Double, Double, Double> partialDeriveY(BiFunction<Double, Double, Double> f){
        double dy = 0.0001;
        return (x, y) -> ((f.apply(x, y + dy)) - (f.apply(x, y)) ) / dy;
    }

    public void solve(){
        draw();

        BiFunction<Double, Double, Double> df1_dx = partialDeriveX(func1);
        BiFunction<Double, Double, Double> df2_dx = partialDeriveX(func2);
        BiFunction<Double, Double, Double> df1_dy = partialDeriveY(func1);
        BiFunction<Double, Double, Double> df2_dy = partialDeriveY(func2);

        x_k = x_0;
        y_k = y_0;
        writeIteration("Первое приближение: x = " + x_k + "  y = " + y_k + "\n--------------------------\n");

        boolean flag = true;
        int i = 0;
        while (flag) {
            i++;
            double a = df1_dx.apply(x_k, y_k);
            double b = df1_dy.apply(x_k, y_k);
            double c = df2_dx.apply(x_k, y_k);
            double d = df2_dy.apply(x_k, y_k);
            double f1 = func1.apply(x_k, y_k);
            double f2 = func2.apply(x_k, y_k);

            dy = ( 0 - a * f2 + c * f1) / (d * a - c * b);
            dx = (0 - f1 - b * dy) / a;

            x_k_next = x_k + dx;
            y_k_next = y_k + dy;

            writeIteration( "Итерация " + i + "\nНовое приближение: x = " + x_k_next + "  y = " + y_k_next + "\n--------------------------\n");

            if(checkEndCondition()) {
                flag = false;
                writeResult( "x = " + x_k_next + " y = " + y_k_next +
                        "\nЗначение функций в корне " + func1.apply(x_k_next, y_k_next) + " " + func2.apply(x_k_next, y_k_next) +
                        "\nКоличество итераций: " + i +
                        "\nВектор погрешностей: " + Math.abs(x_k_next - x_k) +" " + Math.abs(y_k_next - y_k));
            }

            x_k = x_k_next;
            y_k = y_k_next;
        }
    }


    private boolean checkEndCondition(){
        return (Math.abs(x_k_next - x_k) < eps && Math.abs(y_k_next - y_k) < eps);
    }

    public void writeResult(String string){
        if(outputMode){
            System.out.println(string);
        }  else {
            try {
                file.write(string);
                file.close();
            } catch (IOException e){
                System.out.println("Проблемы с файлом");
            }
        }
    }

    public void writeIteration(String string){
        if(solveMode){
            if(outputMode){
                System.out.println(string);
            } else {
                try {
                    file.write(string);
//                    file.close();
                } catch (IOException e){
                    System.out.println("Проблемы с файлом");
                }
            }
        }
    }
    public void draw(){

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

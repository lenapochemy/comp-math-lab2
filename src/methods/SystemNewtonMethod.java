package methods;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiFunction;


public class SystemNewtonMethod {

    public SystemNewtonMethod(BiFunction<Double, Double, Double> func1, BiFunction<Double, Double, Double> func2,
                              double eps, double x_0, double y_0){
        this.func1 = func1;
        this.func2 = func2;
        this.eps = eps;
        this.x_0 = x_0;
        this.y_0 = y_0;
    }

    private final BiFunction<Double, Double, Double> func1, func2;
    private boolean solveMode, outputMode;

    private final double eps, x_0, y_0;
    private double dx, dy, x_k, y_k, x_k_next, y_k_next;
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
                } catch (IOException e){
                    System.out.println("Проблемы с файлом");
                }
            }
        }
    }
}

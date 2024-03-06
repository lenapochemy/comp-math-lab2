package methods;

import chart.Chart;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleFunction;

public abstract class AbstractMethod {


    public final double eps;
    public double x_i, x_i_next, a, b;
    DoubleFunction<Double> function, derive_function;
    int iterationNumber;
    boolean solveMode, outputMode;
    private final String name;
    public FileWriter file;
    public AbstractMethod(DoubleFunction<Double> function, double eps, double a, double b, String name){
        this.function = function;
        this.eps = eps;
        this.a = a;
        this.b = b;
        this.derive_function = derive(function);
        this.name = name;
    }
    public void drawGraph(){
        Map<Double, Double> map = new HashMap<>();
        for(double i = a - 0.2; i <= b + 0.2; i += 0.1){
            double f = function.apply(i);
            map.put(i, f);
        }
        Chart chart = new Chart();
        chart.draw(name, map);
    }
    public DoubleFunction<Double> derive(DoubleFunction<Double> f){
        double dx = 0.0001;
        return x -> ((f.apply(x + dx)) - (f.apply(x)) ) / dx;
    }

    public void setSolveMode(boolean solveMode){
        this.solveMode = solveMode;
    }
    public void setOutputMode(boolean outputMode){
        this.outputMode = outputMode;
    }
    public void setFile(FileWriter file){
        this.file = file;
    }
    public abstract void solve();
    public abstract boolean checkEndConditional();

    public double chooseFirstApproximation(){
        DoubleFunction<Double> second_derive = derive(derive_function);
        if(function.apply(a) * second_derive.apply(a) > 0) {
            return a;
        }
        if(function.apply(b) * second_derive.apply(b) > 0){
            return b;
        }
        return a;
    }


    // вернет true если на промежутке только один корень
    public boolean checkRootCount(){
        if(function.apply(a) * function.apply(b) > 0) return false;
//        if(derive_function.apply(a) * derive_function.apply(b) < 0) return false;
        return true;
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

    public void writeResult(String string){
        if(outputMode){
            System.out.println(string);
        } else {
            try {
                file.write(string);
                file.close();
            } catch (IOException e){
                System.out.println("Проблемы с файлом");
            }
        }
    }

}

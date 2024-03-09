package methods;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.DoubleFunction;

public abstract class AbstractMethod {


    public final double eps;
    public double x_i, x_i_next, a, b;
    DoubleFunction<Double> function, derive_function;
    int iterationNumber;
    boolean solveMode, outputMode;
    public FileWriter file;
    public AbstractMethod(DoubleFunction<Double> function, double eps, double a, double b){
        this.function = function;
        this.eps = eps;
        this.a = a;
        this.b = b;
        this.derive_function = derive(function);
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
        } else return b;
//        if(function.apply(b) * second_derive.apply(b) > 0){
//            return b;
//        }
//        return a;
    }


    // вернет true если на промежутке только один корень
    public static boolean checkRootCount(DoubleFunction<Double> function, double a, double b){
        if(function.apply(a) * function.apply(b) > 0) return false;
        int count = 0;
        for (double i = a; i <= b -0.1; i += 0.1){
            if(function.apply(i) * function.apply(i+0.1) < 0) count++;
            if(count > 1) return false;
        }
        return true;
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

package methods;

import java.util.function.DoubleFunction;

public class MethodHalfDivision extends AbstractMethod {

    public MethodHalfDivision(DoubleFunction<Double> function, double eps, double a, double b){
        super(function, eps, a, b, "Метод половинного деления");
    }
    private double x, f_a, f_b, f_x;
    @Override
    public String solve(){
        drawGraph();
        iterationNumber = 0;
        while (!checkEndConditional()){
            x = (a + b) / 2;
            f_a = function.apply(a);
            f_b = function.apply(b);
            f_x = function.apply(x);
//            System.out.println(a + " " + f_a + " " + b + " " + f_b + " " + x + " " + f_x);
            if(f_a * f_x < 0) {
                b = x;
            } else if(f_x * f_b < 0){
                a = x;
            }
            iterationNumber++;
        }

        if(Math.abs(f_a) < Math.abs(f_b)){
            x = a;
        } else x = b;

        return  "Найденный корень: " + x + "\nЗначение функции в корне: " + function.apply(x) +
                "\nЧисло итераций: " + iterationNumber;

    }


    //вернет true, если условие окончания выполняется и это последняя итерация
    @Override
    public boolean checkEndConditional(){
//        System.out.println("aaa " + Math.abs(a - b));
        return Math.abs(a - b) < eps ;
    }

}

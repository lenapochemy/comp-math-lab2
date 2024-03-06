package methods;

import java.util.function.DoubleFunction;

public class NewtonMethod extends AbstractMethod {

    public NewtonMethod(DoubleFunction<Double> function, double eps, double a, double b){
        super(function, eps, a, b, "Метод Ньютона");
    }
    @Override
    public String solve(){
        drawGraph();
        //выбор начальногоо приближения
        x_i = chooseFirstApproximation();

        iterationNumber = 0;
        x_i_next = x_i - ( function.apply(x_i) / derive_function.apply(x_i) );
//        System.out.println(x_i + " " + function.apply(x_i) + " " + x_i_next + " " + function.apply(x_i_next));
        do {
            x_i = x_i_next;
            x_i_next = x_i - ( function.apply(x_i) / derive_function.apply(x_i) );
            iterationNumber++;
//            System.out.println(x_i + " " + function.apply(x_i) + " " + x_i_next + " " + function.apply(x_i_next));
        } while (!checkEndConditional());

        return  "Найденный корень: " + x_i_next + "\nЗначение функции в корне: " + function.apply(x_i_next) +
                "\nЧисло итераций: " + iterationNumber;
    }

    //вернет true, если условие окончания выполняется и это последняя итерация
    @Override
    public boolean checkEndConditional(){
//        System.out.println("aaa " + Math.abs(x_i_next - x_i));
        return Math.abs(x_i_next - x_i) < eps;
    }
}

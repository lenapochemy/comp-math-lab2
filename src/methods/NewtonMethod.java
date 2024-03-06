package methods;

import java.util.function.DoubleFunction;

public class NewtonMethod extends AbstractMethod {

    public NewtonMethod(DoubleFunction<Double> function, double eps, double a, double b){
        super(function, eps, a, b, "Метод Ньютона");
    }
    @Override
    public void solve(){
        drawGraph();
        //выбор начальногоо приближения
        x_i = chooseFirstApproximation();
        writeIteration("Первое приближение: " + x_i + "\n--------------------------\n");

        iterationNumber = 0;
        x_i_next = x_i - ( function.apply(x_i) / derive_function.apply(x_i) );
        do {
            x_i = x_i_next;
            x_i_next = x_i - ( function.apply(x_i) / derive_function.apply(x_i) );
            writeIteration( "Итерация " + iterationNumber + "\nНовое приближение: " + x_i_next + "\n--------------------------\n");
            iterationNumber++;
        } while (!checkEndConditional());

        writeResult( "Найденный корень: " + x_i_next + "\nЗначение функции в корне: " + function.apply(x_i_next) +
                "\nЧисло итераций: " + iterationNumber);
    }

    //вернет true, если условие окончания выполняется и это последняя итерация
    @Override
    public boolean checkEndConditional(){
        return Math.abs(x_i_next - x_i) < eps;
    }
}

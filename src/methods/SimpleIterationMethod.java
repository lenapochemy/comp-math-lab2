package methods;

import java.util.function.DoubleFunction;

public class SimpleIterationMethod extends AbstractMethod {
    public SimpleIterationMethod(DoubleFunction<Double> f, double eps, double a, double b){
        super(f, eps, a, b, "Метод простой итерации");
    }
    private double lambda = 0;
    @Override
    public void solve(){
        double f1_a, f1_b;
        //ищем функцию
        f1_a = derive_function.apply(a);
        f1_b = derive_function.apply(b);
        if(f1_a > 0 && f1_b > 0) {
            lambda = -1 / Math.max(f1_a, f1_b);
        } else {
            lambda = 1 / Math.min(f1_a, f1_b);
        }
        DoubleFunction<Double> fi_function = x -> (x + lambda * function.apply(x));
        DoubleFunction<Double> derive_fi = derive(fi_function);

        //проверка условия сходимости
        if(Math.abs(derive_fi.apply(a)) > 1 || Math.abs(derive_fi.apply(b)) > 1){
            writeIteration("Достаточное условие сходимости метода простой итерации на данном интервале не выполнено" + "\n--------------------------\n");
            System.exit(0);
        } else writeIteration("Достаточное условие сходимости выполнено" + "\n--------------------------\n");

        drawGraph();

        // первое приближение
        x_i = chooseFirstApproximation();
        writeIteration("Первое приближение: " + x_i + "\n--------------------------\n");

        x_i_next = fi_function.apply(x_i);
        iterationNumber = 1;
        while (!checkEndConditional()) {
            x_i = x_i_next;
            x_i_next = fi_function.apply(x_i);
            writeIteration( "Итерация " + iterationNumber + "\nНовое приближение: " + x_i_next + "\n--------------------------\n");
            iterationNumber++;
        }

        writeResult( "Найденный корень: " + x_i_next + "\nЗначение функции в корне: " + function.apply(x_i_next) +
                "\nЧисло итераций: " + iterationNumber);
    }

    //вернет true, если условие окончания выполняется и это последняя итерация
    @Override
    public boolean checkEndConditional(){
        return Math.abs(x_i_next - x_i) < eps;
    }
}

package methods;

import java.util.function.DoubleFunction;

public class SimpleIterationMethod extends AbstractMethod {
    public SimpleIterationMethod(DoubleFunction<Double> f, double eps, double a, double b){
        super(f, eps, a, b, "Метод простой итерации");
    }
    private double lambda = 0;
    @Override
    public String solve(){
        double f1_a, f1_b;
        //ищем функцию
        f1_a = derive_function.apply(a);
        f1_b = derive_function.apply(b);
        if(f1_a > 0 && f1_b > 0) {
            lambda = -1 / Math.max(f1_a, f1_b);
        } else {
            lambda = 1 / Math.min(f1_a, f1_b);
        }
//        System.out.println("lambda " + lambda);
        DoubleFunction<Double> fi_function = x -> (x + lambda * function.apply(x));
        DoubleFunction<Double> derive_fi = derive(fi_function);
//        System.out.println("-2 " + fi.apply(-2) + " -1 " + fi.apply(-1)  );

        //проверка условия сходимости
        if(Math.abs(derive_fi.apply(a)) > 1 || Math.abs(derive_fi.apply(b)) > 1){
            System.out.println("Достаточное условие сходимости метода простой итерации на данном интервале не выполнено");
//            System.exit(0);
        } else System.out.println("Достаточное условие сходимости выполнено");


        drawGraph();

        // первое приближение
//        x_i = a;
        x_i = chooseFirstApproximation();

        x_i_next = fi_function.apply(x_i);
        iterationNumber = 1;
//        System.out.println(x_i + " " + x_i_next );
        while (!checkEndConditional()) {
            x_i = x_i_next;
            x_i_next = fi_function.apply(x_i);
            iterationNumber++;
//            System.out.println(x_i + " " + x_i_next );
        }

        return "Найденный корень: " + x_i_next + "\nЗначение функции в корне: " + function.apply(x_i_next) +
                "\nЧисло итераций: " + iterationNumber;
    }

    //вернет true, если условие окончания выполняется и это последняя итерация
    @Override
    public boolean checkEndConditional(){
//        System.out.println("aaa " + Math.abs(x_i_next - x_i));
        return Math.abs(x_i_next - x_i) < eps;
    }
}

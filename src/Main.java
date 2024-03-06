import methods.AbstractMethod;
import methods.SystemNewtonMethod;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.DoubleFunction;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ScannerManager scannerManager = new ScannerManager(scanner);

        String[] functions;
        Map<Integer, DoubleFunction<Double>> map = new HashMap<>();
        boolean systemMode = scannerManager.sayEquationType();
        if(!systemMode) {
            functions = new String[]{
                    "x³-x+4=0",
                    "x³-4.5x²-9.21x-0.383=0",
                    "x*sin(x)+2x-3=0",
                    "x³+2x²-4x",
                    "ln(x²)-x+10"
            };
            map.put(1, x -> x * x * x - x + 4);
            map.put(2, x -> x * x * x - 4.5 * x * x - 9.21 * x - 0.383);
            map.put(3, x -> x * Math.sin(x) + 2 * x - 3);
            map.put(4, x -> x * x * x + 2 * x * x - 4 * x);
            map.put(5, x -> Math.log(x * x) - x + 10);
        } else {
            functions = new String[]{
                    "{ x²+y²-4=0 \n\t\t-3x²+y=0",
                    "{ x+sin(y)+0.4=0 \n\t\t 2y-cos(x+1)=0",
                    "{ x²+x+y=0 \n\t\t y+x³-10=0"
            };
        }

            int num = scannerManager.sayFunctionNumber(functions);

            if (scannerManager.sayInputMode()) {
                scannerManager.setFileMode(false);
            } else {
                scannerManager.setScanner(scannerManager.sayNewScanner());
                scannerManager.setFileMode(true);
            }
            double eps = scannerManager.sayEpsilon();

            String result;
            if(systemMode){
                double x_0 = scannerManager.sayDoubleNumber("начальное приближение для переменной x");
                double y_0 = scannerManager.sayDoubleNumber("начальное приближение для переменной y");
                BiFunction<Double, Double, Double> f1x = (x, y) -> (x * x + y * y -4),
                        f1y = (x, y) -> (-3 * x * x + y),
                        f2x = (x, y) -> (x + Math.sin(y) + 0.4),
                        f2y = (x, y) -> (2 * y - Math.cos(x+1)),
                        f3x = (x, y) -> (x * x + x + y),
                        f3y = (x, y) -> (y + x * x * x - 10);
                SystemNewtonMethod method;
                switch (num) {
                    case 1 -> method = new SystemNewtonMethod(f1x, f1y, eps, x_0, y_0, num);
                    case 2 -> method = new SystemNewtonMethod(f2x, f2y, eps, x_0, y_0, num);
                    default -> method = new SystemNewtonMethod(f3x, f3y, eps, x_0, y_0, num);
                }

                result = method.solve();
            } else {
                double a = scannerManager.sayDoubleNumber("левой границы интервала");
                double b = scannerManager.sayDoubleNumber("правой границы интервала");
                AbstractMethod method = scannerManager.sayMethod(map.get(num), eps, a, b);


                if (!method.checkRootCount()) {
                    System.out.println("На данном интервале несколько корней или они отсутствуют");
                    System.exit(0);
                }
//                method.setSolveMode(scannerManager.saySolveMode());
                result = method.solve();
            }

            if (scannerManager.sayOutputMode()) {
                System.out.println(result);
            } else {
                FileWriter writer = scannerManager.sayFileToWrite();
                try {
                    writer.write(result);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Проблемы с файлом");
                }
            }


//        C:\Users\Elena\IdeaProjects\compMath\lab2\src\files\file1
    }
}
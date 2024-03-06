import exceptions.FileException;
import exceptions.IncorrectValueException;
import methods.AbstractMethod;
import methods.MethodHalfDivision;
import methods.NewtonMethod;
import methods.SimpleIterationMethod;

import java.io.*;
import java.util.*;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class ScannerManager {
    private Scanner scanner;
    private boolean fileMode;
    public ScannerManager(Scanner scanner){
        this.scanner = scanner;
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public void setFileMode(boolean fileMode){
        this.fileMode = fileMode;
    }
    //вернет true если ввод с клавиатуры
    public boolean sayInputMode(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Вы хотите вводить данные с клавиатуры или из файла? (k/f)");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                        throw new NullPointerException();
                    case "k" -> {
                        flag = true;
                        return true;
                    }
                    case "f" -> {
                        flag = true;
                        return false;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"k\" или \"f\"");
            }
        }
        return flag;
    }

    public boolean sayOutputMode(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Результаты вывести на экран или в записать в файл? (s/f)");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "s" -> {
                        flag = true;
                        return true;
                    }
                    case "f" -> {
                        flag = true;
                        return false;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"s\" или \"f\"");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return flag;
    }


    public boolean saySolveMode(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.print("Нужно выводить результат каждой итерации решения? (y/n)");
                String ans = scanner.nextLine().trim();
                if(fileMode) System.out.println(ans);
                switch (ans) {
                    case "" ->
                        throw new NullPointerException();
                    case "y" -> {
                        flag = true;
                        return true;
                    }
                    case "n" -> {
                        flag = true;
                        return false;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"y\" или \"n\"");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return flag;
    }

    public AbstractMethod sayMethod(DoubleFunction<Double> function, double eps, double a, double b){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.print("Выберите метод решения уравнения: Метод половинного деления(d), Метод Ньютона(n), Метод простой итерации(i) ");
                String ans = scanner.nextLine().trim();
                if(fileMode) System.out.println(ans);
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "d" -> {
                        flag = true;
                        return new MethodHalfDivision(function, eps, a, b);
                    }
                    case "n" -> {
                        flag = true;
                        return new NewtonMethod(function, eps, a, b);
                    }
                    case "i" -> {
                        flag = true;
                        return new SimpleIterationMethod(function, eps, a, b);
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"d\" или \"n\" или \"i\"");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return null;
    }

//    public GaussSeidelMethod saySLAE(){
//        int n = sayN();
//        return new GaussSeidelMethod(n, sayMatrix(n), sayBi(n),sayEpsilon(),  sayM());
//    }

    public int sayFunctionNumber(String[] functionStrings){
        int n = functionStrings.length;
        int num = 0;
        String sNum;
        while (num <= 0 || num > n){
            try {
                System.out.println("Выберите функцию для решения: ");
                for(int i = 0; i < n; i++){
                    System.out.println("\t" + (i+1) + ". "+ functionStrings[i]);
                }
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Integer.parseInt(sNum);
                if(num <= 0 || num > n) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Номер функции должен быть положительным числом, не большим " + n );
                if(fileMode) errorEnd();
            } catch (NullPointerException e){
                System.out.println("Номер функции не может быть пустым");
                if(fileMode) errorEnd();
            }  catch (NumberFormatException e){
                System.out.println("Номер функции должен быть целым числом");
                if(fileMode) errorEnd();
            }
        }
        return num;
    }

    public Scanner sayNewScanner(){
        String sFile;
        Scanner scanner1 = null;
        while(scanner1 == null){
            try{
                System.out.println("Введите путь к файлу:");
                sFile = scanner.nextLine().trim();
                if(sFile.isEmpty()) throw new NullPointerException();
                File file = new File(sFile);
                if(file.exists() && !file.canRead()) throw new FileException();
                scanner1 = new Scanner(file);
            } catch (NullPointerException e){
                System.out.println("Путь не может быть пустым");
            } catch (FileException e){
                System.out.println("Данные из файла невозможно прочитать");
            } catch (FileNotFoundException e){
                System.out.println("Файл не найден");
            }
        }
        return scanner1;
    }

    public boolean sayEquationType(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Вы хотите решить нелинейное уравнение или систему нелинейных уравнений? (e/s)");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "e" -> {
                        flag = true;
                        return false;
                    }
                    case "s" -> {
                        flag = true;
                        return true;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"e\" или \"s\"");
            }
        }
        return flag;
    }

    public FileWriter sayFileToWrite(){
        String sFile;
        FileWriter writer = null;
        while(writer == null){
            try{
                System.out.println("Введите путь к файлу:");
                sFile = scanner.nextLine().trim();
                if(fileMode) System.out.println(sFile);
                if(sFile.isEmpty()) throw new NullPointerException();
                File file = new File(sFile);
                if(file.exists() && !file.canWrite()) throw new FileException();
                writer = new FileWriter(file);
            } catch (NullPointerException e){
                System.out.println("Путь не может быть пустым");
            } catch (FileException e){
                System.out.println("В файл невозможно записать");
            } catch (IOException e){
                System.out.println("Файл не найден");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return writer;
    }

    public double sayEpsilon(){
        double num = 0;
        String sNum;
        while (num < 0.000001 || num > 1){
            try {
                System.out.print("Введите значение точности [0.000001; 1]: ");
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Double.parseDouble(sNum);
                if(num < 0.000001 || num > 1) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Значение точности должно быть положительным числом из промежутка [0.000001; 1]");
                if(fileMode) errorEnd();
            } catch (NullPointerException e){
                System.out.println("Значение точности не может быть пустым");
                if(fileMode) errorEnd();
            }   catch (NumberFormatException e){
                System.out.println("Количество итераций должно быть числом");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return num;
    }

//    public double sayA(){
//        double num = 0;
//        String sNum;
//        boolean flag = true;
//        while (flag){
//            try {
//                System.out.print("Введите значение левой границы интервала: ");
//                sNum = scanner.nextLine().trim();
//                if(fileMode) System.out.println(sNum);
//                if(sNum.isEmpty()) throw new NullPointerException();
//                num = Double.parseDouble(sNum);
//                flag = false;
//            } catch (NullPointerException e){
//                System.out.println("Значение границы не может быть пустым");
//                if(fileMode) errorEnd();
//            }  catch (NumberFormatException e){
//                System.out.println("Значение границы должно быть числом");
//                if(fileMode) errorEnd();
//            } catch (NoSuchElementException e){
//                System.out.println("Данные не найдены в файле");
//                System.exit(0);
//            }
//        }
//        return num;
//    }
//
//    public double sayB() {
//        double num = 0;
//        String sNum;
//        boolean flag = true;
//        while (flag){
//            try {
//                System.out.print("Введите значение правой границы интервала: ");
//                sNum = scanner.nextLine().trim();
//                if(fileMode) System.out.println(sNum);
//                if(sNum.isEmpty()) throw new NullPointerException();
//                num = Double.parseDouble(sNum);
//                flag = false;
//            } catch (NullPointerException e){
//                System.out.println("Значение границы не может быть пустым");
//                if(fileMode) errorEnd();
//            } catch (NumberFormatException e){
//                System.out.println("Значение границы должно быть числом");
//                if(fileMode) errorEnd();
//            } catch (NoSuchElementException e){
//                System.out.println("Данные не найдены в файле");
//                System.exit(0);
//            }
//        }
//        return num;
//    }

    public double sayDoubleNumber(String name){
        double num = 0;
        String sNum;
        boolean flag = true;
        while (flag){
            try {
                System.out.print("Введите " + name +  " : ");
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Double.parseDouble(sNum);
                flag = false;
            } catch (NullPointerException e){
                System.out.println("Значение " + name +" не может быть пустым");
                if(fileMode) errorEnd();
            }  catch (NumberFormatException e){
                System.out.println("Значение " + name + " должно быть числом");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return num;
    }
    private void errorEnd(){
        System.out.println("В файле неверные данные, программа завершена");
        System.exit(0);
    }

}

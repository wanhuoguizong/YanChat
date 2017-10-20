package com.xinlingyijiu.yanchat.cmd;

import java.util.Scanner;
import java.util.function.Predicate;

public class ScannerUtil {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * 获取命令行输入
     * @param msg 提示信息
     * @param predicate 判断符合条件的
     * @return
     */
    public static String scannerInput(String msg, Predicate<String > predicate){
        String line = null;
        while (true){
            if (msg != null)System.out.println(msg);
            line = scanner.nextLine();
            if (line != null && predicate.test(line)){
                return line;
            }
        }
    }
}

package com.xinlingyijiu.yanchat.test;

import org.junit.Test;

import java.util.Scanner;

/**
 * Created by laotou on 2017/10/18.
 */
public class ScannerTest {
    @Test
    public void test1(){
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.next();
        System.out.println("nest:"+line1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.next();
        System.out.println("第一次:"+line1);
        String line2 = scanner.nextLine();
        System.out.println("第二次:"+line2);
    }
}

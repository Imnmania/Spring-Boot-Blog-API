package me.niloybiswas.spblog.util;

public class MyTest {
    public static void main(String[] args) {
        func1();
    }

    public static String func1 () {
        System.out.println("Hey hey");
        for (int i = 0; i < 5; i++) {
            System.out.println("Test");
            return "Test";
        }
        return "Hello";
    }
}

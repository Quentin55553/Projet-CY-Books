package org.openjfx.cybooks.CommandLine;

public class Colors {
    public static final String RED="\u001B[31m";
    public static final String GREEN="\u001B[32m";
    public static final String BLUE="\u001B[34m";
    public static final String YELLOW="\u001B[33m";
    public static final String PURPLE="\u001B[35m";
    public static final String CYAN="\u001B[36m";
    public static final String BLACK="\u001B[30m";
    public static final String WHITE="\u001B[37m";
    public static final String RESET="\u001B[0m";

    public static void printlnRed(String str){
        System.out.println(RED+str+RESET);
    }
    public static void printRed(String str){
        System.out.print(RED+str+RESET);
    }

    public static void printlnGreen(String str){
        System.out.println(GREEN+str+RESET);
    }
    public static void printGreen(String str){
        System.out.print(GREEN+str+RESET);
    }

    public static void printlnBlue(String str){
        System.out.println(BLUE+str+RESET);
    }
    public static void printBlue(String str){
        System.out.print(BLUE+str+RESET);
    }

    public static void printlnPurple(String str){
        System.out.println(PURPLE+str+RESET);
    }
    public static void printPurple(String str){
        System.out.print(PURPLE+str+RESET);
    }

    public static void printlnYellow(String str){
        System.out.println(YELLOW+str+RESET);
    }
    public static void printYellow(String str){
        System.out.print(YELLOW+str+RESET);
    }

    public static void printlnCyan(String str){
        System.out.println(CYAN+str+RESET);
    }
    public static void printCyan(String str){
        System.out.print(CYAN+str+RESET);
    }

    public static void printlnBlack(String str){
        System.out.println(BLACK+str+RESET);
    }
    public static void printBlack(String str){
        System.out.print(BLACK+str+RESET);
    }
    public static void printlnWhite(String str){
        System.out.println(WHITE+str+RESET);
    }
    public static void printWhite(String str){
        System.out.print(WHITE+str+RESET);
    }
}

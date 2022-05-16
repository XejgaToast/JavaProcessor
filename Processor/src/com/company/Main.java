package com.company;


import java.io.IOException;

public class Main {

    private static final int MAX_VALUE = 65535;

    public static void main(String[] args) throws IOException {
        Processor processor = new Processor();
        if(args.length==1) {
            processor.loadFile(args[0]);
        }
        processor.printFile();
    }

    public static char eightBitToChr(boolean[] bits) {
        char chr = 0;
        for(int i=7;i>=0;i--) {
            if(bits[i]) {
                chr += Processor.twoToTwo(7-i);
            }
        }
        return chr;
    }

    public int hexChrToInt(char hex) {
        return switch (hex) {
            case '0' -> 0;
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'a', 'A' -> 10;
            case 'b', 'B' -> 11;
            case 'c', 'C' -> 12;
            case 'd', 'D' -> 13;
            case 'e', 'E' -> 14;
            case 'f', 'F' -> 15;
            default -> throw new RuntimeException("Not a hex character!");
        };
    }
}

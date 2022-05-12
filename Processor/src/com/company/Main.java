package com.company;

public class Main {

    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.add(Processor.StringTo16Bit("0000100110101101"), Processor.StringTo16Bit("0010100010111111"));
        processor.printState();
        processor.and(Processor.StringTo16Bit("0000000110010100"),Processor.StringTo16Bit("0000000010011111"));
        processor.printState();
        processor.or(Processor.StringTo16Bit("0000000110010100"),Processor.StringTo16Bit("0000000010011111"));
        processor.printState();
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

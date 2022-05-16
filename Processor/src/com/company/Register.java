package com.company;

public class Register {
    private boolean[] bits;

    public Register(int size) {
        bits = new boolean[size];
    }

    public boolean[] load() {
        return bits;
    }

    public void write(boolean[] value) {
        bits = value;
    }

    public void printReg() {
        for (int i = 0; i < bits.length; i++) {
            System.out.print(bits[i]);
        }
        System.out.println();
    }
    /* REGISTER CONVENTION
        $0      contains zero
        $1      program counter (PC)
        $2      Upper 16 bits of product (mult operation)
        $3      Lower 16 bits of product (mult operation)

        NOTE: You have the freedom to modify ANY register without restriction, but if you f.e. modify PC it can lead to errors and other unpredictable events
     */
}

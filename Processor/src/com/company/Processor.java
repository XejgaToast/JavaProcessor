package com.company;

import java.util.ArrayList;

public class Processor {
    private ALU[] alus;
    private Register[] regs;

    public Processor() {
        //Easier to work with ArrayList!!
        ArrayList<Register> reg = new ArrayList<>();
        ArrayList<ALU> alu = new ArrayList<>();

        //This is the LAST ALU (we go from right to left!), so there is no ALU after the "first" ALU in the array
        alu.add(new ALU(null));

        this.alus = new ALU[16];        //Processor only supports 16 Bit ALU
        this.regs = new Register[16];   //Processor only supports 16 Registers

        for(int i=0;i<16;i++) {
            reg.add(new Register(16));
            alu.add(new ALU(alu.get(i)));
        }

        alu.remove(16); //We already declared one before the loop, so there is one too much

        this.regs = reg.toArray(regs);
        this.alus = alu.toArray(alus);
        this.alus[15].cIn = false; //The carryIn of first ALU is always false
    }

    public void add(boolean[] a, boolean[] b) {
        if(a.length != 16 | b.length != 16) {
            throw new RuntimeException("This is a 16Bit ALU, 16 Bits are required for signal processing!");
        }
        else {
            //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array!
            for(int i=15;i>=0;i--) {
                this.alus[i].ALUOp = new boolean[]{false,false}; //The ALUOp-Code for adding
                this.alus[i].a = a[i];
                this.alus[i].b = b[i];
                this.alus[i].processSignals();
            }
            if(alus[0].getCOut())
            {
                System.out.println("OVERFLOW!");
            }
        }
    }

    public void and(boolean[] a, boolean[] b) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        if(a.length != 16 | b.length != 16)
        {
            throw new RuntimeException("Array a or b does not have size = 16");
        }
        for(int i=15;i>=0;i--) {
            this.alus[i].a = a[i];
            this.alus[i].b = b[i];
            this.alus[i].ALUOp = new boolean[]{false, true}; //ALUOp-Code for AND operation
            this.alus[i].processSignals();
        }
    }

    public void or(boolean[] a, boolean[] b) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        if(a.length != 16 | b.length != 16)
        {
            throw new RuntimeException("Array a or b does not have size = 16");
        }
        for(int i=15;i>=0;i--) {
            this.alus[i].a = a[i];
            this.alus[i].b = b[i];
            this.alus[i].ALUOp = new boolean[]{true,false}; //ALUOp-Code for OR operation
            this.alus[i].processSignals();
        }
    }

    public void printState() {
        for(int i=0;i<16;i++) {
            if(this.alus[i].getState()) {
                System.out.print('1');
            } else {
                System.out.print('0');
            }
        }
        System.out.println();
    }

    public static boolean[] StringTo16Bit(String binary)
    {
        if(binary.length() != 16) {
            throw new RuntimeException("String size is not equal to 16");
        } else {
            char[] binarychar = binary.toCharArray();
            ArrayList<Boolean> bitsList = new ArrayList<>();
            Boolean[] bitsB = new Boolean[16];
            boolean[] bits = new boolean[16];
            for(int i=0;i<16;i++) {
                bitsList.add(binarychar[i] == '1');
            }
            bitsB = bitsList.toArray(bitsB);
            for(int i=0;i<16;i++) {
                bits[i] = bitsB[i];
            }
            return bits;
        }
    }
}

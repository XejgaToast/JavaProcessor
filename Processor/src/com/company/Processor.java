package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Processor {
    private ALU[] alus;
    private Register[] regs;
    private RAM ram;
    private String[] file;

    public Processor() {
        //Easier to work with ArrayList!!
        ArrayList<Register> reg = new ArrayList<>();
        ArrayList<ALU> alu = new ArrayList<>();

        //This is the LAST ALU (we go from right to left!), so there is no ALU after the "first" ALU in the array
        alu.add(new ALU(null));

        this.alus = new ALU[16];        //Processor only supports 16 Bit ALU
        this.regs = new Register[16];   //Processor only supports 16 Registers
        this.ram = new RAM();

        for (int i = 0; i < 16; i++) {
            reg.add(new Register(16));
            alu.add(new ALU(alu.get(i)));
        }

        alu.remove(16); //We already declared one before the loop, so there is one too much

        this.regs = reg.toArray(regs);
        this.alus = alu.toArray(alus);
        this.alus[15].cIn = false; //The carryIn of first ALU is always false
        this.file = new String[0]; //Standard size is 0 for later error handling
    }

    public void add(int rd, boolean[] a, boolean[] b) {
        if (a.length != 16 | b.length != 16) {
            throw new RuntimeException("This is a 16Bit ALU, 16 Bits are required for signal processing!");
        } else {
            //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array!
            for (int i = 15; i >= 0; i--) {
                this.alus[i].ALUOp = new boolean[]{false, false}; //The ALUOp-Code for adding
                this.alus[i].a = a[i];
                this.alus[i].b = b[i];
                this.alus[i].processSignals();
            }
            if (alus[0].getCOut()) {
                System.out.println("OVERFLOW!");
            }
            ArrayList<Boolean> res = new ArrayList<Boolean>();
            for (int i = 0; i < 16; i++) {
                res.add(alus[i].getState());
            }
            Boolean[] resArr = new Boolean[16];
            regs[rd].write(toPrimitive(res.toArray(resArr)));
        }
    }

    public void add(int rd, int rs, int rt) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array!
        for (int i = 15; i >= 0; i--) {
            this.alus[i].ALUOp = new boolean[]{false, false}; //The ALUOp-Code for adding
            this.alus[i].a = regs[rs].load()[i];
            this.alus[i].b = regs[rt].load()[i];
            this.alus[i].processSignals();
        }
        if (alus[0].getCOut()) {
            System.out.println("OVERFLOW!");
        }
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        for (int i = 0; i < 16; i++) {
            res.add(alus[i].getState());
        }
        Boolean[] resArr = new Boolean[16];
        regs[rd].write(toPrimitive(res.toArray(resArr)));
    }

    public void add(int rd, int rs, boolean[] b) {
        if (b.length != 16) {
            throw new RuntimeException("This is a 16Bit ALU, 16 Bits are required for signal processing!");
        } else {
            //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array!
            for (int i = 15; i >= 0; i--) {
                this.alus[i].ALUOp = new boolean[]{false, false}; //The ALUOp-Code for adding
                this.alus[i].a = regs[rs].load()[i];
                this.alus[i].b = b[i];
                this.alus[i].processSignals();
            }
            if (alus[0].getCOut()) {
                System.out.println("OVERFLOW!");
            }
            ArrayList<Boolean> res = new ArrayList<Boolean>();
            for (int i = 0; i < 16; i++) {
                res.add(alus[i].getState());
            }
            Boolean[] resArr = new Boolean[16];
            regs[rd].write(toPrimitive(res.toArray(resArr)));
        }
    }

    public void and(int rd, boolean[] a, boolean[] b) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        if (a.length != 16 | b.length != 16) {
            throw new RuntimeException("Array a or b does not have size = 16");
        }
        for (int i = 15; i >= 0; i--) {
            this.alus[i].a = a[i];
            this.alus[i].b = b[i];
            this.alus[i].ALUOp = new boolean[]{false, true}; //ALUOp-Code for AND operation
            this.alus[i].processSignals();
        }
        boolean[] res = new boolean[16];
        for (int i = 0; i < 16; i++) {
            res[i] = this.alus[i].getState();
        }
        regs[rd].write(res);
    }

    public void and(int rd, int rs, boolean[] b) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        if (b.length != 16) {
            throw new RuntimeException("Array a or b does not have size = 16");
        }
        for (int i = 15; i >= 0; i--) {
            this.alus[i].a = regs[rs].load()[i];
            this.alus[i].b = b[i];
            this.alus[i].ALUOp = new boolean[]{false, true}; //ALUOp-Code for AND operation
            this.alus[i].processSignals();
        }
        boolean[] res = new boolean[16];
        for (int i = 0; i < 16; i++) {
            res[i] = this.alus[i].getState();
        }
        regs[rd].write(res);
    }

    public void and(int rd, int rs, int rt) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        for (int i = 15; i >= 0; i--) {
            this.alus[i].a = regs[rs].load()[i];
            this.alus[i].b = regs[rs].load()[i];
            this.alus[i].ALUOp = new boolean[]{false, true}; //ALUOp-Code for AND operation
            this.alus[i].processSignals();
        }
        boolean[] res = new boolean[16];
        for (int i = 0; i < 16; i++) {
            res[i] = this.alus[i].getState();
        }
        regs[rd].write(res);
    }

    public void or(int rd, boolean[] a, boolean[] b) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        if (a.length != 16 | b.length != 16) {
            throw new RuntimeException("Array a or b does not have size = 16");
        }
        for (int i = 15; i >= 0; i--) {
            this.alus[i].a = a[i];
            this.alus[i].b = b[i];
            this.alus[i].ALUOp = new boolean[]{true, false}; //ALUOp-Code for OR operation
            this.alus[i].processSignals();
        }
        boolean[] res = new boolean[16];
        for (int i = 0; i < 16; i++) {
            res[i] = this.alus[i].getState();
        }
        regs[rd].write(res);
    }

    public void or(int rd, int rs, boolean[] b) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        if (b.length != 16) {
            throw new RuntimeException("Array a or b does not have size = 16");
        }
        for (int i = 15; i >= 0; i--) {
            this.alus[i].a = regs[rd].load()[i];
            this.alus[i].b = b[i];
            this.alus[i].ALUOp = new boolean[]{true, false}; //ALUOp-Code for OR operation
            this.alus[i].processSignals();
        }
        boolean[] res = new boolean[16];
        for (int i = 0; i < 16; i++) {
            res[i] = this.alus[i].getState();
        }
        regs[rd].write(res);
    }

    public void or(int rd, int rs, int rt) {
        //NOTE: ALUs go from RIGHT to LEFT, so we start with LAST ALU in the array! (Even though it makes no difference in AND operation)
        for (int i = 15; i >= 0; i--) {
            this.alus[i].a = regs[rs].load()[i];
            this.alus[i].b = regs[rt].load()[i];
            this.alus[i].ALUOp = new boolean[]{true, false}; //ALUOp-Code for OR operation
            this.alus[i].processSignals();
        }
        boolean[] res = new boolean[16];
        for (int i = 0; i < 16; i++) {
            res[i] = this.alus[i].getState();
        }
        regs[rd].write(res);
    }

    public void printReg(int reg) {
        boolean[] content = regs[reg].load();
        for (int i = 0; i < 16; i++) {
            if (content[i]) {
                System.out.print('1');
            } else {
                System.out.print('0');
            }
        }
        System.out.println();
    }

    public void printRegDecimal(int reg) {
        int result = 0;
        for (int i = 0; i < regs[reg].load().length; i++) {
            if (regs[reg].load()[regs[reg].load().length - 1 - i]) {
                result += twoToTwo(i);
            }
        }
        System.out.println(result);
    }

    public static int twoToTwo(int num) {
        if (num == 0) {
            return 1;
        } else {
            return 2 * twoToTwo(num - 1);
        }
    }

    public static boolean[] StringTo16Bit(String binary) {                  //Transforms "0000..." to {false,false,....}
        if (binary.length() != 16) {                                        //binary.length has to be 16 so that all components work together, theoretically it can be different
            throw new RuntimeException("String size is not equal to 16");
        } else {
            char[] binarychar = binary.toCharArray();
            ArrayList<Boolean> bitsList = new ArrayList<>();
            Boolean[] bitsB = new Boolean[16];
            boolean[] bits = new boolean[16];
            for (int i = 0; i < 16; i++) {
                bitsList.add(binarychar[i] == '1');
            }
            bitsB = bitsList.toArray(bitsB);

            return toPrimitive(bitsB);
        }
    }

    public static boolean[] toPrimitive(Boolean[] bool) {           //Boolean[] to boolean[]
        boolean[] primitive = new boolean[bool.length];
        for (int i = 0; i < bool.length; i++) {
            primitive[i] = bool[i];
        }
        return primitive;
    }

    public void loadFile(String path) throws IOException {
        String data = "";
        ArrayList<String> lines = new ArrayList<>();
        data = new String(Files.readAllBytes(Paths.get(path)));
        char[] dataArr = data.toCharArray();
        lines.add(new String(""));
        for(int i=0;i<dataArr.length;i++) {
            if(dataArr[i] == 10) {                  //ASCII decimal for "nextline char"
                lines.add(new String(""));  //We begin a new line with an empty String
            } else {
                lines.set(lines.size()-1,lines.get(lines.size()-1)+dataArr[i]); //This changes the current line to what it already contains plus the next char it already read until it finds the "nextline char" (ascii index 10)
            }
        }
        this.file = new String[lines.size()];
        this.file = lines.toArray(file);
    }

    public void printFile() {
        if(file.length == 0 ) {
            throw new RuntimeException("No file was loaded!");
        }
        for(int i=0;i<this.file.length;i++) {
            System.out.println(file[i]);
        }
    }

    public void process() {  //Here is where the magic happens :)

    }
}
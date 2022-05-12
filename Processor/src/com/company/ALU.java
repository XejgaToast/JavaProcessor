package com.company;

public class ALU {
    private ALU postALU;
    public boolean cIn;
    private boolean cOut;
    private boolean state;
    public boolean a;
    public boolean b;
    public boolean[] ALUOp;

    public void processSignals()
    {
        if(!ALUOp[0]& !ALUOp[1])
        {
            this.state = (this.a^this.b)^this.cIn;
            this.cOut = (this.a&this.b) | ((this.a^this.b)&this.cIn);
            if(this.postALU != null) {
                this.postALU.cIn = this.cOut;
            }
        } else if(!ALUOp[0]&ALUOp[1]) {
            this.state = a&b;
        } else if(ALUOp[0] & !ALUOp[1]) {
            this.state = a|b;
        } else if(ALUOp[0]&ALUOp[1]) {
            throw new RuntimeException("ALUOp cannot be 11");
        }
    }
    public ALU(ALU postALU) {
        ALUOp = new boolean[2];
        if(postALU != null)
        {
            this.postALU = postALU;
        }
    }

    public void printState() {
        if(this.state) {
            System.out.println("State: 1");
        } else {
            System.out.println("State: 0");
        }
        if(this.cOut) {
            System.out.println("cOut: 1");
        } else {
            System.out.println("cOut: 0");
        }
    }

    public boolean getState(){return this.state;}
    public boolean getCOut(){return this.cOut;}
}

package com.company;

public class ALU {
    private ALU postALU;
    public boolean cIn;
    private boolean cOut;
    private boolean state;
    public boolean a;
    public boolean b;
    public boolean[] ALUOp;

    public void processSignals() {
        if (!ALUOp[0] & !ALUOp[1]) {                                        //ALUOp for adding
            this.state = (this.a ^ this.b) ^ this.cIn;                      //Simple logic used for a full adder
            this.cOut = (this.a & this.b) | ((this.a ^ this.b) & this.cIn); //
            if (this.postALU != null) {                                     //If postALU is null, we do not have another ALU after it
                this.postALU.cIn = this.cOut;
            }
        } else if (!ALUOp[0] & ALUOp[1]) {                                  //ALUOp for and operation
            this.state = a & b;
        } else if (ALUOp[0] & !ALUOp[1]) {                                  //ALUOp for or operation
            this.state = a | b;
        } else if (ALUOp[0] & ALUOp[1]) {                                   //Free ALUOp cannot be used
            throw new RuntimeException("ALUOp cannot be 11");
        }
    }

    public ALU(ALU postALU) {
        ALUOp = new boolean[2];
        if (postALU != null) {
            this.postALU = postALU;
        }
    }

    public void printState() {              //Some debug stuff not needed, just prints out the current state (output) and the cOut
        if (this.state) {
            System.out.println("State: 1");
        } else {
            System.out.println("State: 0");
        }
        if (this.cOut) {
            System.out.println("cOut: 1");
        } else {
            System.out.println("cOut: 0");
        }
    }

    public boolean getState() {
        return this.state;
    }

    public boolean getCOut() {
        return this.cOut;
    }
}

package com.company;

public class RAM {
    public boolean[] ram;
    public RAM() {
        ram = new boolean[1000000000];
    }
    public void write(int address, boolean[] data) {
        for(int i=address;i<address+data.length;i++) {
            ram[i] = data[i];
        }
    }
    public void load(int address, int amount) {
        boolean[] data = new boolean[amount];

        for(int i=address;i<address+amount;i++) {
            data[i] = ram[i];
        }
    }
}

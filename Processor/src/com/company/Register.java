package com.company;

public class Register {
    private boolean[] bits;

    public Register(int size)
    {
        bits = new boolean[size];
    }
    public boolean[] load()
    {
        return bits;
    }
    public void safe(boolean[] value)
    {
        bits = value;
    }
}

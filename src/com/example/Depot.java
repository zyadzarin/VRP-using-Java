package com.example;

public class Depot extends Location {
    public Depot(Double x, Double y) {
        super(x, y, 0);
    }

    public Depot(int x, int y) {
        this(Double.valueOf(x), Double.valueOf(y));
    }
}

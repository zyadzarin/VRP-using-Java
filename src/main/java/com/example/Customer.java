package com.example;

import java.util.Comparator;

public class Customer extends Location {
    public Customer(Double x, Double y, int demandSize) {
        super(x, y, demandSize);
    }

    public Customer(int x, int y, int demandSize) {
        this(Double.valueOf(x), Double.valueOf(y), demandSize);
    }


}

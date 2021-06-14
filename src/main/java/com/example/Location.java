package com.example;

public class Location {
    protected Coordinate<Double> coordinate;
    protected int demandSize = 0;

    public Location(){}

    public Location(Double x, Double y, int demandSize) {
        this.coordinate = new Coordinate<>(x, y);
        this.demandSize = demandSize;
    }

    public Location(Double x, Double y) { //constructor for depot
        this.coordinate = new Coordinate<Double>(x, y);
    }

    public Location(int x, int y, int demandSize) {
        this(Double.valueOf(x), Double.valueOf(y), demandSize);
    }

    public Coordinate<Double> getCoordinate() {
        return coordinate;
    }

    public int getDemandSize() {
        return demandSize;
    }
}

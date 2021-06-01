package com.example;

public class Location implements Comparable<Location> {
    protected String name;
    protected Coordinate<Double> coordinate;
    protected int demandSize;

    public Location(String name, Double x, Double y, int demandSize) {
        this.name = name;
        this.coordinate = new Coordinate<>(x, y);
        this.demandSize = demandSize;
    }

//    public Location(int x, int y, int demandSize) {
//        this(Double.valueOf(x), Double.valueOf(y), demandSize);
//    }

    public Coordinate<Double> getCoordinate() {
        return coordinate;
    }

    public int getDemandSize() {
        return demandSize;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Location loc) {
        // TODO define
        return 0;
    }
}

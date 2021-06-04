package com.example;

public class Coordinate<T extends Number> {
    protected T x;
    protected T y;

    public Coordinate(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getX() { return x; }

    public void setY(T y) {
        this.y = y;
    }

    public T getY() {
        return y;
    }

    public double distanceTo(Coordinate<T> point2) {
        return euclideanDistance(this, point2);
    }

    public static<T extends Number> double euclideanDistance(Coordinate<T> point1, Coordinate<T> point2) {
        double diffX = point1.getX().doubleValue() - point2.getX().doubleValue();
        double diffY = point1.getY().doubleValue() - point2.getY().doubleValue();

        return Math.sqrt(Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0));
    }
}

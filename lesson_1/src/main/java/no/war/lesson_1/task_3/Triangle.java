package no.war.lesson_1.task_3;

public class Triangle extends Figure {

    private final double a;
    private final double h;

    public Triangle(double a, double h) {
        this.a = a;
        this.h = h;
    }

    @Override
    public double getArea() {
        return 0.5 * a * h;
    }
}

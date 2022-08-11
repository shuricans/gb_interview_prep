package no.war.lesson_1.task_3;

public class Circle extends Figure {

    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}

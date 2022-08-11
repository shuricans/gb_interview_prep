package no.war.lesson_1.task_3;

public class Square extends Figure {

    private final double a;

    public Square(double a) {
        this.a = a;
    }

    @Override
    public double getArea() {
        return Math.pow(a, 2);
    }
}

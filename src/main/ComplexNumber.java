package main;

/**
 * Created by Kyle on 6/3/2017.
 */
public class ComplexNumber {
    private double realComponent;
    private double imaginaryCoefficient;

    public ComplexNumber(double realComponent, double imaginaryCoefficient) {
        this.realComponent = realComponent;
        this.imaginaryCoefficient = imaginaryCoefficient;
    }

    public double getRealComponent() {
        return realComponent;
    }

    public double getImaginaryCoefficient() {
        return imaginaryCoefficient;
    }

    public int getJuliaValue() {
        if (hasLengthLongerThanTwo()) {
            return 0;
        }
        return juliaValueHelper(1, new ComplexNumber(realComponent, imaginaryCoefficient));
    }

    public void square() {
        double first = realComponent * realComponent;
        double middle = realComponent * imaginaryCoefficient * 2;
        double last = -1 * imaginaryCoefficient * imaginaryCoefficient;
        realComponent = first + last;
        imaginaryCoefficient = middle;
    }

    private boolean hasLengthLongerThanTwo() {
        return realComponent * realComponent + imaginaryCoefficient * imaginaryCoefficient > 4;
    }

    private static int juliaValueHelper(int counter, ComplexNumber z) {
        z.square();
        z.add(Main.JULIA_CONSTANT);
        if (z.hasLengthLongerThanTwo() || counter == Main.ITERATIONS) {
            return counter;
        }
        return juliaValueHelper(counter + 1, z);
    }

    public void add(ComplexNumber numberToAdd) {
        realComponent = realComponent + numberToAdd.realComponent;
        imaginaryCoefficient = imaginaryCoefficient + numberToAdd.imaginaryCoefficient;
    }

    public void setRealComponent(double realComponent) {
        this.realComponent = realComponent;
    }

    public void setImaginaryCoefficient(double imaginaryCoefficient) {
        this.imaginaryCoefficient = imaginaryCoefficient;
    }

    public ComplexNumber clone() {
        return new ComplexNumber(realComponent, imaginaryCoefficient);
    }

    public int getMandelbrotValue() {
        if (hasLengthLongerThanTwo()) {
            return 0;
        }
        return mandelbrotValueHelper(1, new ComplexNumber(realComponent, imaginaryCoefficient), this);
    }

    private static int mandelbrotValueHelper(int counter, ComplexNumber z, ComplexNumber c) {
        z.square();
        z.add(c);
        if (z.hasLengthLongerThanTwo() || counter == Main.MANDELBROT_ITERATIONS) {
            return counter;
        }
        return mandelbrotValueHelper(counter + 1, z, c);
    }

    public int getMandelbrotValueSingleCheck() {
        if (hasLengthLongerThanTwo()) {
            return 0;
        }
        return mandelbrotValueSingleCheckHelper(1, new ComplexNumber(realComponent, imaginaryCoefficient), this);
    }

    private static int mandelbrotValueSingleCheckHelper(int counter, ComplexNumber z, ComplexNumber c) {
        z.square();
        z.add(c);
        if (z.hasLengthLongerThanTwo() || counter == Main.MANDELBROT_ITERATIONS_SINGLE_CHECK) {
            return counter;
        }
        return mandelbrotValueSingleCheckHelper(counter + 1, z, c);
    }
}

package model;

public class Number {

    private long numerator;
    private long denominator;

    public Number(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    public Number(Number number) {
        this(number.numerator, number.denominator);
    }

    public Number(int number) {
        this(number, 1);
    }

    public Number(Integer number) {
        this(number, 1);
    }

    public Number(long number) {
        this(number, 1);
    }

    public Number(double number) {
        this(fromDouble(number));
    }

    public static Number fromDouble(double number) {
        long denominator = 1;
        while ((long) number != number) {
            number *= 10;
            denominator *= 10;
        }
        return new Number((long) number, denominator);

    }

    public static Number valueOf(String amount) {
        return fromDouble(Double.valueOf(amount));
    }

    private int[] getPrimeNumbers() {
        return new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
    }

    private boolean isDivisible(int number) {
        return ((numerator % number == 0) && (denominator % number == 0));
    }

    private void reduce() {
        int[] primeNumber = getPrimeNumbers();
        for (int number : primeNumber) {
            if (numerator < number) {
                break;
            }
            if (denominator < number) {
                break;
            }
            reduce(number);
        }
    }

    private void reduce(int number) {
        while (isDivisible(number)) {
            numerator /= number;
            denominator /= number;
        }
    }

    private void divide(int divisor) {
        while (numerator % divisor == 0 && denominator % divisor == 0) {
            numerator /= divisor;
            denominator /= divisor;
        }
    }

    public static Number multiply(Number number1, Number number2) {
        Number result;
        result = new Number(number1.numerator * number2.numerator, number1.denominator * number2.denominator);
        return result;
    }

    @Override
    public String toString() {
        long number = numerator / denominator;
        return "" + number;
    }

    @Override
    public boolean equals(Object number) {
        if (number == null || getClass() != number.getClass()) {
            return false;
        }

        final Number other = (Number) number;

        if (this.numerator != other.numerator) {
            return false;
        }
        if (this.denominator != other.denominator) {
            return false;
        }
        return true;
    }
}

package org.example;

//两个真分数的四则运算，如果是整数/假分数，先转成真分数
public class Fraction {
    private int numerator; // 分子
    private int denominator; // 分母

    // 构造器,number:
    public Fraction(int[] numbers) {
        int len = numbers.length;
        switch (len) {
            case 1://整数
                pre(numbers[0], 1);
                break;
            case 2://真分数
                pre(numbers[0], numbers[1]);
                break;
            case 3://假分数
                pre(numbers[1] + numbers[2] * numbers[0], numbers[2]);
                break;
        }
    }

    //numerator:分子 denominator分母
    public void pre(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        simplify(); // 初始化时简化分数
    }

    // 简化分数
    private void simplify() {
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        numerator /= gcd;
        denominator /= gcd;
        // 确保分母为正
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    // 最大公约数算法（欧几里得算法）
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // 加法
    public Fraction add(Fraction other) {
        int newNumerator = numerator * other.denominator + other.numerator * denominator;
        int newDenominator = denominator * other.denominator;
        return new Fraction(new int[]{newNumerator, newDenominator});
    }

    // 减法
    public Fraction subtract(Fraction other) {
        int newNumerator = numerator * other.denominator - other.numerator * denominator;
        int newDenominator = denominator * other.denominator;
        return new Fraction(new int[]{newNumerator, newDenominator});
    }

    // 乘法
    public Fraction multiply(Fraction other) {
        int newNumerator = numerator * other.numerator;
        int newDenominator = denominator * other.denominator;
        return new Fraction(new int[]{newNumerator, newDenominator});
    }

    // 除法
    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Division by zero.");
        }
        int newNumerator = numerator * other.denominator;
        int newDenominator = denominator * other.numerator;
        return new Fraction(new int[]{newNumerator, newDenominator});
    }

    public boolean compare(Fraction other) {
        Fraction fraction = subtract(other);
        return fraction.numerator >= 0;
    }

    // 重写toString方法，方便输出
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    // 测试
    public static void main(String[] args) {
        Fraction f1 = new Fraction(new int[]{2, 1, 6});
        Fraction f2 = new Fraction(new int[]{3, 1, 3});
        System.out.println(f1);
        System.out.println("f1 + f2 = " + f1.add(f2));
        System.out.println("f1 - f2 = " + f1.subtract(f2));
        System.out.println("f1 * f2 = " + f1.multiply(f2));
        System.out.println("f1 / f2 = " + f1.divide(f2));
    }
}
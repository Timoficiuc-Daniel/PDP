package org.example;

import org.example.Multiplication;
import org.example.Polynomial;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        Polynomial x = new Polynomial(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)); // x^9 + 2x^8 + 3x^7 + 4x^6 + 5x^5 + 6x^4 + 7x^3 + 8x^2 + 9x + 10
//        Polynomial y = new Polynomial(Arrays.asList(-8, 7, -6, 5, -4, 3, -2, 1)); // -8x^7 + 7x^6 - 6x^5 + 5x^4 - 4x^3 + 3x^2 - 2x + 1
        // -8x^16 - 9x^15 - 16x^14 - 18x^13  -24x^12 - 27x^11 - 32x^10 - 36x^9 - 40x^8 - 44x^7 + 40x^6 - 33x^5 + 30x^4 - 22x^3 + 20x^2 - 11x + 10
        Polynomial x = Polynomial.getLargePolynomial(30000);
        Polynomial y = Polynomial.getLargePolynomial(30000);
        long time = System.currentTimeMillis();
        Multiplication.regularSequentialMultiplication(x, y);
        System.out.println(System.currentTimeMillis() - time + "ms");
        time = System.currentTimeMillis();
        Multiplication.regularParallelMultiplication(x, y);
        System.out.println(System.currentTimeMillis() - time + "ms");
        time = System.currentTimeMillis();
        Multiplication.karatsubaSequentialMultiplication(x, y);
        System.out.println(System.currentTimeMillis() - time + "ms");
        Multiplication a = new Multiplication();
        time = System.currentTimeMillis();
        a.karatsubaGood(x,y);
        System.out.println(System.currentTimeMillis() - time + "ms");
    }
}
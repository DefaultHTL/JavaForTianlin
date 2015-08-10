package org.tianlin.java.exercise1.question5;

import java.math.BigInteger;

public class SumOfFactorial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SumOfFactorial s = new SumOfFactorial();
		System.out.println(s.sumOfFactorial(100));
	}

	public BigInteger sumOfFactorial(int n) {
		BigInteger sum = BigInteger.ZERO;
		BigInteger num = BigInteger.ONE;
		for (int i = 1; i <= n; i++) {
			num = num.multiply(BigInteger.valueOf(i));
			sum = sum.add(num);
		}
		return sum;
	}
}

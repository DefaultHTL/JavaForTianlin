package org.tianlin.java.exercise1.question3;

import java.math.BigInteger;

public class Compute {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Compute c = new Compute();
		System.out.println(c.compute(120, 5));
	}

	public BigInteger compute(int n, int a) {
		BigInteger num = BigInteger.ZERO, sum = BigInteger.ZERO;
		for (int i = 0; i < n; i++) {
			num = num.multiply(BigInteger.valueOf(10)).add(
					BigInteger.valueOf(a));
			sum = sum.add(num);
		}
		return sum;
	}

}

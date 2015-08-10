package org.tianlin.java.lesson2.question2;

public class GCD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GCD gcd = new GCD();
		System.out.println(gcd.gcd(95, 25));
	}

	public int gcd(int a, int b) {
		if (a < b) {
			int c = a;
			a = b;
			b = c;
		}

		int m = a % b;
		return m == 0 ? b : gcd(b, m);
	}

}

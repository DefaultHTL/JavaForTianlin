package org.tianlin.java.exercise1.question10;

import java.util.Scanner;

public class SumOfPrime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		String next = scanner.next();
		SumOfPrime s = new SumOfPrime();
		s.sumofPrime(Integer.parseInt(next));
		// System.out.println(s.isPrime(Integer.parseInt(next)));
		scanner.close();
	}

	public void sumofPrime(int num) {
		if (num % 2 != 0) {
			System.out.println("必须是个大于2的偶数！");
			return;
		}

		for (int i = 2; i <= num / 2; i++) {
			if (isPrime(i) && isPrime(num - i)) {
				System.out.println(num + "=" + i + "+" + (num - i));
			}
		}
	}

	private boolean isPrime(int num) {
		if (num < 2) {
			return false;
		}

		int root = (int) Math.sqrt(num);
		for (int i = 2; i <= root; i++) {
			if (num % i == 0) {
				return false;
			}
		}

		return true;
	}

}

package org.tianlin.java.exercise1.question2;

import java.util.Scanner;

public class CountAlphaNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);
		String string = scanner.next();
		int i = 0, j = 0, k = 0;
		char c = 0;
		for (int n = 0; n < string.length(); n++) {
			c = string.charAt(n);
			if (c >= 0x30 && c <= 0x39) {
				i++;
			} else if ((c >= 0x41 && c <= 0x5A) || (c >= 61 && c <= 0x7A)) {
				j++;
			} else {
				k++;
			}
		}
		System.out.println("Numbers: " + i + ", alphabets: " + j + ", others: "
				+ k + " in " + string);
		scanner.close();
	}
}

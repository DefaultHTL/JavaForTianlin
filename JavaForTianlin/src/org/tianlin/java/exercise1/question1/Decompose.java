package org.tianlin.java.exercise1.question1;

public class Decompose {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Decompose decompose = new Decompose();
		System.out.println(decompose.decompose(1234567));
	}

	public String decompose(int num) {
		StringBuilder builder = new StringBuilder();
		builder.append(num).append('=');
		int prime = 2;
		while (num > 1) {
			while (num % prime == 0) {
				num = num / prime;
				builder.append(prime).append('*');
			}
			prime++;
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
}

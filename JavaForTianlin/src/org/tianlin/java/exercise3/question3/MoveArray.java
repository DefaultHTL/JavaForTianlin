package org.tianlin.java.exercise3.question3;

import java.util.Arrays;

public class MoveArray {
	private static int move = 3;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Integer[] b = new Integer[a.length];
		int index = 0;
		for (int i = move; i < a.length; i++) {
			b[index++] = a[i];
		}

		for (int i = 0; i < move; i++) {
			b[index++] = a[i];
		}

		System.out.println(Arrays.toString(b));
	}

}

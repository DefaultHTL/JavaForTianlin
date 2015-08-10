package org.tianlin.java.exercise3.question2;

import java.util.Scanner;

public class MaxMin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i, min, max, n, temp1, temp2;
		int a[];
		System.out.println("输入数组的长度:");
		Scanner keyboard = new Scanner(System.in);
		n = keyboard.nextInt();
		a = new int[n];
		for (i = 0; i < n; i++) {
			System.out.print("输入第" + (i + 1) + "个数");
			a[i] = keyboard.nextInt();
		}
		keyboard.close();
		max = 0;
		min = 0;
		for (i = 1; i < n; i++) {
			if (a[i] > a[max])
				max = i;
			if (a[i] < a[min])
				min = i;
		}
		temp1 = a[0];
		temp2 = a[min];

		a[0] = a[max];
		a[max] = temp1;

		if (min != 0) {
			a[min] = a[n - 1];
			a[n - 1] = temp2;
		} else {
			a[max] = a[n - 1];
			a[n - 1] = temp1;
		}

		for (i = 0; i < n; i++) {
			System.out.print(a[i] + " ");
		}

	}

}

package org.tianlin.java.exercise3.question6;

import java.util.Scanner;

public class Guess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int gn = (int) (Math.random() * 100);
		int input = 0;
		Scanner scan = new Scanner(System.in);
		System.out.println("输入你猜的数");
		while (true) {
			input = scan.nextInt();
			if (input == gn) {
				System.out.println("猜的数是 " + input);
				break;
			} else if (input > gn) {
				System.out.println("大了");
			} else {
				System.out.println("小了");
			}
		}
		scan.close();
	}

}

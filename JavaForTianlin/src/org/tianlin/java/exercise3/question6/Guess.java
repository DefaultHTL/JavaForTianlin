package org.tianlin.java.exercise3.question6;

import java.util.Scanner;

public class Guess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int gn = (int) (Math.random() * 100);
		int input = 0;
		Scanner scan = new Scanner(System.in);
		System.out.println("������µ���");
		while (true) {
			input = scan.nextInt();
			if (input == gn) {
				System.out.println("�µ����� " + input);
				break;
			} else if (input > gn) {
				System.out.println("����");
			} else {
				System.out.println("С��");
			}
		}
		scan.close();
	}

}

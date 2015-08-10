package org.tianlin.java.exercise6.examples;

import java.util.Scanner;

public class PromptExample {
	Scanner scanner = null;

	public PromptExample() {
		scanner = new Scanner(System.in);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PromptExample example = new PromptExample();
		example.start();
		example.stop();
	}

	public void start() {
		System.out.println("请输入：1.xxx；2.xxx；3.xxx");
		switch (scanner.nextInt()) {
		case 1:
			level1method1();
			break;
		case 2:
			level1method2();
			break;
		case 3:
			level1method3();
			break;
		}
	}

	public void stop() {
		scanner.close();
	}

	private void level1method1() {
		/*
		 * 做该做的事情
		 */

		System.out.println("请输入：1.xxx；2.xxx；3.xxx");
		switch (scanner.nextInt()) {
		case 1:
			level2method1();
			break;
		case 2:
			level2method2();
			break;
		case 3:
			level2method3();
			break;
		}
	}

	private void level1method2() {

	}

	private void level1method3() {

	}

	private void level2method1() {

	}

	private void level2method2() {

	}

	private void level2method3() {

	}
}

package org.tianlin.java.lesson2.question1;

public class Monkey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Monkey monkey = new Monkey();
		System.out.println(monkey.peachCountEveryDay(10));
	}

	public int peachCountEveryDay(int day) {
		return day == 1 ? 1 : 2 * (1 + peachCountEveryDay(day - 1));
	}
}

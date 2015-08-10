package org.tianlin.java.exercise5.question1;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 0; i < 7; i++) {
			Thread t = new Thread(new Washer("Washer" + i));
			t.start();
		}
	}

}

package org.tianlin.java.lesson2.question5;

public class Stairs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stairs s = new Stairs();
		System.out.println(s.upStairs(10));
	}

	public int upStairs(int stairs) {
		if (stairs == 2)
			return 2;
		else if (stairs == 1)
			return 1;
		else
			return upStairs(stairs - 2) + upStairs(stairs - 1);
	}
}

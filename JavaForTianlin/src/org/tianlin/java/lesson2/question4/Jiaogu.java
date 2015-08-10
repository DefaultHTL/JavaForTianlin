package org.tianlin.java.lesson2.question4;

public class Jiaogu {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jiaogu jiaogu = new Jiaogu();
		System.out.println(jiaogu.jiaogu(123454321));
	}

	public int jiaogu(int num) {
		if (num == 1)
			return 0;
		else
			return num % 2 == 0 ? 1 + jiaogu(num / 2) : 1 + jiaogu(3 * num + 1);
	}
}

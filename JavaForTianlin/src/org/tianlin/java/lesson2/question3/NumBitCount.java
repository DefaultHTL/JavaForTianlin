package org.tianlin.java.lesson2.question3;

public class NumBitCount {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NumBitCount bitCount = new NumBitCount();
		System.out.println(bitCount.bitCount(123454321));
	}

	public int bitCount(int number) {
		return number > 0 ? number % 10 + bitCount(number / 10) : 0;
	}
}

package org.tianlin.java.exercise1.question9;

import java.util.LinkedList;
import java.util.List;

public class Exit3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Exit3 e = new Exit3();
		for (int i = 1; i <= 10; i++)
			System.out.println(e.exit3(i));
	}

	public int exit3(int num) {
		List<Integer> nums = new LinkedList<Integer>();
		for (int i = 1; i <= num; i++) {
			nums.add(i);
		}

		int i = 0, count = 1;
		while (nums.size() > 1) {
			if (count == 3) {
				count = 1;
				nums.remove(i);
			} else {
				count++;
				i++;
			}
			if (i == nums.size()) {
				i = 0;
			}
		}

		return nums.get(0);
	}

}

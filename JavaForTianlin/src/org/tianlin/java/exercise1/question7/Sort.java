package org.tianlin.java.exercise1.question7;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> numbers = new LinkedList<Integer>();
		Scanner scanner = new Scanner(System.in);
		String next = null;
		while (!"OK".equals(next = scanner.next())) {
			numbers.add(new Integer(next));
		}
		Collections.sort(numbers);
		for (Integer integer : numbers) {
			System.out.print(integer + " ");
		}
		scanner.close();
	}

}

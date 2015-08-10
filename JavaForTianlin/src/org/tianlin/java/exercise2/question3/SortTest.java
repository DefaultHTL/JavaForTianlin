package org.tianlin.java.exercise2.question3;

public class SortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		{// Straight Insertion Sort
			int[] numbers = new int[] { 1, 5, 7, 3, 9, 6, 2, 4, 8 };
			System.out.println("Straight Insertion Sort: ");
			System.out.print("Before: ");
			for (int i : numbers) {
				System.out.print(i + " ");
			}
			System.out.println();
			Sort.straightInsertionSort(numbers);
			System.out.print("After: ");
			for (int i : numbers) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
		}
		
		{// Simple Selection Sort
			int[] numbers = new int[] { 1, 5, 7, 3, 9, 6, 2, 4, 8 };
			System.out.println("Simple Selection Sort: ");
			System.out.print("Before: ");
			for (int i : numbers) {
				System.out.print(i + " ");
			}
			System.out.println();
			Sort.simpleSelectionSort(numbers);
			System.out.print("After: ");
			for (int i : numbers) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
		}
		
		{// Quick Sort
			int[] numbers = new int[] { 1, 5, 7, 3, 9, 6, 2, 4, 8 };
			System.out.println("Quick Sort: ");
			System.out.print("Before: ");
			for (int i : numbers) {
				System.out.print(i + " ");
			}
			System.out.println();
			Sort.quickSort(numbers);
			System.out.print("After: ");
			for (int i : numbers) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
		}
	}

}

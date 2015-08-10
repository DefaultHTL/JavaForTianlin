package org.tianlin.java.exercise2.question3;

public class Sort {
	public static void straightInsertionSort(int[] nums) {
		if (nums != null && nums.length > 1) {
			int j = 0, t = 0;
			for (int i = 1; i < nums.length; i++) {
				j = i;
				while (j > 0 && nums[j] < nums[j - 1]) {
					t = nums[j - 1];
					nums[j - 1] = nums[j];
					nums[j] = t;
					j--;
				}
			}
		}
	}

	public static void simpleSelectionSort(int[] nums) {
		if (nums != null && nums.length > 1) {
			int minIndex = 0, t = 0;
			for (int i = 0; i < nums.length - 1; i++) {
				minIndex = i;
				for (int j = i + 1; j < nums.length; j++) {
					if (nums[j] < nums[minIndex]) {
						minIndex = j;
					}
				}
				if (minIndex != i) {
					t = nums[minIndex];
					nums[minIndex] = nums[i];
					nums[i] = t;
				}
			}
		}
	}

	public static void quickSort(int[] nums) {
		if (nums != null && nums.length > 1) {
			doQuickSort(nums, 0, nums.length - 1);
		}
	}

	private static void doQuickSort(int[] nums, int b, int e) {
		if (b < e) {
			int l = b + 1, h = e, t = 0;
			while (l < h) {
				if (nums[l] < nums[b]) {
					l++;
				} else {
					t = nums[l];
					nums[l] = nums[h];
					nums[h] = t;
					h--;
				}
			}
			if (nums[l] >= nums[b]) {
				l--;
			}
			if (b != l) {
				t = nums[l];
				nums[l] = nums[b];
				nums[b] = t;
			}
			doQuickSort(nums, b, l - 1);
			doQuickSort(nums, l + 1, e);
		}
	}
}

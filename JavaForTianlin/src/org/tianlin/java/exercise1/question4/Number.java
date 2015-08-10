package org.tianlin.java.exercise1.question4;

public class Number {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 1; i <= 5; i++)
			for (int j = 1; j <= 5; j++)
				for (int k = 1; k <= 5; k++)
					if (i != j && j != k && k != i)
						System.out.println(i * 100 + j * 10 + k);
	}

}

package org.tianlin.java.exercise3.question1;

import java.util.Scanner;

public class DayOfYear {
	public static void main(String[] args)
	{
		int count = 0;
		Scanner in = new Scanner(System.in);

		System.out.println("输入年:");
		int year = in.nextInt();

		System.out.println("输入月:");
		int month = in.nextInt();

		System.out.println("输入日:");
		int day = in.nextInt();
		
		in.close();

		switch (month) {
		case 12:
			count += 30;
		case 11:
			count += 31;
		case 10:
			count += 30;
		case 9:
			count += 31;
		case 8:
			count += 30;
		case 7:
			count += 31;
		case 6:
			count += 31;
		case 5:
			count += 30;
		case 4:
			count += 31;
		case 3:
			count += 28;
		case 2:
			count += 31;
		case 1:
			count += 0;

		}
		count += day;
		if (year % 4 == 0 && year % 100 != 0 && month >= 3)
		{
			count += 1;
		}

		System.out.print(year + "-" + month + "-" + day + "是一年中的第" + count + "天");
	}
}

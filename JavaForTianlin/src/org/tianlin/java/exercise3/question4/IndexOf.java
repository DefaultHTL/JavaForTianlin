package org.tianlin.java.exercise3.question4;

public class IndexOf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(indexOf("hello world", "o"));
		System.out.println(indexOf("hello world", "o w"));
		System.out.println(indexOf("hello world", "orld"));
		System.out.println(indexOf("hello world", "oele"));
		System.out.println(indexOf("hello world", "orldd"));
	}

	public static int indexOf(String s, String p) {
		if (p == null || p.length() == 0 || s == null || s.length() == 0)
			return -1;

		int si = 0, pi = 0, i = 0;
		while (si < s.length() && pi < p.length()) {
			if (s.charAt(si) == p.charAt(pi)) {
				si++;
				pi++;
			} else {
				i++;
				si = i;
				pi = 0;
			}
		}

		if (pi == p.length())
			return i;
		else
			return -1;
	}
}

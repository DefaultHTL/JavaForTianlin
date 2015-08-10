package org.tianlin.java.exercise4.question1;

public enum Quadrant {
	one, two, three, four;
	public static Quadrant getQuadrant(double x, double y) {
		if (x > 0 && y > 0) {
			return one;
		} else if (x < 0 && y > 0) {
			return two;
		} else if (x < 0 && y < 0) {
			return three;
		} else {
			return four;
		}
	}
}

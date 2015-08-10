package org.tianlin.java.exercise4.question1;

public class Point {
	private double x;
	private double y;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;

	}

	@Override
	public boolean equals(Object p) {
		if (p instanceof Point) {
			Point p1 = (Point) p;
			return p1.x == x && p1.y == y;
		} else {
			return false;
		}
	}

	public static double getDistance(Point p1, Point p2) {
		if (p1 == null || p2 == null) {
			throw new IllegalArgumentException("Point is null!");
		}

		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y));
	}

	public Quadrant getQuadrant() {
		return Quadrant.getQuadrant(x, y);
	}
}

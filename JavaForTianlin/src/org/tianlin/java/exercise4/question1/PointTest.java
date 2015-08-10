package org.tianlin.java.exercise4.question1;

public class PointTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point point = new Point(3, 2);
		Point p2 = new Point();
		System.out.println(point.getX());
		System.out.println(point.equals(new Point(2, 2)));
		System.out.println(point.getQuadrant());
		System.out.println(Point.getDistance(point, p2));
		System.out.println(null instanceof Point);
	}

}

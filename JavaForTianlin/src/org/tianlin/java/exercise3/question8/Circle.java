package org.tianlin.java.exercise3.question8;

public class Circle {
	private static final double PI = 3.14; // Math.PI
	private double radius;

	public Circle() {
		this.radius = 0;
	}

	public Circle(double r) {
		this.radius = r;
	}

	public double getArea() {
		return radius * radius * PI;
	}

	public double getPerimeter() {
		return 2.0 * radius * PI;
	}

	public void show() {
		System.out.println("圆的半径" + this.radius);
		System.out.println("圆的面积" + this.getArea());
		System.out.println("圆的周长" + this.getPerimeter());
	}

}

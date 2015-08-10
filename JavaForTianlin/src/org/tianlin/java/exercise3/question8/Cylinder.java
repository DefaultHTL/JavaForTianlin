package org.tianlin.java.exercise3.question8;

public class Cylinder extends Circle {
	private double height;

	public Cylinder(double radius, double height) {
		super(radius);
		this.height = height;
	}

	public double getVolume() {
		return this.getArea() * this.height;
	}

	public void showVolume() {
		System.out.println("Ô²ÖùµÄÌå»ı" + this.getVolume());
	}

}

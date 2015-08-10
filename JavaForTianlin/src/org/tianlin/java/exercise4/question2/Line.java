package org.tianlin.java.exercise4.question2;

import org.tianlin.java.exercise4.question1.Point;

public class Line {
	private double k;
	private double b;

	public Line(double k, double b) {
		this.k = k;
		this.b = b;
	}

	public Line(double k, Point p) {
		this.k = k;
		this.b = p.getY() - k * p.getX();
	}

	public Line(Point p1, Point p2) {
		this.k = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
		this.b = p1.getY() - this.k * p1.getX();
	}

	public double getK() {
		return k;
	}

	public double getYb() {
		return b;
	}

	public double getXb() {
		return (-b) / k;
	}

	@Override
	public boolean equals(Object l) {
		if (l instanceof Line) {
			Line l1 = (Line) l;
			return l1.k == k && l1.b == b;
		} else {
			return false;
		}

	}

	public boolean isParallel(Line l) {
		if (this.k == l.k) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isVertical(Line l) {
		if (this.k == -(1 / l.k)) {
			return true;
		} else {
			return false;
		}
	}

	public double getDistanceFromPoint(Point p) {
		return Math.abs(this.k * p.getX() - p.getY() + this.b)
				/ Math.sqrt(this.k * this.k + 1);
	}

	public boolean isPointOnLine(Point p) {
		if (p.getY() == this.k * p.getX() + this.b) {
			return true;
		} else {
			return false;
		}
	}
}

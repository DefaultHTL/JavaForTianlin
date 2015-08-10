package org.tianlin.java.exercise4.question3;

import java.util.LinkedList;
import java.util.List;

import org.tianlin.java.exercise4.question1.Point;

public class NotParallelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Point a;
	Point b;
	Point c;
	Point d;

	private void setPoints(Point a, Point b, Point c, Point d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public NotParallelException(Point a, Point b, Point c, Point d) {
		super();
		setPoints(a, b, c, d);
	}

	public NotParallelException(Point a, Point b, Point c, Point d,
			String message) {
		super(message);
		setPoints(a, b, c, d);
	}

	public NotParallelException(Point a, Point b, Point c, Point d,
			Throwable throwable) {
		super(throwable);
		setPoints(a, b, c, d);
	}

	public NotParallelException(Point a, Point b, Point c, Point d,
			String message, Throwable throwable) {
		super(message, throwable);
		setPoints(a, b, c, d);
	}

	public List<Point> getErorPoints() {
		List<Point> points = new LinkedList<Point>();
		points.add(a);
		points.add(b);
		points.add(c);
		points.add(d);
		return points;
	}
}

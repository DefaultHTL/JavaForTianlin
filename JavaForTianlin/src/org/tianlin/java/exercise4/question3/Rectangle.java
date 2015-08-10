package org.tianlin.java.exercise4.question3;

import org.tianlin.java.exercise4.question1.Point;
import org.tianlin.java.exercise4.question2.Line;

public class Rectangle extends Parallelogram {

	public Rectangle(Point a, Point b, Point c, Point d)
			throws NotParallelException {
		super(a, b, c, d);
		// TODO Auto-generated constructor stub

		Line ab = new Line(a, b);
		Line bc = new Line(b, c);

		if (!ab.isVertical(bc))
			throw new NotRectangleException(a, b, c, d,
					"These four cannot construct a rectangle!");
	}

	public double getCircumcircleRadius() {
		return Point.getDistance(a, c) / 2;
	}
}

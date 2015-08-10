package org.tianlin.java.exercise4.question3;

import org.tianlin.java.exercise4.question1.Point;
import org.tianlin.java.exercise4.question2.Line;

public class Parallelogram {
	Point a;
	Point b;
	Point c;
	Point d;

	public Parallelogram(Point a, Point b, Point c, Point d)
			throws NotParallelException {
		Line ab = new Line(a, b);
		Line cd = new Line(c, d);
		Line ad = new Line(a, d);
		Line bc = new Line(b, c);

		if (!ab.isParallel(cd) || !ad.isParallel(bc))
			throw new NotParallelException(a, b, c, d,
					"These four points can not construct a parallelogram!");
	}

	public double getArea() {
		double bottom = Point.getDistance(a, c);
		Line ac = new Line(a, c);
		double height = ac.getDistanceFromPoint(b);
		return bottom * height;
	}
}

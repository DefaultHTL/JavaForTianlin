package org.tianlin.java.exercise4.question3;

import org.tianlin.java.exercise4.question1.Point;

public class NotRectangleException extends NotParallelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotRectangleException(Point a, Point b, Point c, Point d,
			String message) {
		super(a, b, c, d, message);
		// TODO Auto-generated constructor stub
	}

}

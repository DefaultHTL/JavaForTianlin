package org.tianlin.java.exercise2.question1;

public class Ball {
	private static final String DEFAULT_MODEL = "M54";

	private int id;
	private String model;

	public Ball() {
		id = 1;
		model = DEFAULT_MODEL;
	}

	public Ball(int id, String model) {
		this.id = id;
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public String getModel() {
		return model;
	}

	public String showIdAndModel() {
		return id + "-" + model;
	}
}

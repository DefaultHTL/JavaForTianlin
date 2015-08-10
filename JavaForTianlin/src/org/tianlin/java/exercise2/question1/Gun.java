package org.tianlin.java.exercise2.question1;

public class Gun {
	private static final String DEFAULT_MODEL = "M54";
	private static final int DEFAULT_MAXBALLCOUNT = 6;

	private int id;
	private String model;
	// TODO 在此添加弹夹

	private int maxBallCount;

	public Gun() {
		id = 1;
		model = DEFAULT_MODEL;
		maxBallCount = DEFAULT_MAXBALLCOUNT;
	}

	public Gun(int id, String model, int maxBallCount) {
		this.id = id;
		this.model = model;
		this.maxBallCount = maxBallCount;
	}
	
	public void reload(Ball ball){
		// TODO 在此装弹
		// 抛出异常：throw new Exception("弹夹已满！");
	}
	
	public Ball shoot(){
		// TODO 在此射击
		// 抛出异常：throw new Exception("弹夹为空！");
		
		return null;
	}
	
	public void showBallInfo(){
		// TODO 在此添加显示弹夹信息
	}
	
	public int currentBallCount(){
		// TODO 在此返回当前弹夹子弹数目
		return 0;
	}

	public int getMaxBallCount() {
		return maxBallCount;
	}

	public String getModel() {
		return model;
	}

	public int getId() {
		return id;
	}
}

package org.tianlin.java.exercise2.question1;

public class Gun {
	private static final String DEFAULT_MODEL = "M54";
	private static final int DEFAULT_MAXBALLCOUNT = 6;

	private int id;
	private String model;
	// TODO �ڴ���ӵ���

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
		// TODO �ڴ�װ��
		// �׳��쳣��throw new Exception("����������");
	}
	
	public Ball shoot(){
		// TODO �ڴ����
		// �׳��쳣��throw new Exception("����Ϊ�գ�");
		
		return null;
	}
	
	public void showBallInfo(){
		// TODO �ڴ������ʾ������Ϣ
	}
	
	public int currentBallCount(){
		// TODO �ڴ˷��ص�ǰ�����ӵ���Ŀ
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

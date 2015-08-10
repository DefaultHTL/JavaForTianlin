package org.tianlin.java.exercise3.question7;

public class MyTime {
	private int hour; 
	private int minute; 
	private int second; 

	public MyTime() {
	}

	public MyTime(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public void diaplay(MyTime time) {
		System.out.println(time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public void addSecond(int sec) {
		this.second = second + sec;
	}

	public void addMinute(int min) {
		this.minute = minute + min;
	}

	public void addHour(int hou) {
		this.hour = hour + hou;
	}

	public void subSecond(int sec) {
		this.second = second - sec;
	}

	public void subMinute(int min) {
		this.minute = minute - min;
	}

	public void subHour(int hou) {
		this.hour = hour - hou;
	}

	public static void main(String[] args) {

		MyTime time = new MyTime(20, 14, 5);
		time.diaplay(time);
		time.addHour(1);
		time.addMinute(1);
		time.addSecond(1);
		time.subHour(1);
		time.subMinute(1);
		time.subSecond(1);
		time.diaplay(time);
	}

}

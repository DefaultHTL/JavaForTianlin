package org.tianlin.java.exercise5.question1;

public class Washer implements Runnable {

	private static final Object LOCK_OBJECT = new Object();

	private String name;

	public Washer(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		synchronized (LOCK_OBJECT) {
			System.out.println(name + " is washing.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

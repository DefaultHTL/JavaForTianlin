package org.tianlin.java.exercise6.examples;

public class NotifyExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Notifier notifier = new Notifier(5000);
		try {
			synchronized (notifier) {
				notifier.wait();
			}
			System.out.printf("Over, I am in thread: %s.\n", Thread
					.currentThread().getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class Notifier implements Runnable {
		private long ms = 0;

		public Notifier(long ms) {
			this.ms = ms;
			new Thread(this).start();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(ms);
				synchronized (this) {
					this.notifyAll();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

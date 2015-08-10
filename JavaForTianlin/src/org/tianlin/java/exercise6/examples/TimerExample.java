package org.tianlin.java.exercise6.examples;

public class TimerExample implements TimerCallback {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimerExample example = new TimerExample();
		Timer timer = new Timer(5000, example);
		timer.start();
	}

	public static class Timer implements Runnable {
		private long ms = 0;
		private TimerCallback cb = null;

		public Timer(long ms, TimerCallback cb) {
			this.ms = ms;
			this.cb = cb;
		}

		public void start() {
			new Thread(this).start();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(ms);
				if (cb != null) {
					cb.callback();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void callback() {
		// TODO Auto-generated method stub
		System.out.printf("Over, I am in thread: %s.\n", Thread.currentThread()
				.getName());
	}

}

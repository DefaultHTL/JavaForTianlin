package org.tianlin.java.exercise5.question3;

import java.util.Random;

public class BufferOperation implements Runnable {
	public enum RW {
		Read, Write,
	}

	private static final int READ_LENGTH = 5;
	private static final int WRITE_LENGTH = 10;
	private static final int BUFFER_LENGTH = 100;

	private static char[] buffer = new char[BUFFER_LENGTH];
	private static int readIndex = 0;
	private static int writeIndex = 0;

	private static Object readWriteLock = new Object();

	private RW rw; // 0 for read, 1 for write
	private String name;

	public BufferOperation(RW rw, String name) {
		this.name = name;
		this.rw = rw;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (rw == RW.Read) {
				if (readIndex == BUFFER_LENGTH)
					break;
				// System.out.println("readIndex=" + readIndex + ", writeIndex="
				// + writeIndex);
				synchronized (readWriteLock) {
					if (writeIndex - readIndex > READ_LENGTH) {
						// read READ_LENGTH
						read(READ_LENGTH);
					} else {
						// read to end
						read(writeIndex - readIndex);
					}
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (rw == RW.Write) {
				if (writeIndex == BUFFER_LENGTH)
					break;
				synchronized (readWriteLock) {
					if (BUFFER_LENGTH - writeIndex > WRITE_LENGTH) {
						// write WRITE_LENGTH
						write(WRITE_LENGTH);
					} else {
						// write to end
						write(BUFFER_LENGTH - writeIndex);
					}
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void read(int length) {
		if (length == 0)
			return;

		int index = 0;
		System.out.print("Reader " + name + " reads (Total: "
				+ (readIndex + length) + "): ");
		while (index < length) {
			System.out.print(buffer[readIndex + index]);
			index++;
		}
		System.out.println();
		readIndex += length;

	}

	private void write(int length) {
		if (length == 0)
			return;

		Random rand = new Random();
		System.out.print("Writer " + name + " writes (Total: "
				+ (writeIndex + length) + "): ");
		int index = 0;
		while (index < length) {
			buffer[writeIndex + index] = (char) ('A' + rand.nextInt(26));
			System.out.print(buffer[writeIndex + index]);
			index++;
		}
		writeIndex += length;
		System.out.println();

	}
}

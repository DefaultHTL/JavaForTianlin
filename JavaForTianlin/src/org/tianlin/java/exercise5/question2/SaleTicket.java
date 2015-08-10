package org.tianlin.java.exercise5.question2;

import java.util.LinkedList;
import java.util.List;

public class SaleTicket implements Runnable {
	private int count = 0;
	private ThreadLocal<Integer> ticketCount = new ThreadLocal<Integer>();
	private ThreadLocal<List<Integer>> tickets = new ThreadLocal<List<Integer>>();

	private synchronized void sale() {
		count++;
	
		tickets.get().add(count);
		System.out.println(Thread.currentThread().getName() + "�򵽵� " + count
				+ "��Ʊ");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		tickets.set(new LinkedList<Integer>());
		ticketCount.set(0);
		Integer value;
		do {
			value = ticketCount.get();
			sale();
			ticketCount.set(value + 1);

		} while (value < 9);
		printTickets();
	}

	public synchronized void printTickets() {
		System.out.print(Thread.currentThread().getName() + "�򵽣�");
		for (int i : tickets.get()) {
			System.out.print(i);
			System.out.print(' ');
		}
		System.out.println();
	}

	public static void main(String[] args) {
		SaleTicket st = new SaleTicket();
		Thread t1 = new Thread(st, "һ�Ż�ţ");
		Thread t2 = new Thread(st, "���Ż�ţ");
		Thread t3 = new Thread(st, "���Ż�ţ");
		Thread t4 = new Thread(st, "�ĺŻ�ţ");
		Thread t5 = new Thread(st, "��Ż�ţ");
		t3.setPriority(Thread.MAX_PRIORITY);
		t5.setPriority(Thread.MIN_PRIORITY);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

	}
}

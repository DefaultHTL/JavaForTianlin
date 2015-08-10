package org.tianlin.java.exercise5.question3;

import org.tianlin.java.exercise5.question3.BufferOperation.RW;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread r1 = new Thread(new BufferOperation(RW.Read, "Bob"));
		Thread r2 = new Thread(new BufferOperation(RW.Read, "James"));
		Thread r3 = new Thread(new BufferOperation(RW.Read, "Shelly"));

		Thread w1 = new Thread(new BufferOperation(RW.Write, "Rubin"));
		Thread w2 = new Thread(new BufferOperation(RW.Write, "Steve"));

		w1.start();
		r1.start();
		w2.start();
		r2.start();
		r3.start();
	}

}

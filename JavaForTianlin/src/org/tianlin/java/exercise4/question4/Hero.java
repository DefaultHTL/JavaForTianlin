package org.tianlin.java.exercise4.question4;

import java.util.LinkedList;

public interface Hero {
	public void attackSkill(Unit target);
	public void defendSkill(LinkedList<Unit> targets);

}

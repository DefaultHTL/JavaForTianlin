package org.tianlin.java.exercise4.question4;

import java.util.LinkedList;

public class Summoner extends Unit implements Hero {

	public Summoner(String name, double attack, double defense, double maxLife) {
		super(name, attack, defense, maxLife);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attackSkill(Unit target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void defendSkill(LinkedList<Unit> targets) {
		// TODO Auto-generated method stub
		
		this.life += this.maxLife * 0.1;
		if (life > maxLife) {
			life = maxLife;
		}
		System.out.println("召唤师发动防御性技能，当前血量为" + life);
	}

}

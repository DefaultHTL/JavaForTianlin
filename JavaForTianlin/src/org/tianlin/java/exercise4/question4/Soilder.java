package org.tianlin.java.exercise4.question4;

import java.util.LinkedList;

public class Soilder extends Unit implements Hero {

	public Soilder(String name, double attack, double defense, double maxLife) {
		super(name, attack, defense, maxLife);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attackSkill(Unit target) {
		// TODO Auto-generated method stub
		target.HurtFrom(this, this.attack);
		life += attack * 0.5;
		if (life > maxLife) {
			life = maxLife;
		}
		System.out.println("战士发动攻击性技能，当前血量为" + life);
	}

	@Override
	public void defendSkill(LinkedList<Unit> targets) {
		// TODO Auto-generated method stub
		this.defense *= 2;
		this.defenseTime = 3;
		System.out.println("战士发动防御性技能，队友防御增加" + defenseTime + "回合");
		for (Unit unit : targets) {
			unit.defense *= 2;
			unit.defenseTime = 3;
		}
	}

}

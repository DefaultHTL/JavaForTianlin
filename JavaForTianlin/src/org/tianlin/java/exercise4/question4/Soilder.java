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
		System.out.println("սʿ���������Լ��ܣ���ǰѪ��Ϊ" + life);
	}

	@Override
	public void defendSkill(LinkedList<Unit> targets) {
		// TODO Auto-generated method stub
		this.defense *= 2;
		this.defenseTime = 3;
		System.out.println("սʿ���������Լ��ܣ����ѷ�������" + defenseTime + "�غ�");
		for (Unit unit : targets) {
			unit.defense *= 2;
			unit.defenseTime = 3;
		}
	}

}

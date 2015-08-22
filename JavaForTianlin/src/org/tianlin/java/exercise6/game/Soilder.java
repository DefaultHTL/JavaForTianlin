package org.tianlin.java.exercise6.game;

import java.util.LinkedList;
import java.util.Random;

//import org.tianlin.java.exercise6.game.Hero;

public class Soilder extends Unit implements Hero {

	public Soilder(String name, double attack, double defense, double maxLife) {
		super(name, attack, defense, maxLife);
	}

	@Override
	public void attackSkill(Unit target ,LinkedList<Unit> targets) {
		target.HurtFrom(this, this.attack);
		life += attack * 0.5;
		if (life > maxLife) {
			life = maxLife;
		}
		System.out.println("战士发动攻击性技能，当前血量为" + life);
	}

	@Override
	public void defendSkill(LinkedList<Unit> targets) {
		this.defense *= 2;
		this.defenseTime = 3;
		System.out.println("战士发动防御性技能，队友防御增加" + defenseTime + "回合");
		for (Unit unit : targets) {
			unit.defense *= 2;
			unit.defenseTime = 3;
		}
	}
	
	@Override
	public void Attack(Unit target, LinkedList<Unit> friends, LinkedList<Unit> targets){
		Random rand = new Random();
		int a = rand.nextInt(10);
		double realAttack = 0;
		if (a <= 1){
			this.attackSkill(target ,targets);
		} else if (a > 1 && a <= 4){
			this.defendSkill(friends);
		} else {
			System.out.println(String.format("%1$s正在攻击%2$s", this.getName(), target.getName()));
			realAttack = attack;
		}
		target.HurtFrom(this, realAttack);
	}

}


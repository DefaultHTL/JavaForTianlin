package org.tianlin.java.exercise6.game.battle;
import java.util.LinkedList;
import java.util.Random;

//import org.tianlin.java.exercise6.game.Hero;

public class Summoner extends Unit implements Hero {

	public Summoner(String name, double attack, double defense, double maxLife) {
		super(name, attack, defense, maxLife);
	}
	

	@Override
	public void defendSkill(LinkedList<Unit> targets) {
		
		this.life += this.maxLife * 0.1;
		if (life > maxLife) {
			life = maxLife;
		}
		System.out.println("召唤师发动防御性技能，当前血量为" + life);
	}

	@Override
	public void attackSkill(Unit target ,LinkedList<Unit> targets) {
		Random rand = new Random();
		if (rand.nextInt(3) == 0) {
			targets.add(new Unit("unit" , 5, 3, 35));
			System.out.println("召唤师召唤unit");
		} else if (rand.nextInt(2) == 1) {
			targets.add(new RangeUnit("range" , 8, 1, 30));
			System.out.println("召唤师召唤range");
		} else {
			targets.add(new ShieldUnit("shield" , 4, 5, 40));
			System.out.println("召唤师召唤shield");
		}
	}

	@Override
	public void Attack(Unit target, LinkedList<Unit> friends, LinkedList<Unit> targets){
		Random rand = new Random();
		int a = rand.nextInt(10);
		double realAttack = 0;
		if (a <= 1){
			this.attackSkill(target ,friends);
		} else if (a > 1 && a <= 4){
			this.defendSkill(friends);
		} else {
			System.out.println(String.format("%1$s正在攻击%2$s", this.getName(), target.getName()));
			realAttack = attack;
		}
		target.HurtFrom(this, realAttack);
	}

}


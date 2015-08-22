package org.tianlin.java.exercise6.game.battle;

public class ShieldUnit extends Unit {

	public ShieldUnit(String name, double attack, double defense, double life) {
		super(name, attack, defense, life);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void HurtFrom(Unit enemy, double attack) {

		if (Math.random() < 0.2) {
			System.out.println(String.format("%1$s¸ñµ²ÁË%2$sµÄ¹¥»÷", this.getName(),
					enemy.getName()));
			deathJudgingOrCounterAttack(enemy);
		} else {
			super.HurtFrom(enemy, attack);
		}
	}
}

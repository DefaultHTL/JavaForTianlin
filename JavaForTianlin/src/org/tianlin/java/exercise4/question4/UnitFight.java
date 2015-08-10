package org.tianlin.java.exercise4.question4;

public class UnitFight {
	public static void main(String[] args) {
		fightTest2();
	}
	
	public static void fightTest2() {
		
	}

	public static void fightTest1() {
		RangeUnit Robin = new RangeUnit("Robin", 5, 1, 20);
		ShieldUnit Tom = new ShieldUnit("Tom", 3, 3, 30);
		simpleFight(Robin, Tom);
		System.out.println("ս������ - " + Robin.getName() + "����Ϊ"
				+ Robin.getLife() + ", " + Tom.getName() + "����Ϊ"
				+ Tom.getLife());
	}

	public static void simpleFight(Unit Robin, Unit Tom) {
		Unit attacker = Robin;
		Unit defender = Tom;
		Unit temp = null;

		while (Robin.getLife() > 0 && Tom.getLife() > 0) {
			attacker.Attack(defender);
			temp = attacker;
			attacker = defender;
			defender = temp;
		}
	}
}

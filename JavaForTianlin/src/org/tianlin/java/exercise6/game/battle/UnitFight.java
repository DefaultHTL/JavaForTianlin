package org.tianlin.java.exercise6.game.battle;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

//import org.tianlin.java.exercise6.game.Hero;

public class UnitFight {

	final static int SUMMONER_ATTACK_MIN = 7;
	final static int SUMMONER_ATTACK_MAX = 8;
	final static int SOLDIER_ATTACK_MIN = 9;
	final static int SOLDIER_ATTACK_MAX = 14;
	final static int UNIT_ATTACK = 5;
	final static int RANGE_UNIT_ATTACK = 8;
	final static int SHIELD_UNIT_ATTACK = 4;

	final static int SUMMONER_DEFENSE = 2;
	final static int SOLDIER_DEFENSE = 4;
	final static int UNIT_DEFENSE = 3;
	final static int RANGE_DEFENSE = 1;
	final static int SHIELD_DEFENSE = 5;

	final static int SUMMONER_LIFE = 40;
	final static int SOLDIER_LIFE = 50;
	final static int UNIT_LIFE = 35;
	final static int RANGE_LIFE = 30;
	final static int SHIELD_LIFE = 40;

	public static void main(String[] args) {
		fightTest2();
	}

	public static void fightTest2() {

		LinkedList<Unit> units1 = new LinkedList<Unit>();
		LinkedList<Unit> units2 = new LinkedList<Unit>();

		createUnits(units1);
		createUnits(units2);

		int count = 0;
		
		while (!units1.isEmpty() && !units2.isEmpty()) {

			count++;
			
			System.out.println("当前为第" + count + "回合");
			System.out.println("第一队剩余" + units1.size() + "人， 第二队" + units2.size() +"人");

			System.out.println("输入单位编号 [1 ~ " + units1.size() + "]:");
			Scanner scanner1 = new Scanner(System.in);
			int a = scanner1.nextInt() - 1;
			
			System.out.println("输入敌人编号:[1 ~ " + units2.size() + "]:");
			Scanner scanner2 = new Scanner(System.in);
			int foe = scanner2.nextInt() - 1;
			
			units1.get(a).Attack(units2.get(foe), units1, units2);
				
			if (units2.get(foe).life <= 0) {
				units2.remove(units2.get(foe));
			}
			if (units1.isEmpty() || units2.isEmpty()) {
				break;
			}

			if (units2.get(foe).life > 0) {
				units2.get(foe).Attack(units1.get(a), units2, units1);
				if (units1.get(a).life <= 0) {
					units1.remove(units1.get(a));
				}
				if (units1.isEmpty() || units2.isEmpty()) {
					break;
				}
			}
		}
		System.out.println("战斗结束 - " + units1);
		System.out.println(units2);

		if (units1.isEmpty()) {
			System.out.println("You lose");
		}
		else {
			System.out.println("You win");
		}
	}

	public static Hero createUnits(LinkedList<Unit> units) {
		Hero hero;
		Random rand = new Random();
		{
			if (rand.nextInt(2) < 1) {
				hero = new Summoner("电脑召唤师",
						SUMMONER_ATTACK_MIN + rand.nextInt(SUMMONER_ATTACK_MAX - SUMMONER_ATTACK_MIN + 1),
						SUMMONER_DEFENSE, SUMMONER_LIFE);
				System.out.println("电脑召唤师" + "Attack:" + ((Unit) hero).getAttack() + " Defense:"
						+ ((Unit) hero).getDefense() + " Life:" + ((Unit) hero).getLife());
			} else {
				hero = new Soilder("电脑战士",
						SOLDIER_ATTACK_MIN + rand.nextInt(SOLDIER_ATTACK_MAX - SOLDIER_ATTACK_MIN + 1), SOLDIER_DEFENSE,
						SOLDIER_LIFE);
				System.out.println("电脑战士" + "Attack:" + ((Unit) hero).getAttack() + " Defense:"
						+ ((Unit) hero).getDefense() + " Life:" + ((Unit) hero).getLife());
			}
			units.add((Unit) hero);

			for (int i = 1; i <= 5; i++) {
				if (rand.nextInt(3) == 0) {
					units.add(new Unit("电脑unit" + "-" + i, UNIT_ATTACK, UNIT_DEFENSE, UNIT_LIFE));
				} else if (rand.nextInt(2) == 1) {
					units.add(new RangeUnit("电脑range" + "-" + i, RANGE_UNIT_ATTACK, RANGE_DEFENSE, RANGE_LIFE));
				} else {
					units.add(new ShieldUnit("电脑shield" + "-" + i, SHIELD_UNIT_ATTACK, SHIELD_DEFENSE,
							SHIELD_LIFE));
				}

			}
		}
		return hero;
	}
}

/*
 * public static void fightTest1() { RangeUnit Robin = new RangeUnit("Robin", 5,
 * 1, 20); ShieldUnit Tom = new ShieldUnit("Tom", 3, 3, 30); simpleFight(Robin,
 * Tom); System.out.println("战斗结束 - " + Robin.getName() + "生命为" +
 * Robin.getLife() + ", " + Tom.getName() + "生命为" + Tom.getLife()); }
 * 
 * public static void simpleFight(Unit Robin, Unit Tom) { Unit attacker = Robin;
 * Unit defender = Tom; Unit temp = null;
 * 
 * while (Robin.getLife() > 0 && Tom.getLife() > 0) { attacker.Attack(defender);
 * temp = attacker; attacker = defender; defender = temp; } }
 **/

package org.tianlin.java.exercise4.question4;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Army {
	private static final int SUMMONER_ATTACK_MIN = 7;
	private static final int SUMMONER_ATTACK_MAX = 8;
	private static final int SOLDIER_ATTACK_MIN = 9;
	private static final int SOLDIER_ATTACK_MAX = 14;
	private static final int UNIT_ATTACK = 5;
	private static final int RANGE_UNIT_ATTACK = 8;
	private static final int SHIELD_UNIT_ATTACK = 4;

	private static final int SUMMONER_DEFENSE = 2;
	private static final int SOLDIER_DEFENSE = 4;
	private static final int UNIT_DEFENSE = 3;
	private static final int RANGE_DEFENSE = 1;
	private static final int SHIELD_DEFENSE = 5;

	private static final int SUMMONER_LIFE = 40;
	private static final int SOLDIER_LIFE = 50;
	private static final int UNIT_LIFE = 35;
	private static final int RANGE_LIFE = 30;
	private static final int SHIELD_LIFE = 40;

	private Hero hero;
	List<Unit> units = new LinkedList<Unit>();

	public Army() {
		Random rand = new Random();
		if (rand.nextInt(2) < 1)
			hero = new Summoner("A", SUMMONER_ATTACK_MIN
					+ rand.nextInt(SUMMONER_ATTACK_MAX - SUMMONER_ATTACK_MIN
							+ 1), SUMMONER_DEFENSE, SUMMONER_LIFE);
		else
			hero = new Soilder("B",
					SOLDIER_ATTACK_MIN
							+ rand.nextInt(SOLDIER_ATTACK_MAX
									- SOLDIER_ATTACK_MIN + 1), SOLDIER_DEFENSE,
					SOLDIER_LIFE);

		for (int i = 1; i <= 5; i++) {
			if (rand.nextInt(3) == 0) {
				units.add(new Unit("unit" + i, UNIT_ATTACK, UNIT_DEFENSE,
						UNIT_LIFE));
			} else if (rand.nextInt(2) == 1) {
				units.add(new RangeUnit("range" + i, RANGE_UNIT_ATTACK,
						RANGE_DEFENSE, RANGE_LIFE));
			} else {
				units.add(new ShieldUnit("shield" + i, SHIELD_UNIT_ATTACK,
						SHIELD_DEFENSE, SHIELD_LIFE));
			}
		}
	}

	public Unit getHero() {
		return (Unit) hero;
	}
}

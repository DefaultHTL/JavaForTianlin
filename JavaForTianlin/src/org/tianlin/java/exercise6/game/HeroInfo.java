package org.tianlin.java.exercise6.game;

import java.util.LinkedList;
import java.util.List;

import org.tianlin.java.exercise6.game.utility.Log;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class HeroInfo {
	private static final String TAG = "HeroInfo";

	public static HeroInfo parse(Element heroElem) {
		if (heroElem == null)
			return null;

		String name, type;
		int exp, money;
		name = heroElem.getAttribute(GameServer.ATTR_HERONAME);
		type = heroElem.getAttribute(GameServer.ATTR_HEROTYPE);
		try {
			exp = Integer.parseInt(heroElem.getAttribute(GameServer.ATTR_EXPERIENCE));
		} catch (NumberFormatException e) {
			Log.w(TAG, "Error parsing hero experience, set %s's experience to 0.", name);
			exp = 0;
		}
		try {
			money = Integer.parseInt(heroElem.getAttribute(GameServer.ATTR_MONEY));
		} catch (NumberFormatException e) {
			Log.w(TAG, "Error parsing hero money, set %s's money to 0.", name);
			money = 0;
		}
		HeroInfo info = new HeroInfo(name, type, exp, money);

		NodeList unitNodes = heroElem.getElementsByTagName(GameServer.TAG_UNIT);
		for (int i = 0; i < unitNodes.getLength(); i++) {
			info.units.add(UnitInfo.parse((Element) unitNodes.item(i)));
		}

		Log.i(TAG, "Hero parsed: %s", info.toString());
		return info;
	}

	private String heroName;
	private String heroType;
	private int exp;
	private int money;
	private List<UnitInfo> units = new LinkedList<UnitInfo>();

	public HeroInfo(String heroName, String heroType, int exp, int money) {
		this.heroName = heroName;
		this.heroType = heroType;
		this.exp = exp;
		this.money = money;
	}

	public String getHeroName() {
		return heroName;
	}

	public String getHeroType() {
		return heroType;
	}

	public int getExp() {
		return exp;
	}

	public int getMoney() {
		return money;
	}
	
	public List<UnitInfo> getUnits() {
		return units;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HeroInfo{ ").append("hero name=").append(heroName).append(", hero type=").append(heroType)
				.append(", exp=").append(exp).append(", money=").append(money).append(", unit count=")
				.append(units.size()).append(" }");
		return builder.toString();
	}
}

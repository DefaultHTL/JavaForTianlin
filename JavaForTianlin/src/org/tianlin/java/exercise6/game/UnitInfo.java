package org.tianlin.java.exercise6.game;

import org.tianlin.java.exercise6.game.utility.Log;
import org.w3c.dom.Element;

public class UnitInfo {
	private static final String TAG = "UnitInfo";

	public static UnitInfo parse(Element unitElem) {
		if (unitElem == null)
			return null;

		String name, type;
		name = unitElem.getAttribute(GameServer.ATTR_USERNAME);
		type = unitElem.getAttribute(GameServer.ATTR_PASSWORD);
		UnitInfo info = new UnitInfo(name, type);

		Log.i(TAG, "Unit parsed: %s", info.toString());
		return info;
	}

	private String unitName;
	private String unitType;

	public UnitInfo(String unitName, String unitType) {
		this.unitName = unitName;
		this.unitType = unitType;
	}

	public String getUnitName() {
		return unitName;
	}

	public String getUnitType() {
		return unitType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnitInfo{ ").append("unit name=").append(unitName).append(", unit type=").append(unitType)
				.append(" }");
		return builder.toString();
	}
}

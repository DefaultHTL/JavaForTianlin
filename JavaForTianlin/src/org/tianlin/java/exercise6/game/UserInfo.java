package org.tianlin.java.exercise6.game;

import java.util.LinkedList;
import java.util.List;

import org.tianlin.java.exercise6.game.utility.Log;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UserInfo {
	private static final String TAG = "UserInfo";

	public static UserInfo parse(Element userElem) {
		if (userElem == null)
			return null;

		String username, password;
		username = userElem.getAttribute(GameServer.ATTR_USERNAME);
		password = userElem.getAttribute(GameServer.ATTR_PASSWORD);
		UserInfo info = new UserInfo(username, password);

		NodeList heroNodes = userElem.getElementsByTagName(GameServer.TAG_HERO);
		for (int i = 0; i < heroNodes.getLength(); i++) {
			info.heros.add(HeroInfo.parse((Element) heroNodes.item(i)));
		}

		Log.i(TAG, "User parsed: %s", info.toString());
		return info;
	}

	public UserInfo(String username, String password) {
		this.username = username;
		this.password = password;
	}

	private String username;
	private String password;
	private List<HeroInfo> heros = new LinkedList<HeroInfo>();

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<HeroInfo> getHeros() {
		return heros;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo{ ").append("username=").append(username).append(", password=").append(password)
				.append(", hero count=").append(heros.size()).append(" }");
		return builder.toString();
	}
}

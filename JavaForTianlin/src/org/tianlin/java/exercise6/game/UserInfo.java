package org.tianlin.java.exercise6.game;

import org.tianlin.java.exercise6.game.utility.Log;

import java.util.LinkedList;

import org.tianlin.java.exercise6.game.battle.Unit;;

public class UserInfo {
	private static final String TAG = "UserInfo";
	private static final String SEPARATOR = ";";

	/*
	 * workaround
	 */
	public static UserInfo parse(String line) {
		if (!line.isEmpty()) {
			String[] segs = line.split(SEPARATOR);
			Log.d(TAG, "Parse UserInfo: %s, %s, %s, %s", segs[0], segs[1], segs[2], segs[3], segs[4]);
			return new UserInfo(segs[0], segs[1], Integer.parseInt(segs[2]), Integer.parseInt(segs[3]), segs[4]);
		}

		return null;
	}

	public UserInfo(String username, String password, int money, int experience, LinkedList<Unit> army) {
		this.username = username;
		this.password = password;
		this.money = money;
		this.experience = experience;
		this.army = army;
	}

	private String username;
	private String password;
	private int money;
	private int experience;
	private LinkedList<Unit> army;
	/*
	 * TODO
	 * experience
	 * List<Unit>
	 */

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getMoney() {
		return money;
	}
	
	public int getExperience() {
		return experience;
	}
	
	public LinkedList<Unit> getArmy() {
		return army;
	}
}

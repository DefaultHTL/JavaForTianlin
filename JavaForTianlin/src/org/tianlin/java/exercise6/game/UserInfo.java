package org.tianlin.java.exercise6.game;

import org.tianlin.java.exercise6.game.utility.Log;

public class UserInfo {
	private static final String TAG = "UserInfo";
	private static final String SEPARATOR = ";";

	/*
	 * workaround
	 */
	public static UserInfo parse(String line) {
		if (!line.isEmpty()) {
			String[] segs = line.split(SEPARATOR);
			Log.d(TAG, "Parse UserInfo: %s, %s, %s", segs[0], segs[1], segs[2]);
			return new UserInfo(segs[0], segs[1], Integer.parseInt(segs[2]));
		}

		return null;
	}

	public UserInfo(String username, String password, int money) {
		this.username = username;
		this.password = password;
		this.money = money;
	}

	private String username;
	private String password;
	private int money;
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
}

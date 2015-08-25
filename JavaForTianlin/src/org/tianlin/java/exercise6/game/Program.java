package org.tianlin.java.exercise6.game;

import org.tianlin.java.exercise6.game.utility.Log;
import org.tianlin.java.exercise6.game.utility.LogType;

public class Program {

	public static void main(String[] args) {
		try {
			// TODO check arguments
			if (args.length == 0) {
				/*
				 * run as client
				 */
				Log.init(LogType.NoConsole);
				new GameClient().run();
			} else {
				/*
				 * run as server
				 */
				Log.init(LogType.Server);
				new GameServer().run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Log.uninit();
		}
	}
}

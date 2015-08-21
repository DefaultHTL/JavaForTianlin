package org.tianlin.java.exercise6.game;

import org.tianlin.java.exercise6.game.utility.Log;
import org.tianlin.java.exercise6.game.utility.LogType;

public class Program {

	public static void main(String[] args) {
		// TODO check arguments
		if (args.length == 0) {
			/*
			 * run as client
			 */
			runClient();
		} else {
			/*
			 * run as server
			 */
			runServer();
		}
	}

	public static void runServer() {
		/*
		 * initial log system first
		 */
		Log.init(LogType.Server);

		GameServer server = new GameServer();
		server.run();

		try {
			/*
			 * wait server thread to finish
			 */
			if (server.getServerThread() != null)
				server.getServerThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * uninit log system
		 */
		Log.uninit();
	}
	
	public static void runClient() {
		/*
		 * initial log system first
		 */
		Log.init(LogType.NoConsole);
		
		GameClient client = new GameClient();
		client.run();
		
		/*
		 * uninit log system
		 */
		Log.uninit();
	}
}

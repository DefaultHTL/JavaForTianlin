package org.tianlin.java.exercise6.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.tianlin.java.exercise6.game.battle.UnitFight;
import org.tianlin.java.exercise6.game.utility.CoDec;
import org.tianlin.java.exercise6.game.utility.CommandEnum;
import org.tianlin.java.exercise6.game.utility.Log;
import org.tianlin.java.exercise6.game.utility.CoDec.DecodeResult;
import org.tianlin.java.exercise6.game.UserInfo;

public class GameClient {
	private static final String TAG = "GameClient";
	private static final String SERVER_ADDR = "localhost";
	private static final int SERVER_PORT = 30001;
	private static final int BUFFER_LENGTH = 32; // TODO is it too small?

	// TODO change buffer to below buffer in encoding methods
	private byte[] buffer = new byte[BUFFER_LENGTH];
	private UserInfo userInfo = null;

	/*
	 * menu list
	 */
	private enum MenuEnum {
		None, Login, Exit, UserPage
	}

	public void run() {
		Log.i(TAG, "------>> Client Start <<------");
		Socket server = null;
		DataInputStream input = null;
		DataOutputStream output = null;
		Scanner scanner = null;

		try {
			server = new Socket(SERVER_ADDR, SERVER_PORT);
			Log.i(TAG, "Connection established! Server address: %s\n", server.getInetAddress().getHostAddress());

			input = new DataInputStream(server.getInputStream());
			output = new DataOutputStream(server.getOutputStream());
			scanner = new Scanner(System.in);

			/*
			 * do login first
			 */
			MenuEnum nextMenu = MenuEnum.Login;
			while (nextMenu != MenuEnum.None) {
				nextMenu = showMenu(nextMenu, input, output, scanner);
			}

			// TODO get user info

			// TODO fight
			// TODO request some enemy

			// someclass.beginfight(scanner);

			// TODO send back to server

			// TODO bye

		} catch (UnknownHostException e) {
			Log.e(TAG, "Connection failed, unknown host: %s", SERVER_ADDR);
		} catch (IOException e) {
			Log.e(TAG, "IOException: %s\n", e.getMessage());
		} finally {
			try {
				if (scanner != null)
					scanner.close();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
				if (server != null)
					server.close();
			} catch (IOException e) {
				Log.e(TAG, "IOException when closing socket: %s\n", e.getMessage());
			}
		}
		Log.i(TAG, "------>> Client Stop <<------");
	}

	/*
	 * show menu according to menu number return next menu number
	 */
	private MenuEnum showMenu(MenuEnum menu, DataInputStream input, DataOutputStream output, Scanner scanner)
			throws IOException {
		Log.i(TAG, "Current menu is %s", menu.name());

		MenuEnum next = MenuEnum.None;
		int choose = 0;

		switch (menu) {
		/*
		 * log in menu always shows first
		 */
		case Login:
			p("\n********************************************************************************\n");
			p("                     Welcome to Hero Fight! (version 1.0)\n\n");
			p("                                1. Sign in\n");
			p("                                2. Sign up\n");
			p("                                3. Exit\n");
			p("\n********************************************************************************\n");
			choose = getUserInputInRange(1, 3, scanner);
			switch (choose) {
			case 1:
				if (processSignIn(input, output, scanner)) {
					userInfo = getUserInfo(input, output);
					next = MenuEnum.UserPage;
				} else {
					next = MenuEnum.Exit;
				}
				break;
			case 2:
				if (processSignUp(input, output, scanner)) {
					userInfo = getUserInfo(input, output);
					next = MenuEnum.UserPage;
				} else {
					next = MenuEnum.Login;
				}
				break;
			case 3:
				next = MenuEnum.Exit;
				break;
			}
			break;
		case Exit:
			p("\nBye-bye!\n");
			sendClientExit(output);
			next = MenuEnum.None;
			break;
		case UserPage:
			p("   Make your choice\n");
			p("     1. Start random game\n");
			p("     2. Exit\n");
			choose = getUserInputInRange(1, 2, scanner);
			switch (choose) {
			case 1:
				/*
				if (userInfo.getArmy().isEmpty()) {
					System.out.println("Create new army.");
					UnitFight.createArmy(userInfo.getArmy());
					System.out.println("Your army: " + userInfo.getArmy());
				}

				System.out.println("Start random battle");
				processRandomGame(scanner);

				// TODO after random game
				if (processRandomGame(scanner) == true) {

				} else {

				}
				*/
				next = MenuEnum.UserPage;
				break;
			case 2:
				next = MenuEnum.Exit;
				break;
			}
			break;
		default:
			next = MenuEnum.None;
		}
		return next;
	}

	private boolean processSignUp(DataInputStream input, DataOutputStream output, Scanner scanner) throws IOException {
		p(" > Username: ");
		String username = scanner.next();
		p(" > Password: ");
		String password = scanner.next();
		
		byte[] content = CoDec.encode(CommandEnum.SignUp, username, password);
		output.write(content);
		int length = input.read(buffer);
		DecodeResult result = CoDec.decode(buffer, length);
		boolean response = result.valid && (boolean) result.arguments[0];
		p("Sign up %s!\n", response ? "succeed" : "fail, this username already exists.");
		return response;
	}

	/*
	 * sign in progress
	 */
	private boolean processSignIn(DataInputStream input, DataOutputStream output, Scanner scanner) throws IOException {
		p(" > Username: ");
		String username = scanner.next();
		p(" > Password: ");
		String password = scanner.next();

		byte[] content = CoDec.encode(CommandEnum.SignIn, username, password);
		output.write(content);
		int length = input.read(buffer);
		DecodeResult result = CoDec.decode(buffer, length);
		boolean response = result.valid && (boolean) result.arguments[0];
		p("Authentication %s!\n", response ? "succeed" : "fail");
		return response;
	}

//	private boolean processRandomGame(Scanner scanner) {
//		boolean a = UnitFight.fight(userInfo.getArmy());
//		if (a == true) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	private UserInfo getUserInfo(DataInputStream input, DataOutputStream output) {
		// TODO
		return null;
	}

	private void sendClientExit(DataOutputStream output) throws IOException {
		output.write(CoDec.encode(CommandEnum.ClientExit));
	}

	/*
	 * Get what we want from user input.
	 */
	private int getUserInputInRange(int min, int max, Scanner scanner) {
		int n = max + 1;
		do {
			p(" > ");
			try {
				n = scanner.nextInt();
			} catch (InputMismatchException e) {
				n = max + 1;
			}
			if (n >= min && n <= max) {
				break;
			} else {
				p("Wrong input, please choose from %d to %d.\n", min, max);
			}
		} while (true);

		Log.i(TAG, "User choice: %d", n);
		return n;
	}

	/*
	 * convenient for System.out.printf()
	 */
	private void p(String pattern, Object... args) {
		System.out.printf(pattern, args);
	}
}

package org.tianlin.java.exercise6.game.utility;

public class CoDec {
	private static final String TAG = "CoDec";
	private static final boolean DEBUG = false;

	/*
	 * keep commands same as Commands.txt file and CommandEnum.java
	 */
	private static final byte COMMAND_SIGN_IN = 0x01;
	private static final byte COMMAND_SIGN_IN_RESULT = 0x02;
	private static final byte COMMAND_SIGN_UP = 0x03;
	private static final byte COMMAND_CLIENT_EXIT = 0x7F;

	private static final byte SIGN_RESULT_SUCCEED = 0;
	private static final byte SIGN_RESULT_FAIL = 1;

	/*
	 * decode byte array into a DecodeResult object will never return null
	 */
	public static DecodeResult decode(byte[] bytes, int length) {
		if (bytes == null || length > bytes.length || bytes.length < 3) {
			Log.e(TAG, "Invalid argument when decoding.");
			return new DecodeResult();
		}

		if (DEBUG) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < length; i++) {
				builder.append(String.format("0x%02X, ", bytes[i]));
			}
			Log.d(TAG, "Decode command { %s }", builder.substring(0, builder.length() - 2));
		}

		if (!verifyChecksum(bytes, length)) {
			Log.w(TAG, "Checksum failure when decoding.");
			return new DecodeResult();
		}

		/*
		 * check length
		 */
		if (bytes[1] != length - 2) {
			Log.w(TAG, "Length of content %d should be %d, abort.", length, bytes[2]);
			return new DecodeResult();
		}

		DecodeResult result = null;
		switch (bytes[0]) {
		case COMMAND_SIGN_IN:
			result = decodeSignIn(bytes);
			break;
		case COMMAND_SIGN_IN_RESULT:
			result = decodeSignInResult(bytes);
			break;
		case COMMAND_SIGN_UP:
			// TODO
			break;
		case COMMAND_CLIENT_EXIT:
			result = decodeClientExit(bytes);
			break;
		default:
			Log.w(TAG, "Ignore unexpected command: 0x%02X", bytes[0]);
			result = new DecodeResult();
		}

		return result;
	}

	/*
	 * encode all commands into a byte array for transfer, checksum added at
	 * tail
	 */
	public static byte[] encode(CommandEnum command, Object... args) {
		byte[] ret = null;

		switch (command) {
		case SignIn:
			/*
			 * arguments: user name and password
			 */
			if (args.length == 2) {
				ret = encodeSignIn(args[0].toString(), args[1].toString());
			} else {
				Log.e(TAG, "Encode SignIn with argument count %d!", args.length);
			}
			break;
		case SignInResult:
			if (args.length == 1) {
				ret = encodeSignInResult((boolean) args[0]);
			} else {
				Log.e(TAG, "Encode SignInResult with argument count %d!", args.length);
			}
			break;
		case SignUp:
			// TODO
			break;
		case ClientExit:
			ret = encodeClientExit();
			break;
		default:
			break;
		}

		if (ret == null) {
			Log.w(TAG, "Encode fail for command '%s'", command.name());
		} else if (DEBUG) {
			StringBuilder builder = new StringBuilder();
			for (byte b : ret) {
				builder.append(String.format("0x%02X, ", b));
			}
			Log.d(TAG, "Encode command '%s' into { %s }", command.name(), builder.substring(0, builder.length() - 2));
		}
		return ret;
	}

	/*
	 * represent a decode result if field 'result' is false, this object will
	 * not be valid
	 */
	public static class DecodeResult {
		public boolean valid;
		public CommandEnum command;
		public Object[] arguments;

		public DecodeResult() {
			this.valid = false;
			this.command = null;
			this.arguments = null;
		}

		public DecodeResult(boolean valid, CommandEnum command, Object... arguments) {
			this.valid = valid;
			this.command = command;
			this.arguments = arguments;
		}
	}

	/*
	 * encode sign in command
	 */
	private static byte[] encodeSignIn(String username, String password) {
		Log.i(TAG, "Encoding SignIn with username=%s, password=%s", username, password);
		byte[] ub = username.getBytes();
		byte[] pb = password.getBytes();
		byte[] b = new byte[5 + ub.length + pb.length]; // 1+1+1+ub+1+pb+1
		// command
		b[0] = COMMAND_SIGN_IN;
		// length including checksum
		b[1] = (byte) (3 + ub.length + pb.length);
		// length of user name
		b[2] = (byte) ub.length;
		// user name
		System.arraycopy(ub, 0, b, 3, ub.length);
		// length of password
		b[ub.length + 3] = (byte) pb.length;
		// password
		System.arraycopy(pb, 0, b, ub.length + 4, pb.length);
		// checksum
		b[b.length - 1] = calculateChecksum(b);
		return b;
	}

	/*
	 * decode sign in command
	 */
	private static DecodeResult decodeSignIn(byte[] buffer) {
		String username = new String(buffer, 3, buffer[2]);
		String password = new String(buffer, 4 + buffer[2], buffer[3 + buffer[2]]);
		Log.i(TAG, "Decoding SignIn with username=%s, password=%s", username, password);
		return new DecodeResult(true, CommandEnum.SignIn, username, password);
	}

	/*
	 * encode sign in result
	 */
	private static byte[] encodeSignInResult(boolean result) {
		Log.i(TAG, "Encoding SignInResult with result=%b", result);
		byte[] b = new byte[4];
		// command
		b[0] = COMMAND_SIGN_IN_RESULT;
		// length
		b[1] = 2;
		// result
		b[2] = result ? SIGN_RESULT_SUCCEED : SIGN_RESULT_FAIL;
		// checksum
		b[3] = calculateChecksum(b);
		return b;
	}

	/*
	 * decode sign in result
	 */
	private static DecodeResult decodeSignInResult(byte[] buffer) {
		boolean succeed = buffer[2] == SIGN_RESULT_SUCCEED;
		Log.i(TAG, "Decoding SignInResult with result=%b", succeed);
		return new DecodeResult(true, CommandEnum.SignInResult, succeed);
	}

	/*
	 * encode client exit event
	 */
	private static byte[] encodeClientExit() {
		Log.i(TAG, "Encoding ClientExit");
		byte[] b = new byte[3];
		// command
		b[0] = COMMAND_CLIENT_EXIT;
		// length
		b[1] = 1;
		// checksum
		b[2] = calculateChecksum(b);
		return b;
	}

	private static DecodeResult decodeClientExit(byte[] buffer) {
		Log.i(TAG, "Decoding ClientExit");
		return new DecodeResult(true, CommandEnum.ClientExit);
	}

	/*
	 * checksum calculation, XOR over all bytes except checksum byte
	 */
	private static byte calculateChecksum(byte[] b) {
		byte c = 0;
		for (int i = 0; i < b.length - 1; i++) {
			c ^= b[i];
		}
		return c;
	}

	/*
	 * verify checksum
	 */
	private static boolean verifyChecksum(byte[] b, int length) {
		byte c = 0;
		for (int i = 0; i < length; i++) {
			c ^= b[i];
		}
		return c == 0;
	}
}

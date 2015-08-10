package org.tianlin.java.exercise6.examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientExample {
	/*
	 * 服务器的地址和端口 客户端就是通过这个连接到服务器的
	 */
	private static final String SERVER_ADDR = "localhost";
	private static final int SERVER_PORT = 30001;

	public static void main(String[] args) {
		System.out.printf("Example begins\n");
		Socket server = null;
		try {
			/*
			 * 连接到服务器
			 */
			server = new Socket(SERVER_ADDR, SERVER_PORT);
			System.out.printf("Connection established from %s\n", server
					.getInetAddress().getHostAddress());

			/*
			 * 同样打开两个流用于读写
			 */
			DataInputStream input = new DataInputStream(server.getInputStream());
			DataOutputStream output = new DataOutputStream(
					server.getOutputStream());
			/*
			 * 打开scanner用于读取用户输入
			 */
			Scanner scanner = new Scanner(System.in);
			String userInput = null;
			/*
			 * 循环读取用户输入，并发给服务器，直到读到‘exit’
			 */
			do {
				System.out.printf("Please input:\n");
				userInput = scanner.nextLine();
				System.out.printf("Sending to server: %s\n", userInput);
				output.writeUTF(userInput);
				String inputString = input.readUTF();
				System.out.printf("Receive from server: %s\n", inputString);
			} while (!"exit".equals(userInput));

			System.out.printf("Closing...\n");

			/*
			 * 关闭打开的流
			 */
			scanner.close();
			output.close();
			input.close();
		} catch (UnknownHostException e) {
			System.err.printf(
					"UnknownHostException when connecting to server: %s\n",
					e.getMessage());
		} catch (IOException e) {
			System.err.printf("IOException when connecting to server: %s\n",
					e.getMessage());
		} finally {
			try {
				if (server != null) {
					/*
					 * 关闭连接
					 */
					server.close();
				}
			} catch (IOException e) {
				System.err.printf("IOException when closing socket: %s\n",
						e.getMessage());
			}
		}
		System.out.printf("Example ends\n");
	}

}

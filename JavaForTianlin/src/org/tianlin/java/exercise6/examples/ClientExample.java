package org.tianlin.java.exercise6.examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientExample {
	/*
	 * �������ĵ�ַ�Ͷ˿� �ͻ��˾���ͨ��������ӵ���������
	 */
	private static final String SERVER_ADDR = "localhost";
	private static final int SERVER_PORT = 30001;

	public static void main(String[] args) {
		System.out.printf("Example begins\n");
		Socket server = null;
		try {
			/*
			 * ���ӵ�������
			 */
			server = new Socket(SERVER_ADDR, SERVER_PORT);
			System.out.printf("Connection established from %s\n", server
					.getInetAddress().getHostAddress());

			/*
			 * ͬ�������������ڶ�д
			 */
			DataInputStream input = new DataInputStream(server.getInputStream());
			DataOutputStream output = new DataOutputStream(
					server.getOutputStream());
			/*
			 * ��scanner���ڶ�ȡ�û�����
			 */
			Scanner scanner = new Scanner(System.in);
			String userInput = null;
			/*
			 * ѭ����ȡ�û����룬��������������ֱ��������exit��
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
			 * �رմ򿪵���
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
					 * �ر�����
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

package org.tianlin.java.exercise6.examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {
	/*
	 * �������ļ����˿�
	 */
	private static final int PORT = 30001;

	public static void main(String[] args) {
		System.out.printf("Example begins\n");
		ServerExample server = new ServerExample();
		server.start();
		System.out.printf("Example ends\n");
	}

	private void start() {
		ServerSocket serverSocket = null;
		try {
			/*
			 * ��0.0.0.0:30001����������������������0.0.0.0���������������ַ��
			 * ���磬һ̨������������������ַ�ֱ���192.168.1.2��10.0.0.2����ô����������192.168.1.2:30001
			 * ��10.0.0.2:30001��ͬʱ��ʼ������
			 */
			serverSocket = new ServerSocket(PORT);
			System.out.printf("Server starts listening on port:%d\n",
					serverSocket.getLocalPort());
			/*
			 * ѭ�����ܿͻ�����������
			 */
			while (true) {
				/*
				 * �÷������������̣߳�ֱ���пͻ�����������
				 */
				Socket clientSocket = serverSocket.accept();
				/*
				 * ��ӡ���Է���IP��ַ
				 */
				System.out.printf("Connection established from %s\n",
						clientSocket.getInetAddress().getHostAddress());
				/*
				 * �������̴߳������� ���̼߳����ȴ��ͻ������ӡ�
				 */
				new Thread(new Handler(clientSocket)).start();
			}
		} catch (IOException e) {
			System.err.printf("IOException: %s\n", e.getMessage());
		} finally {
			if (serverSocket != null) {
				try {
					/*
					 * �������˹رշ��������ͷ���Դ��
					 */
					serverSocket.close();
				} catch (IOException e) {
					System.err.printf("IOException when closing server: %s\n",
							e.getMessage());
				}
			}
		}
	}

	/*
	 * һ������ͻ�������ľ�̬��
	 */
	private static class Handler implements Runnable {
		/*
		 * �ͻ���
		 */
		private Socket client = null;

		public Handler(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				/*
				 * ����������һ������һ�������������ͻ���ͨ�š�
				 */
				DataInputStream input = new DataInputStream(
						client.getInputStream());
				DataOutputStream output = new DataOutputStream(
						client.getOutputStream());

				String inputString = null;
				/*
				 * ѭ����ȡ�ͻ�����Ϣ������ǡ�exit�����˳�ѭ��
				 */
				do {
					/*
					 * ����UTF��ʽ��ȡ�ͻ�����Ϣ�������ȡ�ķ�ʽ��Ҫ��ͻ��˷��ͷ�ʽ��ͬ����������롣
					 */
					inputString = input.readUTF();
					System.out.printf("Receive from client: %s\n", inputString);

					/*
					 * ���ͻ��˵���Ϣ���򵥵Ĵ���ͷ���ȥ
					 */
					String outputString = String.format("���������%s", inputString);
					output.writeUTF(outputString);
				} while (!"exit".equals(inputString)); // ����ѡ�exit������ǰ������ΪinputString����Ϊ�գ���ɿ�ָ���쳣

				System.out.printf("Receive 'exit' from client, closing...\n");

				/*
				 * �رմ򿪵���
				 */
				output.close();
				input.close();
			} catch (IOException e) {
				System.err.printf(
						"IOException when handling client request: %s\n",
						e.getMessage());
			} finally {
				if (client != null) {
					try {
						/*
						 * �رտͻ�������
						 */
						client.close();
					} catch (IOException e) {
						System.err.printf(
								"IOException when closing client: %s\n",
								e.getMessage());
					}
				}
			}
		}

	}
}

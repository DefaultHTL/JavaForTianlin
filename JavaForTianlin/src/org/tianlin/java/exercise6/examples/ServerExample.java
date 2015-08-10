package org.tianlin.java.exercise6.examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {
	/*
	 * 服务器的监听端口
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
			 * 在0.0.0.0:30001启动服务器并监听，这里0.0.0.0代表本机所有网络地址。
			 * 例如，一台主机有两张网卡，地址分别是192.168.1.2和10.0.0.2，那么服务器会在192.168.1.2:30001
			 * 和10.0.0.2:30001上同时开始监听。
			 */
			serverSocket = new ServerSocket(PORT);
			System.out.printf("Server starts listening on port:%d\n",
					serverSocket.getLocalPort());
			/*
			 * 循环接受客户端连接请求
			 */
			while (true) {
				/*
				 * 该方法会阻塞主线程，直到有客户端请求到来。
				 */
				Socket clientSocket = serverSocket.accept();
				/*
				 * 打印出对方的IP地址
				 */
				System.out.printf("Connection established from %s\n",
						clientSocket.getInetAddress().getHostAddress());
				/*
				 * 启动新线程处理请求。 主线程继续等待客户端连接。
				 */
				new Thread(new Handler(clientSocket)).start();
			}
		} catch (IOException e) {
			System.err.printf("IOException: %s\n", e.getMessage());
		} finally {
			if (serverSocket != null) {
				try {
					/*
					 * 最后别忘了关闭服务器，释放资源。
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
	 * 一个处理客户端请求的静态类
	 */
	private static class Handler implements Runnable {
		/*
		 * 客户端
		 */
		private Socket client = null;

		public Handler(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				/*
				 * 打开两个流，一个输入一个输出，用于与客户端通信。
				 */
				DataInputStream input = new DataInputStream(
						client.getInputStream());
				DataOutputStream output = new DataOutputStream(
						client.getOutputStream());

				String inputString = null;
				/*
				 * 循环读取客户端消息，如果是‘exit’则退出循环
				 */
				do {
					/*
					 * 按照UTF格式读取客户端消息，这里读取的方式需要与客户端发送方式相同，否则会乱码。
					 */
					inputString = input.readUTF();
					System.out.printf("Receive from client: %s\n", inputString);

					/*
					 * 将客户端的消息做简单的处理就发回去
					 */
					String outputString = String.format("你输入的是%s", inputString);
					output.writeUTF(outputString);
				} while (!"exit".equals(inputString)); // 这里把‘exit’放在前面是因为inputString可能为空，造成空指针异常

				System.out.printf("Receive 'exit' from client, closing...\n");

				/*
				 * 关闭打开的流
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
						 * 关闭客户端连接
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

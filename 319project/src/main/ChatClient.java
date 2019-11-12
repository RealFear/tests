package main;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

	Socket serverSocket;
	String serverHostName = "localhost";
	int serverPortNumber = 4444;
	ChatServerListener sl;
	String user = "user";

	ChatClient(String username) {
		user = username;
        Scanner chat = new Scanner(System.in);
		boolean access = true;

		
		// 1. CONNECT TO THE SERVER
		try {
			serverSocket = new Socket(serverHostName, serverPortNumber);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 2. SPAWN A LISTENER FOR THE SERVER. THIS WILL KEEP RUNNING
		// when a message is received, an appropriate method is called
		sl = new ChatServerListener(this, serverSocket);
		new Thread(sl).start();
		try {
			PrintWriter out = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));
			out.println(user);
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			this.chatmessage(chat);
		}

		
		
	}
		

	public void handleMessage(String cmd, String s) {
		switch (cmd) {
		case "print":
			System.out.println(s);
			break;
		case "exit":
			System.exit(-1);
			break;
		default:
			System.out.println("unknown command received:" + cmd);
		}
	}
	public void chatmessage(Scanner chat) {
		PrintWriter out;
		String mes = ""; // store chat message
		try {
			out = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));
			String message = chat.nextLine();
			out.println(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		ChatClient lc = new ChatClient("");
//	} // end of main method

} // end of ListClient

class ChatServerListener implements Runnable {
	ChatClient lc;
	Scanner in; // this is used to read which is a blocking call

	ChatServerListener(ChatClient chatClient, Socket s) {
		try {
			this.lc = chatClient;
			in = new Scanner(new BufferedInputStream(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) { // run forever
			String cmd = in.next();
			String s = in.nextLine();
			lc.handleMessage(cmd, s);
		}

	}
}

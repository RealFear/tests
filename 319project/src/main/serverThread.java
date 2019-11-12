package main;
import java.io.IOException;

public class serverThread extends Thread {
	String name;

	public serverThread(String s) {
	}

	public serverThread() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try {
			ChatServer chatserver = new ChatServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
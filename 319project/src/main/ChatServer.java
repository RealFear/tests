package main;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	static Chatholder chatmes = new Chatholder();
	public ChatServer() throws IOException {
		// TODO Auto-generated method stub

		ServerSocket serverSocket = null;  // 1. serversocket
		int clientNum = 0; // keeps track of how many clients were created
		String lastmes = "";

		// 1. CREATE A NEW SERVERSOCKET
		try {
			serverSocket = new ServerSocket(4444); // provide MYSERVICE at port 
													// 4444
			System.out.println(serverSocket);
		} catch (IOException e) {
			System.out.println("Could not Chaten on port: 4444");
		}

		// 2. LOOP FOREVER - SERVER IS ALWAYS WAITING TO PROVIDE SERVICE!
		while (true) { // 3.
			Socket clientSocket = null;
			try {

				// 2.1 WAIT FOR CLIENT TO TRY TO CONNECT TO SERVER
				System.out.println("Waiting for client " + (clientNum + 1)
						+ " to connect!");
				clientSocket = serverSocket.accept(); // // 4.

				// 2.2 SPAWN A THREAD TO HANDLE CLIENT REQUEST
				System.out.println("Server got connected to a client"
						+ ++clientNum);
				Thread t = new Thread(new ChatClientHandler(clientSocket, clientNum));
				t.start();
				
				

			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}

			// 2.3 GO BACK TO WAITING FOR OTHER CLIENTS
			// (While the thread that was created handles the connected client's
			// request)

		} // end while loop // end while loop

	}

}
class Chatholder {
	int lastclient = 0;
	String lastmes = "";
	public synchronized void sendMessage(String s, int client) {
		lastmes = s;
		lastclient= client;
		this.notifyAll();
	}
	public synchronized String getMessage() {
		return lastmes;
	}
	public synchronized int getNum() {
		return lastclient;
	}
}

class ChatClientHandler implements Runnable {
	Chatholder chatmes = ChatServer.chatmes;
	Socket s;// this is socket on the server side that connects to the CLIENT
	int num; // keeps track of its number just for identifying purposes
	String user = "user";
	
	ChatClientHandler(Socket s, int n) {
		this.s = s;
		num = n;
	}

	// This is the client handling code
	// This keeps running handling client requests 
	// after initially sending some stuff to the client
	public void run() { 
		BufferedReader in;
		PrintWriter out;
		String lastmes = "";
		
		try {
			// 1. GET SOCKET IN/OUT STREAMS
			in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
			out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
			user = in.readLine();
			// 2. PRINT SOME STUFF TO THE CLIENT
			out.println("print Welcome to the chat!");
			out.flush(); // force the output
			
			// 3. KEEP LISTENING AND RESPONDING TO CLIENT REQUESTS
			int count = 1;
			while (true) {
				//System.out.println("Server - waiting to read");
				if (chatmes.getNum() != num && chatmes.getNum() != 0) {
						out.println("print " + user + ": " + chatmes.getMessage());
						out.flush();
						chatmes.sendMessage("", 0);
				}
				if (in.ready()) {
					String s = in.readLine();
					chatmes.sendMessage(s, num);
					handleRequest(s);
				}
			}
			//out.println("exit done with wishes");
			//out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// This handling code dies after doing all the printing
	} // end of method run()
	
	void handleRequest(String s) {
		//System.out.println("server side: " + user + ": " + s);
	}

} // end of class ClientHandler

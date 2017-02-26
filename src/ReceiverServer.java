import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class ReceiverServer implements Runnable {

	public static final int ServerPort = 9999;
	public static final String ServerIP = "192.168.0.63";
	private static GUI gui;

	@Override
	public void run() {

		try {
			System.out.println("S: Connecting...");
			ServerSocket serverSocket = new ServerSocket(ServerPort);

			while (true) {
				Socket client = serverSocket.accept();
				System.out.println("S: Receiving...");
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
					String str = "";
					
					while(true){
						
						 str = in.readLine();
						
						if(str.compareTo("null") == 0)
							break;
						
						//콘솔로 출력. 
						System.out.println("S: Received: '" + str + "'");
						gui.setBarcodeArea(str);
												
					}
					
					//GUI에 바코드값 출력
					
										
					

					
					//클라이언트로 다시 출력해주는 기능. 
//					PrintWriter out = new PrintWriter(
//							new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
//					out.println("Server Received " + str);
				} catch (Exception e) {
					System.out.println("S: Error");
					e.printStackTrace();
				} finally {
					client.close();
					System.out.println("S: Done.");
				}
			}
		} catch (Exception e) {
			System.out.println("S: Error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Thread desktopServerThread = new Thread(new ReceiverServer());
		desktopServerThread.start();

		gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(500, 400);
		gui.setVisible(true);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		gui.setLocation(dim.width / 2 - gui.getSize().width / 2, dim.height / 2 - gui.getSize().height / 2);

	}
}

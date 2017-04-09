import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

import javax.print.attribute.standard.Finishings;
import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReceiverServer implements Runnable {

	public static final int ServerPort = 9999;
	
	JSONParser parser = new JSONParser();
	String fileName;

	@Override
	public void run() {

		try {
			System.out.println("S: Connecting...");
			ServerSocket serverSocket = new ServerSocket(ServerPort);
			
			GUI.getInstance();

			while (true) {
				System.out.println("S: Receiving...");
				Socket client = serverSocket.accept();

				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

					String str = "";
					String barcodeVal = "";

					while (true) {

						str = in.readLine();

						// 입력 끝나면 null.
						if (str == null)
							break;

						// 들어온 입력이 SO일때 처리.
						try {
							JSONObject obj = (JSONObject) parser.parse(str);

							fileName = obj.get("soNumber").toString();

							saveBarcode(fileName, barcodeVal);

							// SO일경우에는 뒤에꺼 Rx, Tx진행하지말고 걍 넘겨.
							continue;

						} catch (Exception e) {
							e.printStackTrace();
						}

						// 콘솔로 출력.
						System.out.println("S: Received: '" + str + "'");
						GUI.getInstance().setBarcodeArea(str);
						barcodeVal = barcodeVal + str + System.getProperty("line.separator");

					}

					// 클라이언트로 다시 출력해주는 기능.
					// PrintWriter out = new PrintWriter(
					// new BufferedWriter(new
					// OutputStreamWriter(client.getOutputStream())), true);
					// out.println("Server Received " + str);
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

	private void saveBarcode(String soNumber, String barcodeVal) {

		File file = new File("./Barcode\\" + soNumber + ".txt");

		try {
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file);

			writer.print(barcodeVal);
			writer.flush();

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		Thread desktopServerThread = new Thread(new ReceiverServer());
		desktopServerThread.start();

	}
}

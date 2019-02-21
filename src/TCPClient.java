
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import java.net.UnknownHostException;

/*    */
/*    */ public class TCPClient
/*    */ {
	/*    */ public static String connectToCloud(int port, String service)
	/*    */ {
		/* 29 */ Socket socket = null;
		/*    */ try {
			/* 31 */ int serverPort = port;
			/* 32 */ String ip = "localhost";
			/* 33 */ String data = service;
			/*    */
			/* 35 */ socket = new Socket(ip, serverPort);
			/* 36 */ DataInputStream input = new DataInputStream(socket.getInputStream());
			/* 37 */ DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			/*    */
			/* 39 */ output.writeInt(data.length());
			/* 40 */ output.writeBytes(data);
			/* 41 */ output.flush();
			/*    */
			/* 43 */ int len = input.readInt();
			/* 44 */ byte[] inputBytes = new byte[len];
			/* 45 */ for (int i = 0; i < len; i++) {
				/* 46 */ inputBytes[i] = input.readByte();
				/*    */ }
			/* 48 */ String response = new String(inputBytes);
			/* 49 */ return response;
			/*    */ } catch (UnknownHostException e) {
			/* 51 */ System.out.println("Sock:" + e.getMessage());
			/*    */ } catch (EOFException e) {
			/* 53 */ System.out.println("EOF:" + e.getMessage());
			/*    */ } catch (IOException e) {
			/* 55 */ System.out.println("IO:" + e.getMessage());
			/*    */ } finally {
			/* 57 */ if (socket != null)
				/*    */ try {
					/* 59 */ socket.close();
					/*    */ } catch (IOException localIOException5) {
					/*    */ }
			/*    */ }
		/* 63 */ return null;
		/*    */ }
	/*    */ }
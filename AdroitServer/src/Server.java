package adroit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Server {

	public static ServerSocket ss = null;
	public static Socket socket = null;
	private static final int PORT = 3000;
	ObjectInputStream is;
	ObjectOutputStream os;

	public static void main(String[] args) {

		System.out.println("Adroit Server running at " + PORT + " port...");
		try {
			new Server().runServer();
		} catch (ClassNotFoundException | NoSuchAlgorithmException | IOException | InterruptedException e) {

		}

	}

	public void runServer() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InterruptedException {

		try {

			ss = new ServerSocket(PORT, 200);
			AuthenticationImpl authentication = new AuthenticationImpl();

			while (true) {

				socket = ss.accept();
				is = new ObjectInputStream(socket.getInputStream());
				os = new ObjectOutputStream(socket.getOutputStream());

				R request = (R) is.readObject();
				Idea idea = (Idea) request.getIdea();

				R response = new R();

				if (request.getOption().equals("post")) {

					try {
						String r = authentication.post(request.getUsername(), request.getPassword(), idea.getId(),
								idea.getPhrase());
						response.setOption(r);
						os.writeObject(response);
					} catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
							| IllegalBlockSizeException | SQLException e) {

					}

				} else if (request.getOption().equals("get")) {

					try {
						String r = authentication.get(request.getUsername(), request.getPassword(), idea.getId());

						response.setOption(r);
						os.writeObject(response);

					} catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
							| IllegalBlockSizeException e) {

					}

				}
			}

		} catch (SocketException s) {
			ss.close();
			socket.close();
			ss = null;
			socket = null;
			Thread.sleep(3000);
			new Server().runServer();

		} catch (IOException i) {
			ss.close();
			socket.close();
			ss = null;
			socket = null;
			Thread.sleep(3000);
			new Server().runServer();

		}

	}

}

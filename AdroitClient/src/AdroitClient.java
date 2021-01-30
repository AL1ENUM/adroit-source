package adroit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AdroitClient {

	private static final String secret = "Sup3rS3c********";
	static ObjectOutputStream os;
	static ObjectInputStream is;
	static Socket socket;

	public static void main(String[] args)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException, NotBoundException, ClassNotFoundException {

		Cryptor crypt = new Cryptor();
    
		try {

			socket = new Socket("adroit.local", 3000);
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());

			R request = new R();

			Scanner scanner = new Scanner(System.in);

			System.out.println("Enter the username : ");
			String userName = crypt.encrypt(secret, scanner.nextLine());
			
			System.out.println("Enter the password : ");
			String password = crypt.encrypt(secret, scanner.nextLine());
			
			if (userName.equals(crypt.encrypt(secret, "z***"))
					&& password.equals(crypt.encrypt(secret, "god.thunder.*******"))) {

				request.setUsername(userName);
				request.setPassword(password);

				System.out.println("Options [ post | get ] : ");
				String option = scanner.next();
				scanner.nextLine();
				if (option.toLowerCase().equals("post")) {

					
					request.setOption("post");
					System.out.println("Enter your phrase identifier : ");
					String id = crypt.encrypt(secret, scanner.nextLine());

					System.out.println("Enter your phrase : ");
					String phrase = crypt.encrypt(secret, scanner.nextLine());

					Idea idea = new Idea();
					idea.setId(id);
					idea.setPhrase(phrase);
					request.setIdea(idea);
				
					// send POST
					os.writeObject(request);
					// receive RESPONSE
					R responseobj = (R) is.readObject();
					String response = responseobj.getOption();
					System.out.println(response);

				} else if (option.toLowerCase().equals("get")) {

					request.setOption("get");
					System.out.println("Enter the phrase identifier : ");
					String inp = scanner.nextLine();

					String id = crypt.encrypt(secret, inp);

					Idea idea = new Idea();
					idea.setId(id);
					request.setIdea(idea);

					// send GET
					os.writeObject(request);
					// receive RESPONSE
					R responseobj = (R) is.readObject();
					String response = responseobj.getOption();
					System.out.println(response);
				} else {

					System.out.println("Bad option, valid options = get, post");

				}

			} else {

				System.out.print("Wrong username or password");
			}

			scanner.close();

		} catch (RemoteException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

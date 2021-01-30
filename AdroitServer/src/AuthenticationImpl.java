package adroit;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AuthenticationImpl {

	private static final String secret = "Sup3rS3c********";

	protected AuthenticationImpl() {

	}

	public String post(String userName, String password, String id, String phrase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, SQLException {

		Cryptor crypt = new Cryptor();

		userName = crypt.decrypt(secret, userName);
		password = crypt.decrypt(secret, password);

		if ((userName != null && !userName.isEmpty()) && (password != null && !password.isEmpty())) {

			if ((userName.equalsIgnoreCase("z***")) && (password.equalsIgnoreCase("god.thunder.*******"))) {

				id = crypt.decrypt(secret, id);
				phrase = crypt.decrypt(secret, phrase);

				String url = "jdbc:mysql://localhost:3306/";
				String dbName = "dbname";
				String driver = "com.mysql.cj.jdbc.Driver";
				String user = "dbuser";
				String pass = "dbpass";
				try {

					Class.forName(driver);
					Connection conn = DriverManager.getConnection(url + dbName, user, pass);

					String query = "INSERT INTO ideas (id,phrase) values (?,?)";
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, id);
					preparedStmt.setString(2, phrase);
					preparedStmt.execute();

					conn.close();

					return "Your phrase has been inserted into the database.";

				} catch (Exception e) {
					e.printStackTrace();
				}

				return "Database error";
			}
		}
		return "";
	}

	public String get(String userName, String password, String id)
			throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {

		Cryptor crypt = new Cryptor();

		userName = crypt.decrypt(secret, userName);
		password = crypt.decrypt(secret, password);

		if ((userName != null && !userName.isEmpty()) && (password != null && !password.isEmpty())) {

			if ((userName.equalsIgnoreCase("z***")) && (password.equalsIgnoreCase("god.thunder.*******"))) {

				id = crypt.decrypt(secret, id);

				String url = "jdbc:mysql://localhost:3306/";
				String dbName = "db_name";
				String driver = "com.mysql.cj.jdbc.Driver";
				String user = "db_user";
				String pass = "db_pass";
				String row = "";
				try {
					Class.forName(driver);
					Connection conn = DriverManager.getConnection(url + dbName, user, pass);

					Statement st = conn.createStatement();
					String query = "SELECT * FROM  ideas where id = " + id;
					ResultSet res = st.executeQuery(query);
					while (res.next()) {
						String s = res.getString("phrase");
						row = row + " " + s;
					}
					conn.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				return row;
			}
		}
		return "Unexpected error";

	}

}

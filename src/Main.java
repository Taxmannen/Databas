import java.sql.*;
public class Main {
	
	public static Connection connection;
	public static Statement statement;
	
	/*Open a connection to the database and creates the gui.
	 */
	public static void main(String[] args) {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "user" , "password");
			statement = connection.createStatement();
		}
		catch (Exception exc) {
			System.out.println("ERROR! Your connection to the database does not work!");
		}
		new GUI();
	}
}

import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseHandler {
	
	/*Updates the list that holds the data.
	 */
	public void updateList() {
		try {
			ResultSet result  = Main.statement.executeQuery("SELECT * FROM books");
			GUI.TableModel.setRowCount(0);
			while(result.next()) {
				GUI.TableModel.addRow(new String[]{result.getString("name"), result.getString("genre"), result.getString("author"), result.getString("serienr")});
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Could not update list! " + e.getMessage());
		}
	}
	
	/* Adds a new book to the database.
	 * @param name - the name of the book,.
	 * @param genre - the genre of the book.
	 * @param author - the author of the book.
	 * @param serienr - the serienr of the book.
	 */
	public boolean addToDatabase(String name, String genre, String author, String serienr) {
		try {
			ResultSet result = Main.statement.executeQuery("SELECT serienr FROM books WHERE serienr = '"+serienr+"'");
			if(result.next()) {
				return false;
			}
			Main.statement.executeUpdate("INSERT INTO books(name, genre, author, serienr) VALUES ('"+ name +"','"+ genre +"', '"+ author +"', '"+ serienr +"')");
			return true;
		} catch (Exception e) {
			System.err.println("Could not add to database!" + e.getMessage());
		}
		return false;
	}
	
	/* Edits a book that already exists in the database.
	 * @param oldName - the name before the edit.
	 * @param name - the name of the book.
	 * @param genre - the genre of the book.
	 * @param author - the author of the book.
	 * @param serienr - the serienr of the book.
	 */
	public void editFromDatabase(String oldName, String name, String genre, String author, String serienr) {
		try {
			Main.statement.executeUpdate("UPDATE books SET name='"+name+"', genre= '"+genre+"', author='"+author+"', serienr='"+serienr+"' where name = '"+oldName+"'");
		} catch(Exception e) {
			System.err.println("Could not edit in the database!" + e.getMessage());
		}
	}
	
	/* Deletes a current book from the database.
	 * @param serienr - the serienumber of the book you are trying to delete.
	 */
	public void deleteFromDatabase(int serienr) {
		try {
			Main.statement.executeUpdate("DELETE FROM books WHERE serienr = '"+serienr+"'");
		} catch(Exception e) {
			System.err.println("Cound not delete from  the database!" + e.getMessage());
		}
	}
	
	/* Sorts the library in the way you picked.
	 * @param type - the type that you are trying to sort.
	 */
	public void sortDatabase(String type) {
		try {
			ResultSet result = Main.statement.executeQuery("SELECT * FROM books ORDER BY "+type+"");
			GUI.TableModel.setRowCount(0);
			while(result.next()) {
				GUI.TableModel.addRow(new String[] { result.getString("name"), result.getString("genre"), result.getString("author"), result.getString("serienr")});
			}
		} catch (Exception e) {
			System.err.println("Could not order by " + type);
		}
	}
	
	/* Shows the genre you picked.
	 * @param type - the type that you are trying to show.
	 */
	public void showGenre(String type) {
		try {
			ResultSet result = Main.statement.executeQuery("SELECT * FROM books WHERE genre = '"+type+"'");
			GUI.TableModel.setRowCount(0);
			while(result.next()) {
				GUI.TableModel.addRow(new String[] { result.getString("name"), result.getString("genre"), result.getString("author"), result.getString("serienr")});
			}
		} catch (Exception e) {
			System.err.println("Could not order by " + type);
		}
	}
	
	/* Show the books you are searching for.
	 * @param querty - the thing you searched for. 
	 */
	public void searchDatabase(String query) {
		try {
			ResultSet result = Main.statement.executeQuery("SELECT * FROM books WHERE name LIKE '%" + query + "%' OR genre LIKE '%" + query + "%' OR author LIKE '%" + query + "%'OR serienr LIKE '%" + query + "%'");
			GUI.TableModel.setRowCount(0);
			while(result.next()) {
				GUI.TableModel.addRow(new String[] { result.getString("name"), result.getString("genre"), result.getString("author"), result.getString("serienr")});
			}
		} catch(Exception e) {
			System.err.println("Cound not search for " + query);
		}
	}
	
	/* Looks for the current genres that are in the database.
	 * returns the genrelist.
	 */
	public ArrayList<String>getGenres() {
		try{
			ArrayList<String>genres = new ArrayList<String>();
			ResultSet result = Main.statement.executeQuery("SELECT genre FROM books");
			while(result.next()) {
				genres.add(result.getString("genre"));
			}
			for(int i = 0; i < genres.size(); i++) {
				for(int d = 0; d < genres.size(); d++) {
					if(i != d && genres.get(i).equals(genres.get(d))) {
						genres.remove(i);
					}
				}
			}
			return genres;
			
		} catch(Exception e) {
			System.err.println("Could not get the genres!");
		}
		return null;
	}
}

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GUI extends JFrame  {
	
	public static DefaultTableModel TableModel;
	private JTable table;
	private JButton add_button, delete_button, edit_button;
	private DatabaseHandler dbhandler = new DatabaseHandler();
	private Font myFont = new Font ("Helvetica", 1, 20);
	private ImageIcon icon = new ImageIcon("assets/Book.png");
	
	/*The constructor
	 */
	public GUI() {
		setSize(1030, 760);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Library");
		setLayout(null);
		Image Image = icon.getImage();
		Image Image2 = Image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(Image2);
		add_components();
		dbhandler.sortDatabase("name");
		setVisible(true);
	}
	
	/* Adds the components to the screen. 
	 */
	private void add_components() {
		addText();
		
		TableModel = new DefaultTableModel();
		TableModel.addColumn("Name");
		TableModel.addColumn("Genre");
		TableModel.addColumn("Author");
		TableModel.addColumn("Serie Nr");
		
		table = new JTable(TableModel);
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(60, 115, 650, 550);
		add(scroll);
		
		add_button = new JButton("Add");
		add_button.setBounds(800, 245, 150, 70);
		add_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField name = new JTextField();
				JTextField genre = new JTextField();
				JTextField author = new JTextField();
				JTextField serienr = new JTextField();
				
				Object[] message = {
					"Name:", name,
					"Genre:", genre,
					"Author:", author,
					"Serie Nr:", serienr
				};
				JOptionPane.showConfirmDialog(null, message, "Add Entry", JOptionPane.OK_CANCEL_OPTION, 0, icon);
				if(!name.getText().equals("")) {
				     if(dbhandler.addToDatabase(name.getText(), genre.getText(), author.getText(), serienr.getText())) {
				    	 dbhandler.updateList();
				     } else {
				    	 JOptionPane.showMessageDialog(null, "Wrong Serie Nr");
				     }
				}
			}
		});
		add(add_button);
		
		edit_button = new JButton("Edit");
		edit_button.setBounds(800, 370, 150, 70);
		edit_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedColumn() >= 0) {
					JTextField name = new JTextField((String)TableModel.getValueAt(table.getSelectedRow(), 0));
					JTextField genre = new JTextField((String)TableModel.getValueAt(table.getSelectedRow(), 1));
					JTextField author = new JTextField((String)TableModel.getValueAt(table.getSelectedRow(), 2));
					JTextField serienr = new JTextField((String)TableModel.getValueAt(table.getSelectedRow(), 3));
					Object[] message = {
						"Name", name,
						"Genre", genre,
						"Author", author,
						"Serie Nr", serienr
					};
					JOptionPane.showConfirmDialog(null, message, "Edit", JOptionPane.OK_CANCEL_OPTION, 0, icon);
					if(!name.getText().equals("")){
						dbhandler.editFromDatabase((String)TableModel.getValueAt(table.getSelectedRow(), 0), name.getText(), genre.getText(), author.getText(), serienr.getText());
						dbhandler.updateList();
					}
				}
			}
		});
		add(edit_button);
		
		delete_button = new JButton("Delete");
		delete_button.setBounds(800, 495, 150, 70);
		delete_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedColumn() >= 0) {
					int answer = JOptionPane.showConfirmDialog(null, "Are You Sure?");
					if(answer == 0) {
						dbhandler.deleteFromDatabase(Integer.valueOf((String) TableModel.getValueAt(table.getSelectedRow(), 3)));
						dbhandler.updateList();
					}
				}
			}
		});
		add(delete_button);
		
		JComboBox<String> show = new JComboBox<String>();
		show.addItem("Show All");
		for(String a:dbhandler.getGenres()) {
			show.addItem(a);
		}
		show.setBounds(460, 45, 150, 40);
		show.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String type = (String) show.getSelectedItem();
				if(!type.equals("Show All")) {
					dbhandler.showGenre(type);
				} else {
					dbhandler.updateList();
				}
			}
		});
		add(show);
		
		JComboBox<String> sortby = new JComboBox<String>();
		sortby.addItem("Name");
		sortby.addItem("Genre");
		sortby.addItem("Author");
		sortby.addItem("Serie Nr");
		sortby.setBounds(150, 45, 150, 40);
		sortby.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String type = (String) sortby.getSelectedItem();
				type = type.toLowerCase().replace(" ", "");
				dbhandler.sortDatabase(type);
			}
		});
		add(sortby);
		
		JTextField searchField = new JTextField();
		searchField.setBounds(775, 100, 200, 30);
		searchField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(!searchField.getText().equals("")) {
					dbhandler.searchDatabase(searchField.getText());
				} else {
					dbhandler.updateList();
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) { }
		});
		add(searchField);
	}
	
	/*Adds the text to the screen
	 */
	private void addText() {
		JLabel searchText = new JLabel("Search");
		searchText.setFont(myFont);
		searchText.setBounds(840, 60, 160, 30);
		add(searchText);
		
		JLabel sortText = new JLabel("Sort by");
		sortText.setFont(myFont);
		sortText.setBounds(190, 5, 160, 30);
		add(sortText);
		
		JLabel showText = new JLabel("Show");
		showText.setFont(myFont);
		showText.setBounds(505, 5, 160, 30);
		add(showText);
	}
}

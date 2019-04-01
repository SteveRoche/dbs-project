package movies.components;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import movies.Database;

public class Dashboard extends JPanel {
	static final String location = "DASHBOARD";
	private static final int pad = 10;

	SpringLayout layout = new SpringLayout();
	NavBar navbar = new NavBar();
	JTextField tfSearch = new JTextField(15);
	JButton btnSearch = new JButton("Search");

	PreparedStatement stmtSearch;

	Dashboard() {
		try {
			stmtSearch = Database.conn().prepareStatement("SELECT name FROM Movie WHERE LOWER(name) LIKE LOWER(?)");
			stmtSearch.setString(1, "%" + "DaWn" + "%");
			ResultSet rs = stmtSearch.executeQuery();
			while (rs.next()) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		buildUI();
	}

	private void buildUI() {
		setLayout(layout);
		add(navbar);
		add(tfSearch);
		add(btnSearch);
		layout.putConstraint(SpringLayout.WEST, tfSearch, pad, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, tfSearch, pad, SpringLayout.SOUTH, navbar);

		layout.putConstraint(SpringLayout.WEST, btnSearch, 0, SpringLayout.EAST, tfSearch);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, btnSearch, 0, SpringLayout.VERTICAL_CENTER, tfSearch);

		setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
}

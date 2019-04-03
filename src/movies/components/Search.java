package movies.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import movies.Database;

public class Search extends JPanel {
	private static final int pad = 10;

	SpringLayout layout = new SpringLayout();
	JTextField tfSearch = new JTextField(15);
	JButton btnSearch = new JButton("Search");
	JButton btnVis = new JButton("Toggle");
	JTextField tfVis = new JTextField(15);

	PreparedStatement stmtSearch, stmtAll;

	Search() {

		try {
			stmtSearch = Database.conn().prepareStatement("SELECT name FROM Movie WHERE LOWER(name) LIKE LOWER(?)");
			stmtAll = Database.conn().prepareStatement("SELECT name FROM Movie");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					String query = tfSearch.getText();
					stmtSearch.setString(1, "%" + query + "%");
					ResultSet rs = stmtSearch.executeQuery();
				} catch (SQLException e) {
					// TODO: Proper error handling
				}
			}
		});

		btnVis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				tfVis.setVisible(!tfVis.isVisible());
			}
		});

		buildUI();
	}

	private void buildUI() {
//		setLayout(layout);
		add(tfSearch);
		add(btnSearch);
		add(btnVis);
		add(tfVis);
		tfVis.setVisible(false);

		layout.putConstraint(SpringLayout.WEST, tfSearch, pad, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.WEST, btnSearch, 0, SpringLayout.EAST, tfSearch);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, btnSearch, 0, SpringLayout.VERTICAL_CENTER, tfSearch);

		layout.putConstraint(SpringLayout.NORTH, btnVis, 10, SpringLayout.SOUTH, tfSearch);
		layout.putConstraint(SpringLayout.WEST, btnVis, 0, SpringLayout.SOUTH, tfSearch);

		layout.putConstraint(SpringLayout.NORTH, tfVis, 50, SpringLayout.SOUTH, tfSearch);
		layout.putConstraint(SpringLayout.WEST, tfVis, 0, SpringLayout.SOUTH, tfSearch);

		setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 430);
	}
}

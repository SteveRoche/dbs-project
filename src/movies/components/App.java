package movies.components;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;

import movies.Database;

public class App extends JFrame {
	public App() {
		Database.conn();
		Container pane = getContentPane();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				// Cleanup
				try {
					Database.conn().close();
				} catch (SQLException e) {
				}
				System.exit(0);
			}
		});

		setTitle("DBS App");
		setLayout(new CardLayout());

		pane.add(LoginScreen.location, new LoginScreen());
		pane.add(Dashboard.location, new Dashboard());
		pane.add(RegisterScreen.location, new RegisterScreen());

		pack();
		center();
		setResizable(false);
		setVisible(true);
		setState(NORMAL);
	}

	private void center() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
	}
}
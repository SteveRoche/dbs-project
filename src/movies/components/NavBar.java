package movies.components;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import movies.Session;
import movies.SessionObserver;

public class NavBar extends JPanel implements SessionObserver {
	SpringLayout layout = new SpringLayout();
	JLabel lblUser = new JLabel();
	JButton btnLogout = new JButton("Logout");

	final int pad = 10;

	NavBar() {
		Session.attach(this);
		buildUI();
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container frame = getParent().getParent();
				((CardLayout) frame.getLayout()).show(frame, LoginScreen.location);
				((JFrame) frame).pack();
				Session.clear();
			}
		});
	}

	private void buildUI() {
		setLayout(layout);
		add(lblUser);
		add(btnLogout);

		layout.putConstraint(SpringLayout.NORTH, lblUser, pad, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, lblUser, pad, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, btnLogout, pad, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, btnLogout, 0, SpringLayout.EAST, this);
		setVisible(true);
	}

	public void update() {
		lblUser.setText("Logged in as " + Session.getUser());
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 40);
	}
}
package movies.components;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import movies.Database;
import movies.Session;

public class LoginScreen extends JPanel {
	final static String location = "LOGINSCREEN";
	final int margin = 10;

	SpringLayout layout = new SpringLayout();
	JLabel lblUsername = new JLabel("Username");
	JLabel lblPassword = new JLabel("Password");
	JLabel lblError = new JLabel();
	JTextField tfUsername = new JTextField(15);
	JPasswordField pfPassword = new JPasswordField(15);
	JButton btnLogin = new JButton("Login");
	JButton btnRegister = new JButton("Register");

	PreparedStatement stmtLogin;

	LoginScreen() {
		try {
			stmtLogin = Database.conn().prepareStatement("SELECT * FROM MUser WHERE username=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				boolean validUsername = false;
				boolean validPassword = false;

				String username = tfUsername.getText();
				String password = String.valueOf(pfPassword.getPassword());

				try {
					stmtLogin.setString(1, username);
					ResultSet rs = stmtLogin.executeQuery();
					if (rs.next() == true) {
						validUsername = true;
						String storedPassword = rs.getString("password");
						if (storedPassword.equals(password)) {
							validPassword = true;
						} else {
							lblError.setText("Invalid password");
							pfPassword.setText("");
						}
					} else {
						lblError.setText("Invalid username");
						tfUsername.setText("");
						pfPassword.setText("");
					}
				} catch (SQLException e) {
					lblError.setText("An error occurred");
					e.printStackTrace();
				}

				if (validUsername && validPassword) {
					Session.init(username);
					redirect(Dashboard.location);
					resetForm();
				}
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				redirect(RegisterScreen.location);
				resetForm();
			}
		});
		buildGUI();
	}

	private void redirect(String location) {
		Container frame = getParent();
		((CardLayout) frame.getLayout()).show(frame, location);
	}

	private void resetForm() {
		tfUsername.setText("");
		pfPassword.setText("");
		lblError.setText("");
	}

	private void buildGUI() {
		setLayout(layout);
		add(lblUsername);
		add(tfUsername);
		add(lblPassword);
		add(pfPassword);
		add(lblError);
		add(btnLogin);
		add(btnRegister);

		lblError.setForeground(Color.RED);

		layout.putConstraint(SpringLayout.WEST, lblUsername, margin, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, lblUsername, margin, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, tfUsername, margin, SpringLayout.EAST, lblUsername);
		layout.putConstraint(SpringLayout.BASELINE, tfUsername, 0, SpringLayout.BASELINE, lblUsername);

		layout.putConstraint(SpringLayout.WEST, lblPassword, margin, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, lblPassword, margin, SpringLayout.SOUTH, lblUsername);

		layout.putConstraint(SpringLayout.WEST, pfPassword, margin, SpringLayout.EAST, lblUsername);
		layout.putConstraint(SpringLayout.BASELINE, pfPassword, 0, SpringLayout.BASELINE, lblPassword);

		layout.putConstraint(SpringLayout.WEST, lblError, margin, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, lblError, margin, SpringLayout.SOUTH, lblPassword);

		layout.putConstraint(SpringLayout.WEST, btnRegister, margin, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.BASELINE, btnRegister, 0, SpringLayout.BASELINE, btnLogin);

		layout.putConstraint(SpringLayout.EAST, btnLogin, 0, SpringLayout.EAST, pfPassword);
		layout.putConstraint(SpringLayout.NORTH, btnLogin, margin, SpringLayout.SOUTH, lblError);

		setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(242, 120);
	}
}

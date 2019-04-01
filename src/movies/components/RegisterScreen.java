package movies.components;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import movies.Database;

public class RegisterScreen extends JPanel {
	public static final String location = "REGISTER";
//	private final int pad = 10;

//	SpringLayout layout = new SpringLayout();
	GroupLayout layout = new GroupLayout(this);

	JLabel lblUsername = new JLabel("Username");
	JLabel lblPassword = new JLabel("Password");
	JLabel lblConfirm = new JLabel("Confirm Password");
	JLabel lblMessage = new JLabel();

	JTextField tfUsername = new JTextField(15);
	JPasswordField pfPassword = new JPasswordField(15);
	JPasswordField pfConfirm = new JPasswordField(15);

	JButton btnRegister = new JButton("Register");
	JButton btnLogin = new JButton("<< Back to Login");

	PreparedStatement stmtAvailability, stmtRegister;

	RegisterScreen() {
		try {
			stmtAvailability = Database.conn().prepareStatement("SELECT username FROM MUser WHERE username=?");
			stmtRegister = Database.conn().prepareStatement("INSERT INTO MUser(username, password) VALUES(?,?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redirect(LoginScreen.location);
				resetForm();
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String username = tfUsername.getText();
				String password = String.valueOf(pfPassword.getPassword());
				String confirm = String.valueOf(pfConfirm.getPassword());

				if (username.isEmpty()) {
					resetForm();
					showError("Username must not be empty!");
					return;
				}
				if (password.isEmpty()) {
					showError("Password must not be empty!");
					return;
				}
				if (confirm.isEmpty()) {
					showError("Please confirm password!");
					return;
				}
				if (!password.equals(confirm)) {
					showError("Passwords do not match!");
					pfPassword.setText("");
					pfConfirm.setText("");
					return;
				}

				try {
					stmtAvailability.setString(1, username);
					ResultSet rsUsername = stmtAvailability.executeQuery();
					if (rsUsername.next() == true) {
						showError("Username is already taken!");
						tfUsername.setText("");
						return;
					}
					stmtRegister.setString(1, username);
					stmtRegister.setString(2, password);
					stmtRegister.execute();
					resetForm();
					showMessage("Registered successfully!");
				} catch (SQLException e) {
					lblMessage.setText("An error occurred");
					e.printStackTrace();
				}
			}
		});

		buildUI();
	}

	private void showError(String error) {
		lblMessage.setForeground(Color.RED);
		lblMessage.setText(error);
	}

	private void showMessage(String message) {
		lblMessage.setForeground(Color.BLACK);
		lblMessage.setText(message);
	}

	private void buildUI() {
//		add(lblUsername);
//		add(tfUsername);
//
//		add(lblPassword);
//		add(pfPassword);
//
//		add(lblConfirm);
//		add(pfConfirm);
//
//		add(lblMessage);
//		add(btnRegister);
//		add(btnLogin);

		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup().addComponent(lblUsername).addComponent(tfUsername))
				.addGroup(layout.createSequentialGroup().addComponent(lblPassword).addComponent(pfPassword))
				.addGroup(layout.createSequentialGroup().addComponent(lblConfirm).addComponent(pfConfirm)));

		layout.setVerticalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup().addComponent(lblUsername).addComponent(lblPassword)
						.addComponent(lblConfirm))
				.addGroup(layout.createSequentialGroup().addComponent(tfUsername).addComponent(pfPassword)
						.addComponent(pfConfirm)));

		// Labels horizontal
//		layout.putConstraint(SpringLayout.WEST, lblUsername, pad, SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.WEST, lblPassword, pad, SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.WEST, lblConfirm, pad, SpringLayout.WEST, this);
//
//		// Labels vertical
//		layout.putConstraint(SpringLayout.NORTH, lblUsername, pad, SpringLayout.NORTH, this);
//		layout.putConstraint(SpringLayout.NORTH, lblPassword, pad, SpringLayout.SOUTH, lblUsername);
//		layout.putConstraint(SpringLayout.NORTH, lblConfirm, pad, SpringLayout.SOUTH, lblPassword);
//
//		// Inputs vertical
//		layout.putConstraint(SpringLayout.BASELINE, tfUsername, 0, SpringLayout.BASELINE, lblUsername);
//		layout.putConstraint(SpringLayout.BASELINE, pfPassword, 0, SpringLayout.BASELINE, lblPassword);
//		layout.putConstraint(SpringLayout.BASELINE, pfConfirm, 0, SpringLayout.BASELINE, lblConfirm);
//
//		// Inputs horizontal
//		layout.putConstraint(SpringLayout.WEST, pfConfirm, pad, SpringLayout.EAST, lblConfirm);
//		layout.putConstraint(SpringLayout.EAST, tfUsername, 0, SpringLayout.EAST, pfConfirm);
//		layout.putConstraint(SpringLayout.EAST, pfPassword, 0, SpringLayout.EAST, pfConfirm);
//
//		// Message
//		layout.putConstraint(SpringLayout.WEST, lblMessage, pad, SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.NORTH, lblMessage, pad, SpringLayout.SOUTH, lblConfirm);
//
//		// Back to login button
//		layout.putConstraint(SpringLayout.WEST, btnLogin, pad, SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.NORTH, btnLogin, pad, SpringLayout.SOUTH, lblMessage);
//
//		// Register button
//		layout.putConstraint(SpringLayout.NORTH, btnRegister, pad, SpringLayout.SOUTH, lblMessage);
//		layout.putConstraint(SpringLayout.EAST, btnRegister, 0, SpringLayout.EAST, pfConfirm);
		setVisible(true);
	}

	private void resetForm() {
		tfUsername.setText("");
		pfPassword.setText("");
		pfConfirm.setText("");
		lblMessage.setText("");
	}

	private void redirect(String location) {
		Container parent = getParent();
		((CardLayout) parent.getLayout()).show(parent, location);
	}
}
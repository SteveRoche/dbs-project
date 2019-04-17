package movies.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import movies.Database;
import movies.Session;
import movies.SessionObserver;
import movies.models.Movie;

public class DetailsTab extends JPanel implements SessionObserver {
	Movie movie;
	BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
	JLabel lblName = new JLabel();
	JLabel lblDescription = new JLabel();
	JTextArea taActors = new JTextArea();
	JLabel lblDirector = new JLabel();
	JTextField tfRate = new JTextField();
	JButton btnRate = new JButton("Rate");
	Box b = Box.createVerticalBox();
	CallableStatement stmtRate;

	DetailsTab() {
		Session.attach(this);
		try {
			stmtRate = Database.conn().prepareCall("BEGIN rate(?, ?, ?); END;");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btnRate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (tfRate.getText() != "") {
					float rating = Float.parseFloat(tfRate.getText());
					try {
						System.out.println(Session.getUser());
						stmtRate.setString(1, Session.getUser());
						stmtRate.setInt(2, movie.id);
						stmtRate.setFloat(3, rating);
						stmtRate.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		buildUI();
	}

	@Override
	public void update() {
		if (Session.getCurrentMovie() != null) {
			movie = Movie.details(Session.getCurrentMovie());
			lblName.setText("Name: " + movie.name);
			lblDescription.setText("Description: " + movie.description);
			taActors.setText("Actors: " + String.join(", ", movie.actors));
		}
	}

	private void buildUI() {
		setLayout(layout);
		b.add(lblName);
		b.add(lblDescription);
		b.add(taActors);
		b.add(tfRate);
		b.add(btnRate);
		add(b);
		taActors.setWrapStyleWord(true);
		taActors.setLineWrap(true);
		taActors.setOpaque(false);
		taActors.setEditable(false);
		taActors.setFocusable(false);
		taActors.setBackground(UIManager.getColor("Label.background"));
		taActors.setFont(UIManager.getFont("Label.font"));
		taActors.setBorder(UIManager.getBorder("Label.border"));
		setVisible(true);
	}

}

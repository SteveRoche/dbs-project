package movies.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import movies.Session;
import movies.models.Movie;

public class MoviePanel extends JPanel {
	public Movie movie;

	FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
	JLabel lblName = new JLabel();
	JButton btnMore = new JButton("More");

	MoviePanel(Movie movie) {
		btnMore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JTabbedPane tabs = (JTabbedPane) getParent().getParent().getParent().getParent().getParent();
				tabs.setSelectedIndex(1);
				Session.setCurrentMovie(movie.id);
			}
		});

		this.movie = movie;
		buildUI();
	}

	private void buildUI() {
		setLayout(layout);
		add(lblName);
		add(btnMore);
		lblName.setText(movie.name);
		setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 50);
	}
}
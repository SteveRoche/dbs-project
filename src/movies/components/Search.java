package movies.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import movies.models.Movie;

public class Search extends JPanel {
	private static final int pad = 10;

	SpringLayout layout = new SpringLayout();
	JPanel moviesContainer = new JPanel();
	JTextField tfSearch = new JTextField(15);
	JButton btnSearch = new JButton("Search");
	JScrollPane sp = new JScrollPane(moviesContainer);

	Search() {
		Movie.all().stream().forEachOrdered(m -> {
			moviesContainer.add(new MoviePanel(m));
		});

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String query = tfSearch.getText();
				moviesContainer.removeAll();
				Movie.search(query).stream().forEachOrdered(m -> {
					moviesContainer.add(new MoviePanel(m));
				});
				moviesContainer.revalidate();
				moviesContainer.repaint();
			}
		});

		buildUI();
	}

	private void buildUI() {
		setLayout(layout);
		add(tfSearch);
		add(btnSearch);
		add(sp);
		moviesContainer.setLayout(new BoxLayout(moviesContainer, BoxLayout.PAGE_AXIS));
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		layout.putConstraint(SpringLayout.NORTH, tfSearch, pad, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, tfSearch, pad, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.BASELINE, btnSearch, 0, SpringLayout.BASELINE, tfSearch);
		layout.putConstraint(SpringLayout.WEST, btnSearch, 0, SpringLayout.EAST, tfSearch);

		layout.putConstraint(SpringLayout.NORTH, sp, pad, SpringLayout.SOUTH, tfSearch);
		layout.putConstraint(SpringLayout.EAST, sp, -pad, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, sp, -pad, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, sp, pad, SpringLayout.WEST, this);

		setVisible(true);
		sp.setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 430);
	}
}

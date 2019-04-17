package movies.components;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import movies.Database;
import movies.Session;
import movies.SessionObserver;
import movies.models.Movie;

public class RecommendTab extends JPanel implements SessionObserver {
	private PreparedStatement stmtActor;
	private static int pad = 10;

	SpringLayout layout = new SpringLayout();
	JPanel moviesContainer = new JPanel();
	JScrollPane sp = new JScrollPane(moviesContainer);

	RecommendTab() {
		Session.attach(this);
		try {
			stmtActor = Database.conn().prepareStatement(
					"with local as (select actor_id from acted where movie_id in (select movie_id from rated where username=? and rating >=7)) select id, name from movie where id in (select movie_id from acted where actor_id = some(select actor_id from local))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		buildUI();
	}

	private void buildUI() {
		setLayout(layout);
		add(sp);
		moviesContainer.setLayout(new BoxLayout(moviesContainer, BoxLayout.PAGE_AXIS));
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		layout.putConstraint(SpringLayout.NORTH, sp, pad, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, sp, -pad, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, sp, -pad, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, sp, pad, SpringLayout.WEST, this);

		setVisible(true);
		sp.setVisible(true);
	}

	private ArrayList<Movie> getActorRecs() {
		ArrayList<Movie> recs = new ArrayList<>();
		try {
			stmtActor.setString(1, Session.getUser());
			ResultSet rs = stmtActor.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.id = rs.getInt("id");
				movie.name = rs.getString("name");
				recs.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recs;
	}

	public void update() {
		moviesContainer.removeAll();
		if (Session.getUser() != null) {
			getActorRecs().stream().forEachOrdered(m -> {
				moviesContainer.add(new MoviePanel(m));
			});
		}
	}
}

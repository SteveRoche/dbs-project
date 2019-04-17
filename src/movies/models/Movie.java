package movies.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import movies.Database;

public class Movie {
	public int id;
	public String name;
	public String description;
	public String director;
	public Date release;
	public ArrayList<String> actors;

	private static PreparedStatement stmtByID, stmtSearch, stmtAll, stmtActors, stmtDirector;

	static {
		try {
			stmtByID = Database.conn().prepareStatement("SELECT * from Movie WHERE id=?");
			stmtSearch = Database.conn()
					.prepareStatement("SELECT id, name FROM Movie WHERE UTL_MATCH.EDIT_DISTANCE(name, ?) < 6");
			stmtAll = Database.conn().prepareStatement("SELECT id, name FROM Movie");

			stmtActors = Database.conn()
					.prepareStatement("SELECT name FROM Acted INNER JOIN Actor ON id=actor_id WHERE movie_id=?");

			stmtDirector = Database.conn().prepareStatement(
					"SELECT D.name FROM Director D INNER JOIN Movie M ON director_id=D.id WHERE M.id=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Movie> all() {
		ArrayList<Movie> movies = new ArrayList<>();
		try {
			ResultSet rs = stmtAll.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.id = rs.getInt("id");
				movie.name = rs.getString("name");
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}

	public static ArrayList<Movie> search(String query) {
		ArrayList<Movie> movies = new ArrayList<>();
		try {
			stmtSearch.setString(1, "%" + query + "%");
			ResultSet rs = stmtSearch.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.id = rs.getInt("id");
				movie.name = rs.getString("name");
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movies;
	}

	public static Movie details(int id) {
		Movie movie = new Movie();
		try {
			stmtByID.setInt(1, id);
			ResultSet rs = stmtByID.executeQuery();
			while (rs.next()) {
				movie.id = rs.getInt("id");
				movie.name = rs.getString("name");
				movie.description = rs.getString("description");
				movie.release = rs.getDate("release_date");

				stmtDirector.setInt(1, id);
				ResultSet rsDir = stmtDirector.executeQuery();
				while (rsDir.next()) {
					movie.director = rs.getString("name");
				}

				movie.actors = new ArrayList<>();
				stmtActors.setInt(1, id);
				ResultSet rsAct = stmtActors.executeQuery();
				while (rsAct.next()) {
					movie.actors.add(rsAct.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}

	public static void rate(int id, float rating) {

	}
}

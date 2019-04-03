package movies.components;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Dashboard extends JPanel {
	static final String location = "DASHBOARD";
	private static final int pad = 10;

	BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

	NavBar navbar = new NavBar();
	JTabbedPane tabs = new JTabbedPane();
	Search search = new Search();

	Dashboard() {
		add(navbar);
		add(tabs);
		tabs.addTab("Search", search);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
}

class Movie {
	public String name;
	public Date releaseDate;
	public String director;
	public ArrayList<String> actors;
}

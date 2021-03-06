package movies.components;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Dashboard extends JPanel {
	static final String location = "DASHBOARD";

	BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

	NavBar navbar = new NavBar();
	JTabbedPane tabs = new JTabbedPane();
	Search search = new Search();
	DetailsTab details = new DetailsTab();
	RecommendTab recommend = new RecommendTab();

	Dashboard() {
		add(navbar);
		add(tabs);
		tabs.addTab("Search", search);
		tabs.addTab("Details", details);
		tabs.addTab("Recommendations", recommend);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
}

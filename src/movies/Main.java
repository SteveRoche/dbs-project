package movies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import movies.components.App;

public class Main extends JFrame {
	public static void main(String[] args) throws SQLException {
		File dotenv = new File(".env");
		String dbUsername = "";
		String dbPassword = "";

		try {
			if (dotenv.createNewFile()) {
				FileWriter fw = new FileWriter(dotenv);
				Scanner sc = new Scanner(System.in);

				System.out.println("Creating new .env file");

				System.out.print("DB Username: ");
				dbUsername = sc.nextLine();

				System.out.print("DB Password: ");
				dbPassword = sc.nextLine();

				fw.write("DB_USERNAME=" + dbUsername + '\n');
				fw.write("DB_PASSWORD=" + dbPassword + '\n');

				sc.close();
				fw.close();
			} else {
				BufferedReader br = new BufferedReader(new FileReader(dotenv));
				dbUsername = br.readLine().split("=")[1];
				dbPassword = br.readLine().split("=")[1];
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		Database.configure(dbUsername, dbPassword);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new App();
			}
		});
	}
}

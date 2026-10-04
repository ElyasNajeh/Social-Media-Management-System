package FxSocial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadFriends {
	Alerts a = new Alerts();
	EnterSystem es = Main.es;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Friends File");
		fc.setInitialDirectory(AppFiles.getLoadDirectory());
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text and CSV files", "*.txt", "*.csv"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			a.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try {
			List<List<String>> records = CsvUtils.readRecords(f.toPath());
			for (Object item : es.tableData) {
				((User) item).getFriend().clear();
			}
			int skippedRows = 0;
			for (List<String> data : records) {
				try {
					if (data.isEmpty()) {
						throw new IllegalArgumentException();
					}
					int userId = Integer.parseInt(data.get(0));
					User user = Main.findUserById(userId);
					if (user == null) {
						throw new IllegalArgumentException();
					}
					List<User> friends = new ArrayList<>();
					for (int j = 1; j < data.size(); j++) {
						User friend = Main.findUserById(Integer.parseInt(data.get(j)));
						if (friend == null || friend == user || friends.contains(friend)) {
							throw new IllegalArgumentException();
						}
						friends.add(friend);
					}
					for (User friend : friends) {
						user.addFriend(friend);
					}
				} catch (IllegalArgumentException e) {
					skippedRows++;
				}
			}
			if (Main.es != null && Main.es.tableView1 != null) {
				Main.es.tableView1.refresh();
			}
			String message = "Friendships loaded successfully.";
			if (skippedRows > 0) {
				message += " Skipped " + skippedRows + " invalid row(s).";
			}
			a.InfoAlert("Success", message);
		} catch (IOException e) {
			a.ErrorAlert("Error", "Could not read the selected file: " + e.getMessage());
		}
	}
}

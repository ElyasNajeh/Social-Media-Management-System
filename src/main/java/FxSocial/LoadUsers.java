package FxSocial;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadUsers {
	Alerts a = new Alerts();
	LinkedList s = Main.sharedList;
	EnterSystem es = Main.es;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Users File");
		fc.setInitialDirectory(AppFiles.getLoadDirectory());
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text and CSV files", "*.txt", "*.csv"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			a.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try {
			LinkedList loadedUsers = new LinkedList();
			Set<Integer> userIds = new HashSet<>();
			int skippedRows = 0;
			for (List<String> data : CsvUtils.readRecords(f.toPath())) {
				try {
					if (data.size() != 3) {
						throw new IllegalArgumentException();
					}
					int userId = Integer.parseInt(data.get(0));
					String name = data.get(1);
					int age = Integer.parseInt(data.get(2));
					if (userId <= 0 || name.isBlank() || age <= 0 || !userIds.add(userId)) {
						throw new IllegalArgumentException();
					}
					loadedUsers.addLast(new User(userId, name, age));
				} catch (IllegalArgumentException e) {
					skippedRows++;
				}
			}
			if (loadedUsers.getSize() == 0) {
				a.ErrorAlert("Error", "The selected file does not contain any valid users.");
				return;
			}

			s.clear();
			es.tableData.clear();
			for (int i = 0; i < loadedUsers.getSize(); i++) {
				User user = (User) loadedUsers.get(i);
				s.addLast(user);
				es.tableData.add(user);
			}

			String message = "Users loaded successfully.";
			if (skippedRows > 0) {
				message += " Skipped " + skippedRows + " invalid row(s).";
			}
			a.InfoAlert("Success", message);

			if (Main.es != null && Main.es.tableView1 != null) {
				Main.es.tableView1.setItems(es.tableData);
				Main.es.tableView1.refresh();
				Main.es.updateActionButtons();
			}
		} catch (IOException e) {
			a.ErrorAlert("Error", "Could not read the selected file: " + e.getMessage());
		}
	}
}

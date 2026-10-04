package FxSocial;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadPosts {
	Alerts a = new Alerts();
	EnterSystem es = Main.es;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Posts File");
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
				((User) item).getPostCreated().clear();
				((User) item).getPostShared().clear();
			}
			Set<Integer> postIds = new HashSet<>();
			int skippedRows = 0;
			for (List<String> data : records) {
				try {
					if (data.size() != 4) {
						throw new IllegalArgumentException();
					}
					int userId = Integer.parseInt(data.get(0));
					int postId = Integer.parseInt(data.get(1));
					String content = data.get(2);
					String date = data.get(3);
					User creator = Main.findUserById(userId);
					LocalDate.parse(date);
					if (creator == null || postId <= 0 || content.isBlank() || !postIds.add(postId)) {
						throw new IllegalArgumentException();
					}
					creator.createPost(new Posts(postId, creator, content, date));
				} catch (RuntimeException e) {
					skippedRows++;
				}
			}
			if (Main.es != null && Main.es.tableView1 != null) {
				Main.es.tableView1.refresh();
			}
			String message = "Posts loaded successfully.";
			if (skippedRows > 0) {
				message += " Skipped " + skippedRows + " invalid row(s).";
			}
			a.InfoAlert("Success", message);
		} catch (IOException e) {
			a.ErrorAlert("Error", "Could not read the selected file: " + e.getMessage());
		}
	}
}

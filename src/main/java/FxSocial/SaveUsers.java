package FxSocial;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.collections.ObservableList;

public class SaveUsers {
	ObservableList<Object> userList;
	Alerts a = new Alerts();

	public SaveUsers(ObservableList<Object> userList) {
		this.userList = userList;
	}

	public void Display() {
		try {
			Path file = AppFiles.getExportFile("users.txt");
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
			for (Object o : userList) {
				User u = (User) o;
				writer.println(buildUserData(u));
			}
			}
			a.InfoAlert("Success", "User data saved to:\n" + file);
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save user data: " + e.getMessage());
		}
	}

	private String buildUserData(User user) {
		return CsvUtils.format(user.getUserId(), user.getName(), user.getAge());
	}

}

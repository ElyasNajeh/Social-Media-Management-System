package FxSocial;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.collections.ObservableList;

public class SaveUsersAsc implements Comparator<User> {
	ObservableList<Object> userList;
	Alerts a = new Alerts();

	public SaveUsersAsc(ObservableList<Object> userList) {
		this.userList = userList;
	}

	public void Display() {
		try {
			Path file = AppFiles.getExportFile("users-ascending.txt");
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
			 List<User> users = new ArrayList<>();
			 for (Object o : userList) {
				 users.add((User)o);
			 }
			Collections.sort(users, this);
			for (User u  : users) {
				writer.println(buildUserData(u));
			}
			}
			a.InfoAlert("Success", "Ascending user data saved to:\n" + file);
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save user data: " + e.getMessage());
		}
	}

	private String buildUserData(User user) {
		return CsvUtils.format(user.getUserId(), user.getName(), user.getAge());
	}

	@Override
	public int compare(User o1, User o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}

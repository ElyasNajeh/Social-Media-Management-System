package FxSocial;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.collections.ObservableList;

public class SaveFriends {
	ObservableList<Object> friendList;
	Alerts a = new Alerts();

	public SaveFriends(ObservableList<Object> friendList) {
		this.friendList = friendList;
	}

	public void Display() {
		try {
			Path file = AppFiles.getExportFile("friendships.txt");
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
			for (Object o : friendList) {
				User u = (User) o;
				writer.println(buildFriendData(u));
			}
			}
			a.InfoAlert("Success", "Friendship data saved to:\n" + file);
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save friendship data: " + e.getMessage());
		}
	}

	private String buildFriendData(User user) {
		StringBuilder s = new StringBuilder(String.valueOf(user.getUserId()));
		for (int i = 0; i < user.getFriend().getSize(); i++) {
			User friend = (User) user.getFriend().get(i);
			s.append(',').append(friend.getUserId());
		}
		return s.toString();
	}
}

package FxSocial;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.collections.ObservableList;

public class SavePosts {
	ObservableList<Object> postList;
	Alerts a = new Alerts();

	public SavePosts(ObservableList<Object> postList) {
		this.postList = postList;
	}

	public void Display() {
		try {
			Path file = AppFiles.getExportFile("posts.txt");
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
			for (Object o : postList) {
				User u = (User) o;
				for (int i = 0; i < u.getPostCreated().getSize(); i++) {
					Posts post = (Posts) u.getPostCreated().get(i);
					writer.println(CsvUtils.format(u.getUserId(), post.getPostId(), post.getContent(),
							post.getCreationDate()));
				}
			}
			}
			a.InfoAlert("Success", "Post data saved to:\n" + file);
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save post data: " + e.getMessage());
		}
	}
}

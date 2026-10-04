package FxSocial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PostM {
	TextArea t1;
	User selectedUser;
	ObservableList<Object> postData = FXCollections.observableArrayList();
	Alerts a = new Alerts();
	IconButton b3, b4, b5;
	int index;
	EnterSystem es = Main.es;
	

	public PostM() {
	}

	public PostM(User selectedUser, int index) {
		this.selectedUser = selectedUser;
		this.index = Math.max(0, index);
	}

	public void loadPost(User selectedUser) {
		postData.clear();
		if (selectedUser != null && selectedUser.getPostCreated() != null
				&& selectedUser.getPostCreated().getSize() > 0) {
			LinkedList postList = selectedUser.getPostCreated();
			for (int i = 0; i < postList.getSize(); i++) {
				postData.add(postList.get(i));
			}
			if (index >= 0 && index < postList.getSize()) {
				Posts post = (Posts) postList.get(index);
				t1.setText(post.toString());
			}
			b4.setDisable(index >= postList.getSize() - 1);
			b5.setDisable(index <= 0);
			if (b3 != null) {
				b3.setDisable(postData.isEmpty());
			}
		} else {
			t1.clear();
			a.InfoAlert("Info", "This User doesn't have Posts");
			if (b3 != null) {
				b3.setDisable(true);
			}
			b4.setDisable(true);
			b5.setDisable(true);

		}
	}

	public void deletePost() {
		if (index < 0 || index >= postData.size()) {
			return;
		}
		boolean confirmation = a.ConfirmationAlert("Confirmation", "Are you Sure you Need to Delete Post?");
		if (!confirmation) {
			return;
		}
		Posts deletedPost = (Posts) selectedUser.getPostCreated().get(index);
		selectedUser.getPostCreated().remove(index);
		postData.remove(index);
		for (Object item : es.tableData) {
			((User) item).removeSharedPost(deletedPost);
		}
		if (postData.size() == 0) {
			t1.clear();
			b3.setDisable(true);
			b4.setDisable(true);
			b5.setDisable(true);
			a.InfoAlert("Info", "No Posts Left");
			Main.es.tableView1.refresh();
			return;
		}
		index = Math.min(index, postData.size() - 1);
		loadPost(selectedUser);

		a.InfoAlert("Success", "The Post has been Deleted Successfully");
		Main.es.tableView1.refresh();
	}

	public void Display() {

		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		HBox hb1 = new HBox(40);
		IconButton b1 = new IconButton("Create Posts", "/FxSocial/images/icons8-create-post-64.png");
		b1.setOnAction(x -> {
			CreatePost cp = new CreatePost(selectedUser, this);
			cp.Display();

		});
		IconButton b2 = new IconButton("View Posts", "/FxSocial/images/icons8-view-50.png");
		b2.setOnAction(x -> {
			ViewPosts vp = new ViewPosts(selectedUser, 0);
			vp.Display();

		});
		b3 = new IconButton("Delete Posts", "/FxSocial/images/icons8-remove-60.png");
		b3.setOnAction(x -> {
			deletePost();
		});
		IconButton b6 = new IconButton("Back", "/FxSocial/images/icons8-back-50.png");
		b6.setOnAction(x -> {
			stage.close();
		});

		hb1.getChildren().addAll(b6, b1, b2, b3);
		hb1.setAlignment(Pos.CENTER);

		HBox hb2 = new HBox(30);
		hb2.setPadding(new Insets(20, 0, 0, 0));
		b4 = new IconButton("Next", "/FxSocial/images/icons8-next-50.png");
		b4.setOnAction(x -> {
			if (index < postData.size() - 1) {
				index++;
				loadPost(selectedUser);
			}
		});
		b5 = new IconButton("Previous", "/FxSocial/images/icons8-previous-50.png");
		b5.setOnAction(x -> {
			if (index > 0) {
				index--;
				loadPost(selectedUser);
			}
		});

		hb2.getChildren().addAll(b5, b4);
		hb2.setAlignment(Pos.CENTER);

		VBox vb1 = new VBox(30);
		vb1.getChildren().addAll(hb2, hb1);
		vb1.setAlignment(Pos.CENTER);

		VBox vb2 = new VBox(20);
		CustomLabel l = new CustomLabel("My Posts!");
		t1 = new TextArea();
		t1.setStyle("-fx-background-color: #1e1e1e;" + "-fx-text-fill: #000000;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Calibri', 'Segoe UI', sans-serif;" + "-fx-border-color: #3a3a3a;"
				+ "-fx-border-width: 1.5;" + "-fx-border-radius: 8;" + "-fx-background-radius: 8;" + "-fx-padding: 10;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.1, 0, 2);" + "-fx-opacity: 1;");

		t1.setEditable(false);
		t1.setMaxWidth(680);
		t1.setPrefHeight(600);

		vb2.getChildren().addAll(l, t1);
		vb2.setAlignment(Pos.CENTER);

		loadPost(selectedUser);

		BorderPane bp = new BorderPane();

		Main.setbackGround(bp);

		bp.setTop(m);
		bp.setCenter(vb2);
		bp.setBottom(vb1);

		Scene scene = Main.createScene(bp);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		Main.setStageIcon(stage);
		stage.show();
	}
}

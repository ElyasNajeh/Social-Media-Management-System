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

public class ViewPosts {
	TextArea t1;
	int index;
	IconButton b1, b2, b3;
	User selectedUser;
	ObservableList<Object> postShared = FXCollections.observableArrayList();
	Alerts a = new Alerts();
	EnterSystem es = Main.es;

	ViewPosts() {

	}

	public ViewPosts(User selectedUser, int index) {
		this.selectedUser = selectedUser;
		this.index = Math.max(0, index);
	}

	public void loadPostShared(User selectedUser) {
		postShared.clear();
		if (selectedUser != null && selectedUser.getPostShared() != null
				&& selectedUser.getPostShared().getSize() > 0) {
			LinkedList postList = selectedUser.getPostShared();
			for (int i = postList.getSize() - 1; i >= 0; i--) {
				Posts current = (Posts) postList.get(i);
				if (!es.tableData.contains(current.getCreatorId())) {
					postList.remove(i);
				}
			}
		}
		if (selectedUser != null && selectedUser.getPostShared() != null
				&& selectedUser.getPostShared().getSize() > 0) {
			LinkedList postSharedList = selectedUser.getPostShared();
			for (int i = 0; i < postSharedList.getSize(); i++) {
				postShared.add(postSharedList.get(i));
			}
			if (index >= 0 && index < postSharedList.getSize()) {
				Posts post = (Posts) postShared.get(index);
				t1.setText(post.toString());
			}
			b3.setDisable(index >= postSharedList.getSize() - 1);
			b2.setDisable(index <= 0);
		} else {
			t1.clear();
			a.InfoAlert("Info", "This User doesn't have Shared Posts");
			b2.setDisable(true);
			b3.setDisable(true);
		}
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		VBox vb1 = new VBox(20);
		CustomLabel l = new CustomLabel("Posts Shared With Me!");
		t1 = new TextArea();
		t1.setStyle("-fx-background-color: #1e1e1e;" + "-fx-text-fill: #000000;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Calibri', 'Segoe UI', sans-serif;" + "-fx-border-color: #3a3a3a;"
				+ "-fx-border-width: 1.5;" + "-fx-border-radius: 8;" + "-fx-background-radius: 8;" + "-fx-padding: 10;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.1, 0, 2);" + "-fx-opacity: 1;");

		t1.setEditable(false);
		t1.setMaxWidth(680);
		t1.setPrefHeight(600);

		vb1.getChildren().addAll(l, t1);
		vb1.setAlignment(Pos.CENTER);

		HBox hb1 = new HBox(20);
		hb1.setPadding(new Insets(20, 0, 0, 0));
		b1 = new IconButton("Back", "/FxSocial/images/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});

		b2 = new IconButton("Previous", "/FxSocial/images/icons8-previous-50.png");
		b2.setOnAction(x -> {
			if (index > 0) {
				index--;
				loadPostShared(selectedUser);
			}
		});
		b3 = new IconButton("Next", "/FxSocial/images/icons8-next-50.png");
		b3.setOnAction(x -> {
			if (index < postShared.size() - 1) {
				index++;
				loadPostShared(selectedUser);
			}
		});

		loadPostShared(selectedUser);

		hb1.getChildren().addAll(b1, b2, b3);
		hb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		Main.setbackGround(bp);

		bp.setTop(m);
		bp.setCenter(vb1);
		bp.setBottom(hb1);

		Scene scene = Main.createScene(bp);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		Main.setStageIcon(stage);
		stage.show();
	}
}

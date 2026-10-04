package FxSocial;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayView {
	TextArea t1;
	int index;
	IconButton b1, b2, b3;
	User selectedUser;
	ViewPosts vp;

	public DisplayView(User selectedUser, int index) {
		this.selectedUser = selectedUser;
		this.index = Math.max(0, index);
		this.vp = new ViewPosts(selectedUser, this.index);
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		VBox vb1 = new VBox(20);
		CustomLabel l = new CustomLabel("Posts Shared With Me!");
		t1 = new TextArea();
		vp.t1 = t1;
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
		vp.b2 = b2;
		b2.setOnAction(x -> {
			if (index > 0) {
				index--;
				vp.index = index ;
				vp.loadPostShared(selectedUser);
			}
		});
		b3 = new IconButton("Next", "/FxSocial/images/icons8-next-50.png");
		vp.b3 = b3;
		b3.setOnAction(x -> {
			if (index < vp.postShared.size() - 1) {
				index++;
				vp.index= index ;
				vp.loadPostShared(selectedUser);
			}
		});


		hb1.getChildren().addAll(b1, b2, b3);
		hb1.setAlignment(Pos.CENTER);

		vp.loadPostShared(selectedUser);
		
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

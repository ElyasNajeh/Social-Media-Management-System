package FxSocial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatePost {
	CustomTextField t3, t2;
	TextArea t1;
	DatePicker datePicker;
	Alerts a = new Alerts();
	User selectedUser;
	VBox vb2;
	LinkedList s = Main.sharedList;
	PostM pp = new PostM();
	CheckBox cb;
	ArrayList<CheckBox> ListofShared = new ArrayList<>();

	CreatePost(User selectedUser, PostM pp) {
		this.selectedUser = selectedUser;
		this.pp = pp;
	}

	public boolean validInput1(int postId, String content, String date) {
		if (postId <= 0) {
			a.ErrorAlert("Error", "Post ID can't be Zero or Less");
			return false;
		}
		if (content == null || content.trim().isEmpty()) {
			a.ErrorAlert("Error", "Content of Post can't be Empty");
			return false;
		}
		if (!date.equals(LocalDate.now().toString())) {
			a.ErrorAlert("Error", "Post date must be today Date Only");
			return false;
		}

		return true;
	}

	public boolean checkId(int id) {
		if (Main.postIdExists(id)) {
			a.ErrorAlert("Error", "This post ID already exists.");
			return false;
		}
		return true;
	}

	public void clear() {
		t1.clear();
		t2.clear();
		datePicker.setValue(null);
		for (CheckBox checkBox : ListofShared) {
			checkBox.setSelected(false);
		}
	}

	public void loadFriendsOfUser(User selectedUser) {
		if (selectedUser != null && selectedUser.getFriend() != null && selectedUser.getFriend().getSize() > 0) {
			LinkedList friendList = selectedUser.getFriend();
			for (int i = 0; i < friendList.getSize(); i++) {
				User current = (User) friendList.get(i);
				String name = current.getName();
				cb = new CheckBox(name);
				cb.setUserData(current);
				ListofShared.add(cb);
				vb2.getChildren().add(cb);
			}
		} else {
			CustomLabel l5 = new CustomLabel("No Friends For this User to Share the Post With Them");
			vb2.getChildren().add(l5);
		}
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		GridPane gp = new GridPane();
		gp.setPadding(new Insets(30));
		gp.setHgap(50);
		gp.setVgap(40);

		CustomLabel l1 = new CustomLabel("Creator ID: ");
		t3 = new CustomTextField();
		t3.setText(String.valueOf(selectedUser.getUserId()));
		t3.setDisable(true);
		gp.add(l1, 0, 0);
		gp.add(t3, 1, 0);

		CustomLabel l2 = new CustomLabel("Post ID: ");
		t2 = new CustomTextField();
		gp.add(l2, 0, 1);
		gp.add(t2, 1, 1);

		CustomLabel dBirth = new CustomLabel("Date of Post:");
		datePicker = new DatePicker();
		datePicker.setStyle("-fx-background-color: white;" + "-fx-text-fill: #2b2b2b;" + "-fx-font-size: 13px;"
				+ "-fx-font-family: 'Segoe UI', 'Calibri';" + "-fx-border-color: #cccccc;" + "-fx-border-radius: 6;"
				+ "-fx-background-radius: 6;" + "-fx-padding: 6;" + "-fx-opacity: 0.98;");
		datePicker.setMaxWidth(320);
		datePicker.setMaxHeight(60);
		gp.add(dBirth, 0, 2);
		gp.add(datePicker, 1, 2);

		VBox vb1 = new VBox(10);
		t1 = new TextArea();
		t1.setStyle("-fx-background-color: #1e1e1e;" + "-fx-text-fill: #000000;" + "-fx-font-size: 16px;"
				+ "-fx-font-family: 'Calibri', 'Segoe UI', sans-serif;" + "-fx-border-color: #3a3a3a;"
				+ "-fx-border-width: 1.5;" + "-fx-border-radius: 8;" + "-fx-background-radius: 8;" + "-fx-padding: 10;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.1, 0, 2);" + "-fx-opacity: 1;");

		t1.setMaxWidth(700);
		t1.setMinHeight(500);

		CustomLabel l3 = new CustomLabel("Content of Post !");
		vb1.getChildren().addAll(l3, t1);
		vb1.setAlignment(Pos.CENTER);

		gp.add(vb1, 2, 2);

		CustomLabel l4 = new CustomLabel("Shared With:");
		vb2 = new VBox(10);
		loadFriendsOfUser(selectedUser);

		gp.add(l4, 0, 3);
		gp.add(vb2, 1, 3);

		HBox hb1 = new HBox(100);
		IconButton b1 = new IconButton("Back", "/FxSocial/images/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});
		IconButton b2 = new IconButton("Create Post", "/FxSocial/images/icons8-create-post-64.png");
		b2.setOnAction(x -> {
			String id = t2.getText();
			String content = t1.getText();

			if (datePicker.getValue() == null) {
				a.ErrorAlert("Error", "Date can't be empty");
				return;
			}
			String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			if (id == null || id.trim().isEmpty()) {
				a.ErrorAlert("Error", "Post ID can't be Empty");
				return;
			}
			if (!id.matches("\\d+")) {
				a.ErrorAlert("Error", "Post ID Can be Only Numbers");
				return;
			}
			int postId;
			try {
				postId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				a.ErrorAlert("Error", "Post ID is too large.");
				return;
			}

			boolean isValid1 = validInput1(postId, content, selectedDate);
			if (!isValid1) {
				return;
			}
			boolean isValid3 = checkId(postId);
			if (!isValid3) {
				return;
			}
			boolean confirmed = a.ConfirmationAlert("Confirmation", "Are you sure you want to Create this Post?");
			if (!confirmed) {
				return;
			}
			Posts p = new Posts(postId, selectedUser, content, selectedDate);
			selectedUser.createPost(p);
			for (CheckBox cb : ListofShared) {
				if (cb.isSelected()) {
					User u = (User) cb.getUserData();
					u.addPostShared(p);
				}
			}
			pp.loadPost(selectedUser);

			a.InfoAlert("Success", "Post created successfully.");
			clear();
			Main.es.tableView1.refresh();

		});
		IconButton b3 = new IconButton("Clear", "/FxSocial/images/icons8-clear-50.png");
		b3.setOnAction(x -> {
			clear();
		});
		hb1.getChildren().addAll(b1, b2, b3);
		hb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		Main.setbackGround(bp);

		bp.setTop(m);
		bp.setCenter(vb1);
		bp.setLeft(gp);
		bp.setBottom(hb1);

		Scene scene = Main.createScene(bp);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		Main.setStageIcon(stage);
		stage.show();
	}
}

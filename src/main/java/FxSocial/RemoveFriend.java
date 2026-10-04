package FxSocial;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveFriend {
	CustomTextField t1, t2, t3;
	IconButton b2, b3, b4;
	Alerts a = new Alerts();
	FriendShipsM friendd;
	ObservableList<Object> friendList;
	int index;

	public void loadDetails(CustomTextField t1, CustomTextField t2, CustomTextField t3) {
		if (index >= 0 && index < friendList.size()) {
			User user = (User) friendList.get(index);
			t1.setText(user.getUserId() + "");
			t2.setText(user.getName());
			t3.setText(user.getAge() + "");
		}
		b3.setDisable(index >= friendList.size() - 1);
		b4.setDisable(index <= 0);

	}

	public void deleteButton() {
		if (index < 0 || index >= friendList.size()) {
			return;
		}
		boolean confirmation = a.ConfirmationAlert("Confirmation",
				"Are you Sure you Need to Delete Information For This Friend ?");
		if (!confirmation) {
			return;
		}
		friendd.selectedUser.getFriend().remove(index);
		friendList.remove(index);
		friendd.tableView2.setItems(friendList);
		if (friendList.size() == 0) {
			t1.clear();
			t2.clear();
			t3.clear();
			b2.setDisable(true);
			b3.setDisable(true);
			b4.setDisable(true);
			a.InfoAlert("Info", "No Friends Left");
			return;
		}
		index = Math.min(index, friendList.size() - 1);
		loadDetails(t1, t2, t3);
		a.InfoAlert("Success", "The Friend has been Deleted Successfully");
		Main.es.tableView1.refresh();

	}

	public RemoveFriend(FriendShipsM friendd, ObservableList<Object> friendList, int index) {
		this.friendd = friendd;
		this.friendList = friendList;
		this.index = index;
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		GridPane gp = new GridPane();
		gp.setPadding(new Insets(30));
		gp.setHgap(50);
		gp.setVgap(40);

		CustomLabel l1 = new CustomLabel("User ID: ");
		t1 = new CustomTextField();
		gp.add(l1, 0, 0);
		gp.add(t1, 1, 0);
		t1.setDisable(true);

		CustomLabel l2 = new CustomLabel("Name: ");
		t2 = new CustomTextField();
		gp.add(l2, 0, 1);
		gp.add(t2, 1, 1);
		t2.setDisable(true);

		CustomLabel l3 = new CustomLabel("Age: ");
		t3 = new CustomTextField();
		gp.add(l3, 0, 2);
		gp.add(t3, 1, 2);
		t3.setDisable(true);

		HBox hb1 = new HBox(100);
		IconButton b1 = new IconButton("Back", "/FxSocial/images/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});

		b3 = new IconButton("Next", "/FxSocial/images/icons8-next-50.png");
		b3.setOnAction(x -> {
			if (index < friendList.size() - 1) {
				index++;
				loadDetails(t1, t2, t3);
			}
		});
		b4 = new IconButton("Previous", "/FxSocial/images/icons8-previous-50.png");
		b4.setOnAction(x -> {
			if (index > 0) {
				index--;
				loadDetails(t1, t2, t3);
			}
		});
		b2 = new IconButton("Remove", "/FxSocial/images/icons8-remove-60.png");
		b2.setOnAction(x -> {
			deleteButton();

		});

		HBox hb2 = new HBox(100);
		VBox vb1 = new VBox(30);

		loadDetails(t1, t2, t3);

		hb1.getChildren().addAll(b1, b2);
		hb1.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(b4, b3);
		hb2.setAlignment(Pos.CENTER);
		vb1.getChildren().addAll(hb2, hb1);
		vb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		Main.setbackGround(bp);

		bp.setTop(m);
		bp.setLeft(gp);
		bp.setBottom(vb1);

		Scene scene = Main.createScene(bp);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		Main.setStageIcon(stage);
		stage.show();
	}
}

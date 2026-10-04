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

public class Delete {
	CustomTextField t1, t2, t3;
	IconButton b2, b3, b4;
	Alerts a = new Alerts();
	EnterSystem es = Main.es;
	LinkedList s = Main.sharedList;
	ObservableList<Object> tableList = es.tableData;
	int index;

	public void loadDetails(CustomTextField t1, CustomTextField t2, CustomTextField t3) {
		if (index >= 0 && index < tableList.size()) {
			User user = (User) tableList.get(index);
			t1.setText(user.getUserId() + "");
			t2.setText(user.getName());
			t3.setText(user.getAge() + "");
		}
		b3.setDisable(index >= tableList.size() - 1);
		b4.setDisable(index <= 0);

	}

	public void deleteButton() {
		if (index < 0 || index >= tableList.size()) {
			return;
		}
		boolean confirmation = a.ConfirmationAlert("Confirmation",
				"Are you Sure you Need to Delete Information For This User ?");
		if (!confirmation) {
			return;
		}
		User deletedUser = (User) tableList.get(index);
		for (Object item : es.tableData) {
			User user = (User) item;
			user.removeFriend(deletedUser);
			for (int i = 0; i < deletedUser.getPostCreated().getSize(); i++) {
				user.removeSharedPost((Posts) deletedUser.getPostCreated().get(i));
			}
		}
		s.remove(index);
		tableList.remove(index);
		if (es.tableView1.getItems() != tableList) {
			es.tableView1.getItems().remove(deletedUser);
		}
		if (tableList.isEmpty()) {
			t1.clear();
			t2.clear();
			t3.clear();
			b2.setDisable(true);
			b3.setDisable(true);
			b4.setDisable(true);
			a.InfoAlert("Success", "The last user was deleted successfully.");
			es.updateActionButtons();
			return;
		}
		index = Math.min(index, tableList.size() - 1);
		loadDetails(t1, t2, t3);
		a.InfoAlert("Success", "The User has been Deleted Successfully");
		es.tableView1.refresh();
		es.updateActionButtons();

	}

	public Delete(ObservableList<Object> tableList, int index) {
		this.tableList = tableList;
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
			if (index < tableList.size() - 1) {
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

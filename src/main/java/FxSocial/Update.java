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

public class Update {
	CustomTextField t1, t2, t3;
	IconButton b3, b4;
	Alerts a = new Alerts();
	EnterSystem es = Main.es;
	LinkedList s = Main.sharedList;
	ObservableList<Object> tableList = es.tableData;
	int index;
	AddUser u = new AddUser();

	public void loadDetails(CustomTextField t1, CustomTextField t2, CustomTextField t3) {
		if (index >= 0 && index < s.getSize()) {
			User user = (User) tableList.get(index);
			t1.setText(user.getUserId() + "");
			t2.setText(user.getName());
			t3.setText(user.getAge() + "");
		}
		b3.setDisable(index >= s.getSize() - 1);
		b4.setDisable(index <= 0);
	}

	public void updateButton() {
		if (index < 0 || index >= s.getSize()) {
			return;
		}
		User user = (User) tableList.get(index);
		int newId;
		try {
			newId = Integer.parseInt(t1.getText().trim());
		} catch (NumberFormatException e) {
			a.ErrorAlert("Error", "User ID is invalid.");
			return;
		}
		String newName = t2.getText().trim();

		String age = t3.getText();

		if (age == null || age.trim().isEmpty()) {
			a.ErrorAlert("Error", "Age can't be Empty");
			return;
		}
		if (!age.matches("\\d+")) {
			a.ErrorAlert("Error", "Age Can be Only Numbers");
			return;

		}
		int newAge;
		try {
			newAge = Integer.parseInt(age);
		} catch (NumberFormatException e) {
			a.ErrorAlert("Error", "Age is too large.");
			return;
		}

		boolean isValid1 = u.validInput1(newId, newName, newAge);
		if (!isValid1) {
			return;
		}

		boolean confirmation = a.ConfirmationAlert("Confirmation",
				"Are you Sure you Need to Update Information For This User ?");
		if (!confirmation) {
			return;
		}
		user.setUserId(newId);
		user.setName(newName);
		user.setAge(newAge);

		tableList.set(index, user);
		es.tableView1.refresh();

		a.InfoAlert("Success", "The User has been Updated Successfully");
	}

	public Update(ObservableList<Object> tableList, int index) {
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

		CustomLabel l3 = new CustomLabel("Age: ");
		t3 = new CustomTextField();
		gp.add(l3, 0, 2);
		gp.add(t3, 1, 2);

		HBox hb1 = new HBox(100);
		IconButton b1 = new IconButton("Back", "/FxSocial/images/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});
		IconButton b2 = new IconButton("Update", "/FxSocial/images/icons8-update-64.png");
		b2.setOnAction(x -> {
			updateButton();
		});
		b3 = new IconButton("Next", "/FxSocial/images/icons8-next-50.png");
		b3.setOnAction(x -> {
			if (index < s.getSize() - 1) {
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

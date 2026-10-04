package FxSocial;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddUser {
	CustomTextField t1, t2, t3;
	Alerts a = new Alerts();
	LinkedList s = Main.sharedList;
	EnterSystem es = Main.es;

	public boolean validInput1(int id, String name, int age) {
		if (id <= 0) {
			a.ErrorAlert("Error", "User ID can't be Zero or Less");
			return false;
		}
		if (name == null || name.trim().isEmpty()) {
			a.ErrorAlert("Error", "Name can't be Empty");
			return false;
		}
		if (age <= 17 || age >= 80) {
			a.ErrorAlert("Error", "Age Must be Between 18 And 80");
			return false;
		}

		if (!name.matches("^[a-zA-Z ]+$")) {
			a.ErrorAlert("Error", "Name Can be Only Characters");
			return false;
		}

		return true;
	}

	public boolean checkId(int id) {
		for (Object user : es.tableData) {
			User u = (User) user;
			if (u.getUserId() == id) {
				a.ErrorAlert("Error", "User ID already Exists!");
				return false;
			}
		}
		return true;
	}

	public void clear() {
		t1.clear();
		t2.clear();
		t3.clear();
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
		IconButton b2 = new IconButton("Add", "/FxSocial/images/icons8-add-50.png");
		b2.setOnAction(x -> {
			String id1 = t1.getText();
			String name = t2.getText();
			String age = t3.getText();

			if (id1 == null || id1.trim().isEmpty()) {
				a.ErrorAlert("Error", "User ID can't be Empty");
				return;
			}
			if (age == null || age.trim().isEmpty()) {
				a.ErrorAlert("Error", "Age can't be Empty");
				return;
			}
			if (!id1.matches("\\d+")) {
				a.ErrorAlert("Error", "User ID Can be Only Numbers");
				return;

			}
			if (!age.matches("\\d+")) {
				a.ErrorAlert("Error", "Age Can be Only Numbers");
				return;

			}

			int userId;
			int Age;
			try {
				userId = Integer.parseInt(id1);
				Age = Integer.parseInt(age);
			} catch (NumberFormatException e) {
				a.ErrorAlert("Error", "User ID or age is too large.");
				return;
			}

			boolean isValid1 = validInput1(userId, name, Age);
			if (!isValid1) {
				return;
			}

			boolean isValid3 = checkId(userId);
			if (!isValid3) {
				return;
			}
			boolean confirmed = a.ConfirmationAlert("Confirmation", "Are you sure you want to add this User?");
			if (!confirmed) {
				return;
			}
			User u1 = new User(userId, name, Age);
			s.addLast(u1);
			es.tableData.add(u1);

			es.updateActionButtons();
			a.InfoAlert("Success", "User added successfully.");
			clear();

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

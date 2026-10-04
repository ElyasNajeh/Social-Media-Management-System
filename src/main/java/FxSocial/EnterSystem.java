package FxSocial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EnterSystem {
	boolean columnsAdded = false;
	TableView<Object> tableView1 = new TableView<>();
	ObservableList<Object> tableData = FXCollections.observableArrayList();
	Alerts a = new Alerts();
	TextField t1;
	IconButton b2, b3, b4, b5, b6;
	private Stage stage;

	void updateActionButtons() {
		boolean userSelected = tableView1.getSelectionModel().getSelectedItem() != null;
		if (b2 != null) {
			b2.setDisable(!userSelected);
			b3.setDisable(!userSelected);
			b4.setDisable(!userSelected);
			b5.setDisable(!userSelected);
			b6.setDisable(tableData.isEmpty());
		}
	}

	public void Search(String search) {
		ObservableList<Object> results = FXCollections.observableArrayList();
		if (search == null || search.trim().isEmpty()) {
			a.ErrorAlert("Error", "Please enter ID or Name to Search");
			return;
		}
		boolean Num = true;
		for (int i = 0; i < search.length(); i++) {
			if (!Character.isDigit(search.charAt(i))) {
				Num = false;
				break;
			}
		}
		Integer numericSearch = null;
		if (Num) {
			try {
				numericSearch = Integer.valueOf(search);
			} catch (NumberFormatException e) {
				a.ErrorAlert("Error", "The user ID is too large.");
				return;
			}
		}
		for (Object item : tableData) {
			User current = (User) item;
			if (numericSearch != null && current.getUserId() == numericSearch) {
				results.add(current);
			} else if (current.getName().equalsIgnoreCase(search)) {
				results.add(current);
			}
		}
		if (results.isEmpty()) {
			a.ErrorAlert("Error", "No User Found with this ID or Name");
		} else {
			tableView1.setItems(results);
		}
	}

	public void Display() {
		if (stage != null) {
			stage.show();
			stage.toFront();
			stage.requestFocus();
			return;
		}
		stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		if (!columnsAdded) {
			TableColumn<Object, Integer> idCol = new TableColumn<>("UserID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

			TableColumn<Object, String> nCol = new TableColumn<>("Name");
			nCol.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Object, Integer> ageCol = new TableColumn<>("Age");
			ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

			TableColumn<Object, String> frCol = new TableColumn<>("Number of Friends");
			frCol.setCellValueFactory(new PropertyValueFactory<>("friendsCount"));

			TableColumn<Object, Integer> poCol = new TableColumn<>("Number of Posts Created");
			poCol.setCellValueFactory(new PropertyValueFactory<>("postsCount"));

			TableColumn<Object, Integer> posCol = new TableColumn<>("Number of Posts Shared");
			posCol.setCellValueFactory(new PropertyValueFactory<>("postsSharedCount"));

			tableView1.getColumns().add(idCol);
			tableView1.getColumns().add(nCol);
			tableView1.getColumns().add(ageCol);
			tableView1.getColumns().add(frCol);
			tableView1.getColumns().add(poCol);
			tableView1.getColumns().add(posCol);
			tableView1.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent;"
					+ "-fx-table-cell-border-color: transparent;" + "-fx-font-family: 'Segoe UI';"
					+ "-fx-font-size: 14px;");
			tableView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
			tableView1.setItems(tableData);
			columnsAdded = true;
		}

		HBox hb1 = new HBox(10);
		IconButton b = new IconButton("Search", "/FxSocial/images/icons8-search-50.png");
		b.setOnAction(x -> {
			Search(t1.getText());

		});
		b.setPrefHeight(35);
		b.setPrefWidth(130);
		t1 = new TextField();
		t1.setStyle("-fx-background-color: white;" + "-fx-border-color: #6a11cb;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #333;"
				+ "-fx-padding: 14 4 14 18;");
		t1.setPrefWidth(150);
		t1.setPromptText("Search here ..");
		hb1.getChildren().addAll(b, t1);
		hb1.setAlignment(Pos.CENTER);

		VBox vb1 = new VBox(15);
		IconButton b0 = new IconButton("Refresh", "/FxSocial/images/icons8-refresh-50.png");
		b0.setMaxSize(365, 365);
		b0.setOnAction(x -> {
			if (tableData.isEmpty()) {
				a.ErrorAlert("Error", "No Data in Table to Refresh");
			} else {
				tableView1.setItems(tableData);
				updateActionButtons();
			}
		});
		IconButton b1 = new IconButton("Add User", "/FxSocial/images/icons8-add-50.png");
		b1.setOnAction(x -> {
			AddUser a = new AddUser();
			a.Display();
		});
		b1.setMaxSize(365, 365);

		b2 = new IconButton("Update User", "/FxSocial/images/icons8-update-64.png");
		b2.setDisable(true);
		b2.setOnAction(x -> {
			User selectedUser = (User) tableView1.getSelectionModel().getSelectedItem();
			int selected = tableData.indexOf(selectedUser);
			if (selectedUser != null && selected >= 0) {
				Update u = new Update(tableData, selected);
				u.Display();
			}
		});
		b2.setMaxSize(365, 365);

		tableView1.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> updateActionButtons());

		b3 = new IconButton("Remove User", "/FxSocial/images/icons8-remove-60.png");
		b3.setDisable(true);
		b3.setOnAction(x -> {
			User selectedUser = (User) tableView1.getSelectionModel().getSelectedItem();
			int selected = tableData.indexOf(selectedUser);
			if (selectedUser != null && selected >= 0) {
				Delete d = new Delete(tableData, selected);
				d.Display();
			}
		});
		b3.setMaxSize(365, 365);

		b4 = new IconButton("Posts Management", "/FxSocial/images/icons8-post-53.png");
		b4.setDisable(true);
		b4.setOnAction(x -> {
			User selectedUser = (User) tableView1.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				PostM pm = new PostM(selectedUser, 0);
				pm.Display();
			}
		});
		b4.setMaxSize(365, 365);

		b5 = new IconButton("Friendships Management", "/FxSocial/images/icons8-crowd-50.png");
		b5.setDisable(true);
		b5.setOnAction(x -> {
			User selectedUser = (User) tableView1.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				FriendShipsM f = new FriendShipsM(selectedUser);
				f.Display();
			}
		});
		b5.setMaxSize(365, 365);

		b6 = new IconButton("Most Active", "/FxSocial/images/icons8-check-mark-50.png");
		b6.setDisable(true);
		b6.setOnAction(x -> {
			MostActive ma = new MostActive();
			ma.Display();
		});

		b6.setMaxSize(365, 365);
		updateActionButtons();

		vb1.getChildren().addAll(hb1, b0, b1, b2, b3, b4, b5, b6);
		vb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setTop(m);
		bp.setCenter(tableView1);
		bp.setRight(vb1);

		Scene scene = Main.createScene(bp);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		Main.setStageIcon(stage);
		stage.show();
	}
}

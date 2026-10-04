package FxSocial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FriendShipsM {
	IconButton b1, b2, b3;
	CustomTextField t1;
	int index;
	Alerts a = new Alerts();
	boolean columnsAdded = false;
	TableView<Object> tableView2 = new TableView<>();
	ObservableList<Object> friendsData = FXCollections.observableArrayList();
	User selectedUser;
	EnterSystem es = Main.es;

	public void loadFriends(User selectedUser) {
		friendsData.clear();
		if (selectedUser != null && selectedUser.getFriend() != null && selectedUser.getFriend().getSize() > 0) {
			LinkedList friendList = selectedUser.getFriend();
			for (int i = 0; i < friendList.getSize(); i++) {
				friendsData.add(friendList.get(i));
			}
			tableView2.setItems(friendsData);
		} else {
			a.InfoAlert("Info", "This User doesn't have Friends !");
			return;
		}

		if (selectedUser != null && selectedUser.getFriend() != null && selectedUser.getFriend().getSize() > 0) {
			LinkedList friendList1 = selectedUser.getFriend();
			for (int i = friendList1.getSize() - 1; i >= 0; i--) {
				User current = (User) friendList1.get(i);
				if (!es.tableData.contains(current)) {
					friendList1.remove(i);
					friendsData.remove(i);
				}
			}
			tableView2.setItems(friendsData);
		}
	}

	public FriendShipsM() {

	}

	public FriendShipsM(User selectedUser) {
		this.selectedUser = selectedUser;
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
		for (Object item : friendsData) {
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
			tableView2.setItems(results);
		}
	}

	public void Display() {

		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		if (!columnsAdded) {
			TableColumn<Object, Integer> idCol = new TableColumn<>("Friend ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

			TableColumn<Object, String> nCol = new TableColumn<>("Name");
			nCol.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Object, Integer> ageCol = new TableColumn<>("Age");
			ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

			tableView2.getColumns().add(idCol);
			tableView2.getColumns().add(nCol);
			tableView2.getColumns().add(ageCol);
			tableView2.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent;"
					+ "-fx-table-cell-border-color: transparent;" + "-fx-font-family: 'Segoe UI';"
					+ "-fx-font-size: 14px;");
			tableView2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
			tableView2.setItems(friendsData);
			columnsAdded = true;

		}
		loadFriends(selectedUser);

		HBox hb1 = new HBox(40);
		b1 = new IconButton("Back", "/FxSocial/images/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});
		b2 = new IconButton("Add Friend", "/FxSocial/images/icons8-add-50.png");
		b2.setOnAction(x -> {
			AddFriend af = new AddFriend(this);
			af.Display();

		});
		b3 = new IconButton("Remove Friend", "/FxSocial/images/icons8-remove-60.png");
		b3.setOnAction(x -> {
			User selectedFriend = (User) tableView2.getSelectionModel().getSelectedItem();
			int selected = friendsData.indexOf(selectedFriend);
			if (selectedFriend != null && selected >= 0) {
				RemoveFriend rf = new RemoveFriend(this, friendsData, selected);
				rf.Display();

			} else {
				a.ErrorAlert("Error", "Please Select a Friend to Delete");
			}
		});
		IconButton b = new IconButton("Search", "/FxSocial/images/icons8-search-50.png");
		b.setOnAction(x -> {
			Search(t1.getText());

		});
		t1 = new CustomTextField();
		t1.setStyle("-fx-background-color: white;" + "-fx-border-color: #6a11cb;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #333;"
				+ "-fx-padding: 14 4 14 18;");
		t1.setPromptText("Search here ..");

		IconButton b4 = new IconButton("Refresh", "/FxSocial/images/icons8-refresh-50.png");
		b4.setOnAction(x -> {
			tableView2.setItems(friendsData);
		});

		hb1.getChildren().addAll(b1, b2, b3, b, t1, b4);
		hb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setTop(m);
		bp.setCenter(tableView2);
		bp.setBottom(hb1);

		Scene scene = Main.createScene(bp);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		Main.setStageIcon(stage);
		stage.show();

	}
}

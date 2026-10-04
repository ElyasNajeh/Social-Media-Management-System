package FxSocial;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public boolean ConfirmationAlert(String title, String message) {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle(title);
		a.setContentText(message);
		Optional<ButtonType> res = a.showAndWait();
		return res.isPresent() && res.get() == ButtonType.OK;
	}

	public void ErrorAlert(String title, String message) {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle(title);
		a.setContentText(message);
		a.showAndWait();
	}

	public void InfoAlert(String title, String message) {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setTitle(title);
		a.setContentText(message);
		a.showAndWait();

	}

}

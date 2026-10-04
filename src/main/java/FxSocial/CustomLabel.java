package FxSocial;

import javafx.scene.control.Label;

public class CustomLabel extends Label {
	CustomLabel(String text) {
		super(text);
		this.setStyle("-fx-text-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;"
				+ "-fx-font-family: 'Segoe UI Semibold';"
				+ "-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);" + "-fx-padding: 6 18;"
				+ "-fx-background-radius: 12px;" + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 4, 0, 0, 1);");

	}

}

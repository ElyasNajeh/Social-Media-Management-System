package FxSocial;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class IconButton extends Button {
	IconButton(String text, String imagePath) {
		super(text);
		Image icon = new Image(imagePath);
		ImageView im = new ImageView(icon);
		im.setFitWidth(40);
		im.setFitHeight(40);
		this.setGraphic(im);

		this.setStyle("-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);" + "-fx-text-fill: white;"
				+ "-fx-font-size: 22px;" + "-fx-font-family: 'Segoe UI', sans-serif;" + "-fx-font-weight: bold;"
				+ "-fx-padding: 18px 26px;" + "-fx-min-width: 200px;" + "-fx-min-height: 65px;"
				+ "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;" + "-fx-border-color: transparent;"
				+ "-fx-cursor: hand;");

		this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: linear-gradient(to right, #8e2de2, #4a00e0);"
				+ "-fx-text-fill: white;" + "-fx-font-size: 22px;" + "-fx-font-family: 'Segoe UI', sans-serif;"
				+ "-fx-font-weight: bold;" + "-fx-padding: 18px 26px;" + "-fx-min-width: 200px;"
				+ "-fx-min-height: 65px;" + "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;"
				+ "-fx-border-color: transparent;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.6), 15, 0.3, 0, 0);"));

		this.setOnMouseExited(e -> this.setStyle("-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);"
				+ "-fx-text-fill: white;" + "-fx-font-size: 22px;" + "-fx-font-family: 'Segoe UI', sans-serif;"
				+ "-fx-font-weight: bold;" + "-fx-padding: 18px 26px;" + "-fx-min-width: 200px;"
				+ "-fx-min-height: 65px;" + "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;"
				+ "-fx-border-color: transparent;" + "-fx-cursor: hand;"));

		this.setOnMousePressed(e -> {
			this.setScaleX(0.98);
			this.setScaleY(0.98);
		});

		this.setOnMouseReleased(e -> {
			this.setScaleX(1);
			this.setScaleY(1);
		});

		this.setEffect(new DropShadow(15, Color.web("#00000066")));
	}

}

package fr.univ_amu.iut.exercice8;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class ConvertisseurTemperatures extends Application {

  private boolean updating = false;

  @Override
  public void start(Stage primaryStage) {
    Label labelC = new Label("°C");
    labelC.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

    Slider sliderC = new Slider(0, 100, 0);
    sliderC.setOrientation(Orientation.VERTICAL);
    sliderC.setId("slider-celsius");

    TextField tfC = new TextField();
    tfC.setId("tf-celsius");
    tfC.setMaxWidth(50);

    VBox panneauC = new VBox(10, labelC, sliderC, tfC);
    panneauC.setAlignment(Pos.CENTER);
    panneauC.setPadding(new Insets(10));

    Label labelF = new Label("°F");
    labelF.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

    Slider sliderF = new Slider(0, 212, 32);
    sliderF.setOrientation(Orientation.VERTICAL);
    sliderF.setId("slider-fahrenheit");

    TextField tfF = new TextField();
    tfF.setId("tf-fahrenheit");
    tfF.setMaxWidth(50);

    VBox panneauF = new VBox(10, labelF, sliderF, tfF);
    panneauF.setAlignment(Pos.CENTER);
    panneauF.setPadding(new Insets(10));

    sliderC
        .valueProperty()
        .addListener(
            (obs, old, newVal) -> {
              if (!updating) {
                updating = true;
                sliderF.setValue(newVal.doubleValue() * 9.0 / 5.0 + 32);
                updating = false;
              }
            });

    sliderF
        .valueProperty()
        .addListener(
            (obs, old, newVal) -> {
              if (!updating) {
                updating = true;
                sliderC.setValue((newVal.doubleValue() - 32) * 5.0 / 9.0);
                updating = false;
              }
            });

    Bindings.bindBidirectional(
        tfC.textProperty(), sliderC.valueProperty(), new NumberStringConverter());
    Bindings.bindBidirectional(
        tfF.textProperty(), sliderF.valueProperty(), new NumberStringConverter());

    HBox root = new HBox(30, panneauC, panneauF);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));

    primaryStage.setScene(new Scene(root, 350, 350));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

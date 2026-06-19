package fr.univ_amu.iut.exercice7;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CercleInteractif extends Application {

  private final Circle cercle = new Circle();
  private final Slider slider = new Slider();
  private final TextField textField = new TextField();
  private final Pane panneauCercle = new Pane();
  private final BorderPane root = new BorderPane();

  @Override
  public void start(Stage primaryStage) {
    ajouterPanneau();
    ajouterSlider();
    ajouterTextField();
    creerBindings();

    primaryStage.setScene(new Scene(root, 550, 600));
    primaryStage.show();
  }

  void ajouterPanneau() {
    cercle.setFill(Color.web("#cfe8fb"));
    cercle.setStroke(Color.web("#2980b9"));
    panneauCercle.getChildren().add(cercle);
    panneauCercle.setPrefSize(500, 500);
    panneauCercle.setId("panneau");
    cercle.setId("cercle");
    root.setCenter(panneauCercle);
  }

  void ajouterSlider() {
    slider.setMin(0);
    slider.setMax(250);
    slider.setId("slider");
    root.setTop(slider);
  }

  void ajouterTextField() {
    TextFormatter<Number> formatter = new TextFormatter<>(new NumberStringConverter());
    textField.setTextFormatter(formatter);
    textField.setId("rayon");
    root.setBottom(textField);
  }

  void creerBindings() {
    cercle.centerXProperty().bind(panneauCercle.widthProperty().divide(2));
    cercle.centerYProperty().bind(panneauCercle.heightProperty().divide(2));

    cercle.radiusProperty().bindBidirectional(slider.valueProperty());

    Bindings.bindBidirectional(
        textField.textProperty(), slider.valueProperty(), new NumberStringConverter());

    slider.setValue(150);
  }

  public static void main(String[] args) {
    launch(args);
  }
}

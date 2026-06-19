package fr.univ_amu.iut.exercice3;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaletteReactive extends Application {

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();

    BoutonCouleur btnRouge = new BoutonCouleur("Rouge", "red");
    btnRouge.setId("btn-rouge");
    BoutonCouleur btnVert = new BoutonCouleur("Vert", "green");
    btnVert.setId("btn-vert");
    BoutonCouleur btnBleu = new BoutonCouleur("Bleu", "blue");
    btnBleu.setId("btn-bleu");
    HBox hbox = new HBox(10, btnRouge, btnVert, btnBleu);
    root.setTop(hbox);

    Pane zone = new Pane();
    zone.setId("zone");
    zone.setMinSize(300, 200);
    root.setCenter(zone);

    Label compteurs = new Label();
    compteurs.setId("compteurs");
    compteurs.setMaxWidth(Double.MAX_VALUE);
    compteurs.setAlignment(Pos.CENTER);
    root.setBottom(compteurs);

    createBindings(btnRouge, btnVert, btnBleu, zone, compteurs);

    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  void createBindings(
      BoutonCouleur btnRouge,
      BoutonCouleur btnVert,
      BoutonCouleur btnBleu,
      Pane zone,
      Label labelCompteurs) {

    btnRouge
        .nbClicsProperty()
        .addListener((obs, oldV, newV) -> zone.setStyle("-fx-background-color: red;"));

    btnVert
        .nbClicsProperty()
        .addListener((obs, oldV, newV) -> zone.setStyle("-fx-background-color: green;"));

    btnBleu
        .nbClicsProperty()
        .addListener((obs, oldV, newV) -> zone.setStyle("-fx-background-color: blue;"));

    var texteCompteurs =
        Bindings.concat(
            "Rouge: ",
            btnRouge.nbClicsProperty().asString(),
            "  Vert: ",
            btnVert.nbClicsProperty().asString(),
            "  Bleu: ",
            btnBleu.nbClicsProperty().asString());

    var aucunClic =
        btnRouge
            .nbClicsProperty()
            .add(btnVert.nbClicsProperty())
            .add(btnBleu.nbClicsProperty())
            .isEqualTo(0);

    labelCompteurs
        .textProperty()
        .bind(Bindings.when(aucunClic).then("Bienvenue !").otherwise(texteCompteurs));
  }

  public static void main(String[] args) {
    launch(args);
  }
}

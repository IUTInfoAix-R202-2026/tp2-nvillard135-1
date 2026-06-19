package fr.univ_amu.iut.exercice6;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FormulaireConnexion extends Application {

  private TextField userId;
  private PasswordField pwd;
  private Button okBtn;
  private Button cancelBtn;
  private Label message;

  @Override
  public void start(Stage primaryStage) {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20));
    grid.setHgap(10);
    grid.setVgap(10);

    userId = new TextField();
    userId.setId("user-id");

    pwd = new PasswordField();
    pwd.setId("pwd");

    okBtn = new Button("OK");
    okBtn.setId("btn-ok");

    cancelBtn = new Button("Annuler");
    cancelBtn.setId("btn-cancel");

    message = new Label();
    message.setId("message");

    grid.add(new Label("Identifiant :"), 0, 0);
    grid.add(userId, 1, 0);
    grid.add(new Label("Mot de passe :"), 0, 1);
    grid.add(pwd, 1, 1);
    grid.add(okBtn, 0, 2);
    grid.add(cancelBtn, 1, 2);
    grid.add(message, 0, 3, 2, 1);

    createBindings();

    okBtn.setOnAction(e -> okClicked());
    cancelBtn.setOnAction(e -> cancelClicked());

    primaryStage.setScene(new Scene(grid, 350, 200));
    primaryStage.show();
  }

  void createBindings() {
    pwd.editableProperty().bind(Bindings.greaterThanOrEqual(userId.textProperty().length(), 6));

    cancelBtn
        .disableProperty()
        .bind(
            Bindings.and(
                Bindings.equal(0, pwd.textProperty().length()),
                Bindings.equal(0, userId.textProperty().length())));

    BooleanBinding motDePasseInvalide =
        new BooleanBinding() {
          {
            super.bind(pwd.textProperty());
          }

          @Override
          protected boolean computeValue() {
            String p = pwd.getText();
            return p.length() < 8
                || p.chars().noneMatch(Character::isUpperCase)
                || p.chars().noneMatch(Character::isDigit);
          }
        };
    okBtn.disableProperty().bind(motDePasseInvalide);
  }

  void okClicked() {
    message.setText(
        "Bienvenue " + userId.getText() + " (" + "*".repeat(pwd.getText().length()) + ")");
  }

  void cancelClicked() {
    userId.clear();
    pwd.clear();
    message.setText("");
  }

  public static void main(String[] args) {
    launch(args);
  }
}

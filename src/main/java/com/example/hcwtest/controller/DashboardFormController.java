package com.example.hcwtest.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    public AnchorPane root;
    public Label lblPasswordLoader;
    public Label lblUsernameLoader;
    private String username;

    private String password;

    public DashboardFormController() {

    }
    public DashboardFormController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {

        this.username = username;
        lblUsernameLoader.setText(this.username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
        lblPasswordLoader.setText(this.password);
    }

    public void btnChangeOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/changeCredential_form.fxml"));
        AnchorPane anchorPane = loader.load();

        ChangeCredentialFormController controller = loader.getController();
        controller.setUsername(username);
        controller.setPassword(password);

        System.out.println(username +"\n"+ password);

        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("AdminDash");
        stage.centerOnScreen();

        stage.show();
    }

    public void btnRefreshOnAction(MouseEvent mouseEvent) {

    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        AnchorPane anchorPane = loader.load();


        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();

        stage.show();
    }
}

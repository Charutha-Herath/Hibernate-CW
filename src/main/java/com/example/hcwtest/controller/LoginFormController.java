package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {


    public AnchorPane MainPane;
    public TextField txtUsername;
    public TextField txtPassword;

    UserBo userBo = (UserBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    public void btnSignIn(ActionEvent actionEvent) throws IOException {
        String uname = txtUsername.getText();
        String pwd = txtPassword.getText();

        System.out.println(uname);
        System.out.println(pwd);

        if (uname.isEmpty()| pwd.isEmpty()  ) {
            new Alert(Alert.AlertType.ERROR,"please fill all fields").show();
        }else {

            if (userBo.checkCredential(new UserDto(uname,pwd)) ){
                MainPane.getChildren().clear();
                MainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            }else{
                new Alert(Alert.AlertType.WARNING,"Incorrect Username Or Password").show();
            }

            txtUsername.clear();
            txtPassword.clear();
        }
    }

    public void btnRegister(ActionEvent actionEvent) throws IOException {

        Parent pane = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));

        Scene scene = new Scene(pane);

        Stage stage = new Stage();

        stage.setTitle("SignUp Users");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
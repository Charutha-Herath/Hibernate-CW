package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeCredentialFormController {


    public TextField txtUsername;
    public TextField txtNewPassword;
    public TextField txtConfirmPassword;

    private String username;

    private String password;

    UserBo userBo = (UserBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    public ChangeCredentialFormController() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {

        System.out.println("Credential "+username+"  "+password);

        String id = userBo.getUserId(username,password);

        System.out.println("getUserId : "+id);
        System.out.println(txtUsername.getText()+"   "+ txtNewPassword.getText());

        if (txtUsername.getText().isEmpty() || txtNewPassword.getText().isEmpty()  || txtConfirmPassword.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else{

            if (txtNewPassword.getText().equals(txtConfirmPassword.getText())){

                if (userBo.updateNewCredential(id,password, new UserDto(txtUsername.getText(),txtNewPassword.getText()))){
                    System.out.println("update");



                    AdminDashFormController adminDashFormController = new AdminDashFormController();
                    System.out.println(txtUsername.getText());
                    System.out.println(txtNewPassword.getText());
                    /*adminDashFormController.setUsername(txtUsername.getText());
                    adminDashFormController.setPassword(txtNewPassword.getText());*/

                    txtUsername.clear();
                    txtNewPassword.clear();
                    txtConfirmPassword.clear();
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"Password does not match").show();

                txtNewPassword.clear();
                txtConfirmPassword.clear();
            }

        }

    }
}

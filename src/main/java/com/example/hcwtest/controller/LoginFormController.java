package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dto.UserDto;
import com.example.hcwtest.util.RegexUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {


    public AnchorPane MainPane;
    public TextField txtUsername;
    public TextField txtPassword;
    public Label idHide;
    public Label idShow;
    public PasswordField passwordField;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    UserBo userBo = (UserBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);


    public void initialize(){
        idHide.setVisible(false);
    }

    public void btnSignIn(ActionEvent actionEvent) throws IOException {

        //loadAdminDash();
        //loadUserDash();

        String uname = txtUsername.getText();
        String pwd = passwordField.getText();

        System.out.println(uname);
        System.out.println(pwd);

        if (uname.isEmpty()|| pwd.isEmpty()  ) {
            new Alert(Alert.AlertType.ERROR,"please fill all fields").show();
        }else {

            if (!RegexUtil.matchesRegex(uname, "^[a-zA-Z0-9_]{4,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").show();
            return;
            }

            if (!RegexUtil.matchesRegex(pwd, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid password").show();
                return;
            }



            if (userBo.checkCredential(new UserDto(uname,pwd)) ){

                /*MainPane.getChildren().clear();
                MainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));*/

                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
                Parent dashboardParent = loader.load();
                DashboardFormController dashboardFormController = loader.getController();
                dashboardFormController.setUsername(uname);
                dashboardFormController.setPassword(pwd);*/
                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
                Parent dashboardParent = loader.load();
                DashboardController dashboardController = loader.getController();
                dashboardController.setUsername(uname);*/





                /*MainPane.getChildren().clear();
                MainPane.getChildren().add(dashboardParent);*/

                String id = userBo.getUserId(uname,pwd);

                if (id.startsWith("U")){
                    loadUserDash();
                    /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
                    AnchorPane anchorPane = loader.load();
                    DashboardFormController dashboardFormController = loader.getController();
                    dashboardFormController.setUsername(uname);
                    dashboardFormController.setPassword(pwd);

                    Scene scene = new Scene(anchorPane);

                    Stage stage = (Stage) MainPane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("UserDash");
                    stage.centerOnScreen();

                    stage.show();*/


                }else{
                    loadAdminDash();

                    /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDash_form.fxml"));
                    AnchorPane anchorPane = loader.load();
                    AdminDashFormController adminDashFormController = loader.getController();
                    adminDashFormController.setUsername(uname);
                    adminDashFormController.setPassword(pwd);
                    adminDashFormController.setId(id);

                    Scene scene = new Scene(anchorPane);

                    Stage stage = (Stage) MainPane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("AdminDash");
                    stage.centerOnScreen();

                    stage.show();*/
                }


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

    public void loadAdminDash() throws IOException {

        String uname = txtUsername.getText();
        String pwd = txtPassword.getText();

        String id = userBo.getUserId(uname,pwd);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDash_form.fxml"));
        AnchorPane anchorPane = loader.load();
        AdminDashFormController adminDashFormController = loader.getController();
        adminDashFormController.setUsername(uname);
        adminDashFormController.setPassword(pwd);
        adminDashFormController.setId(id);

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) MainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("AdminDash");
        stage.centerOnScreen();

        stage.show();

    }

    public void loadUserDash() throws IOException {

        String uname = txtUsername.getText();
        String pwd = txtPassword.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        AnchorPane anchorPane = loader.load();
        DashboardFormController dashboardFormController = loader.getController();
        dashboardFormController.setUsername(uname);
        dashboardFormController.setPassword(pwd);
        dashboardFormController.setUsernamePassword(uname,pwd);
        /*dashboardFormController.initializeHistory();*/
        String id = userBo.getUserId(uname,pwd);
        dashboardFormController.loadHistory(id);

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) MainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("UserDash");
        stage.centerOnScreen();

        stage.show();
    }

    public void hideOnMouseClick(MouseEvent mouseEvent) {
        String password = txtPassword.getText();
        txtPassword.setText(password);
        idHide.setVisible(false);
        idShow.setVisible(true);
        passwordField.setVisible(true);
        txtPassword.setVisible(false);
    }

    public void showMouseOnClick(MouseEvent mouseEvent) {
        String password = passwordField.getText();
        txtPassword.setText(password);
        idShow.setVisible(false);
        idHide.setVisible(true);
        passwordField.setVisible(false);
        txtPassword.setVisible(true);
    }
}
package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dto.UserDto;
import com.example.hcwtest.util.RegexUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterFormController {

    public AnchorPane MainPane;
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtPassword;
    public TextField txtConfirmPassword;

    UserBo userBo = (UserBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    public void btnRegister(ActionEvent actionEvent) {

        String uname = txtUsername.getText();
        String mail = txtEmail.getText();
        String pwd = txtPassword.getText();
        String conPwd = txtConfirmPassword.getText();

        if (uname.isEmpty() | mail.isEmpty() | pwd.isEmpty() | conPwd.isEmpty() ) {

            new Alert(Alert.AlertType.ERROR,"please fill all fields").show();
        }else{

            if (!RegexUtil.matchesRegex(mail, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid email address").show();
                return;
            }

            if (!RegexUtil.matchesRegex(uname, "^[a-zA-Z0-9_]{4,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid username").show();
                return;
            }

            if (!RegexUtil.matchesRegex(pwd, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid password").show();
                return;
            }
            if (!pwd.equals(conPwd)) {
                new Alert(Alert.AlertType.ERROR,"Password and Confirm Password fields does not match").show();

                txtPassword.clear();
                txtConfirmPassword.clear();
            }else {
                String id =userBo.generateId();
                userBo.saveUser(new UserDto(id,uname,mail,pwd));
                new Alert(Alert.AlertType.CONFIRMATION,"Registration Successfully!").show();

                txtUsername.clear();
                txtEmail.clear();
                txtPassword.clear();
                txtConfirmPassword.clear();

                Stage stage = (Stage) MainPane.getScene().getWindow();
                stage.close();
            }

        }
    }
}

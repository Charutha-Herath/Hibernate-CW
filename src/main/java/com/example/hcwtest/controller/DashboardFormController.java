package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.BookBo;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.dto.Tm.BookTm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardFormController {

    public AnchorPane root;
    public Label lblPasswordLoader;
    public Label lblUsernameLoader;
    public TableView tblBook;
    public TableColumn colBookId;
    public TableColumn colBookTitle;
    public TableColumn colBookAuthor;
    public TableColumn colBookGenre;
    public TableColumn colBookAvailability;
    public TableColumn colBookBranch;
    public TableColumn colBookOptions;
    public TextField txtSearch;
    public TableView tblUserHistory;
    public TableColumn colHistoryTid;
    public TableColumn colHistoryBookID;
    public TableColumn colHistoryBorrowedDate;
    public TableColumn colHistoryRetDate;
    public TableColumn colHistoryStatus;
    private String username;

    private String password;

    BookBo bookBo = (BookBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOK);

    UserBo userBo = (UserBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

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


    public void initializerBooks(){

    }

    public void searchOnAction(ActionEvent actionEvent) {

    }

    public void booksSearchOnAction(ActionEvent actionEvent) {

    }

    private void loadBooks() {
        ObservableList<BookTm> oblist = FXCollections.observableArrayList();
        List<BookDto> list = bookBo.getAllBooks();
        for(BookDto dto : list){
            oblist.add(
                    new BookTm(
                            dto.getBookId(),
                            dto.getTitle(),
                            dto.getAuthor(),
                            dto.getGenre(),
                            dto.getStatus(),
                            dto.getBranch(),
                            loadJFXButton()
                    )
            );
        }
        for (int i = 0; i < oblist.size(); i++) {
            JFXButton bt =   oblist.get(i).getButton();
            int finalI = i;
            int finalI1 = i;
            if (oblist.get(finalI1).getStatus().startsWith("borrowed")){
                bt.setText("borrowed");
                bt.setDisable(true);
            }
            bt.setOnAction(actionEvent -> {
                boolean b = userBo.addTransaction(oblist.get(finalI).getBookId(), username);
                if (b){ bt.setStyle("-fx-background-color: green");
                    bt.setText("borrowed");
                    bt.setDisable(true);
                }
                /*loadLogs();*/
            });
        }
        tblBook.setItems(oblist);
        tblBook.refresh();

    }

    private JFXButton loadJFXButton() {
        return new JFXButton("burrow");
    }
}

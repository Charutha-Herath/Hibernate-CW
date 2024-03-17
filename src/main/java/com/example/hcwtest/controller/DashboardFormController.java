package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.BookBo;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.dto.BranchDto;
import com.example.hcwtest.dto.Tm.BookTm;
import com.example.hcwtest.dto.Tm.BranchTm;
import com.example.hcwtest.dto.Tm.TransactionTm;
import com.example.hcwtest.dto.TransactionDto;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public TableColumn colBookOptions;
    public TextField txtSearch;
    public TableView tblUserHistory;
    public TableColumn colHistoryTid;
    public TableColumn colHistoryBookID;
    public TableColumn colHistoryBorrowedDate;
    public TableColumn colHistoryRetDate;
    public TableColumn colHistoryStatus;
    public Label lblPleaseLogout;
    private String username;

    private String password;

    private String array [] = new String[2];

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
        loadHistory(username);

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
        lblPasswordLoader.setText("********");
        array[1] = password;
    }

    public void setUsernamePassword(String uname, String password){
        //String id = userBo.getUserId(uname,password);

        //setCellValueFactoryHistory();
        //System.out.println("xxxxxxx: "+id+"   "+uname+"    "+ password);
        //loadHistory(id);
    }

    public void initialize(){
        initializerBooks();
        //initializeHistory();
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
        stage.setTitle("Change Credentials");
        stage.centerOnScreen();

        stage.show();




    }

    public void setLogoutMsg() throws IOException {
        /*new Alert(Alert.AlertType.WARNING,"Please logout and login again").show();*/

        //lblPleaseLogout.setText("Please logout and\nlogin again");

    }

    public void btnRefreshOnAction(MouseEvent mouseEvent) {
        //lblUsernameLoader.setText(userBo.getNewUsername(id));
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
        setCellValueFactoryBook();
        loadBooks();
    }

    public void searchOnAction(ActionEvent event) {
        String search = txtSearch.getText();
        List<BookDto> dtoList = bookBo.searchBookByName(search);
        ObservableList<BookTm> oblist = FXCollections.observableArrayList();

        for (BookDto dto :dtoList ) {

            oblist.add(
                    new BookTm(
                            dto.getBookId(),
                            dto.getTitle(),
                            dto.getAuthor(),
                            dto.getGenre(),
                            dto.getStatus(),
                            loadJFXButton(dto.getStatus())
                    ));
        }

        for (int i = 0; i < oblist.size(); i++) {
            JFXButton bt =   oblist.get(i).getButton();
            int finalI = i;
            int finalI1 = i;
            if (oblist.get(finalI1).getStatus().startsWith(" Borrowed")){
                bt.setText("Borrowed");
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

    public void booksSearchOnAction(ActionEvent actionEven) {
        String search = txtSearch.getText();
        List<BookDto> dtoList = bookBo.searchBookByName(search);
        ObservableList<BookTm> oblist = FXCollections.observableArrayList();

        for (BookDto dto :dtoList ) {

        oblist.add(
                new BookTm(
                        dto.getBookId(),
                        dto.getTitle(),
                        dto.getAuthor(),
                        dto.getGenre(),
                        dto.getStatus(),
                        loadJFXButton(dto.getStatus())
                ));
        }

        for (int i = 0; i < oblist.size(); i++) {
            JFXButton bt =   oblist.get(i).getButton();
            int finalI = i;
            int finalI1 = i;
            if (oblist.get(finalI1).getStatus().startsWith(" Borrowed")){
                bt.setText("Borrowed");
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

    private void setCellValueFactoryBook(){
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colBookGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBookAvailability.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBookOptions.setCellValueFactory(new PropertyValueFactory<>("button"));
        /*colBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));*/




        colHistoryTid.setCellValueFactory(new PropertyValueFactory<>("TransactionId"));
        colHistoryBookID.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        colHistoryBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        colHistoryRetDate.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        colHistoryStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

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
                            /*dto.getBranch(),*/
                            loadJFXButton(dto.getStatus())


                    )
            );
        }
        for (int i = 0; i < oblist.size(); i++) {
            JFXButton bt =   oblist.get(i).getButton();
            int finalI = i;
            int finalI1 = i;
            if (oblist.get(finalI1).getStatus().startsWith(" Borrowed")){
                bt.setText("Borrowed");
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

    private JFXButton loadJFXButton(String x) {
        System.out.println("x@@@@@@@@@ :  "+x);
        JFXButton button = new JFXButton("burrow");
        button.setStyle("-fx-background-color: #26ea1f; -fx-text-fill: black; -fx-alignment: center; -fx-content-display: center;");
        if (x.equals("No")|x.equals("NO")){
            button.setDisable(true);
            setCellValueFactoryBook();
            String id = userBo.getUserId(username,password);
            loadHistory(id);
        }
        return button;
    }


    //------------------------------------------------ History ------------------------------------------------------

    public void initializeHistory(){
        //setCellValueFactoryHistory();
        //loadHistory();
    }

    public void setCellValueFactoryHistory(){

        colHistoryTid.setCellValueFactory(new PropertyValueFactory<>("TransactionId"));
        colHistoryBookID.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        colHistoryBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        colHistoryRetDate.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        colHistoryStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        System.out.println("tblUserHistory1111111");
    }
    public void loadHistory(String id){
        /*System.out.println("detail "+getUsername()+"    "+getPassword());*/
        /*System.out.println("detail "+lblUsernameLoader.getText()+"    "+lblPasswordLoader.getText());*/
        System.out.println("detail "+array[0]+"    "+array[1]);

        /*String id = userBo.getUserId(array[0],array[1]);*/
        System.out.println("userIdHistory  "+id);

        ObservableList<TransactionTm> oblist = FXCollections.observableArrayList();
        List<TransactionDto> list = userBo.getAllTransactionsForThisUser(id);
        for (TransactionDto d : list){
            oblist.add(
                    new TransactionTm(
                            d.getTransactionId(),
                            d.getBook_name(),
                            d.getBorrowed_date(),
                            d.getReturn_date(),
                            d.isStatus()
                    )
            );
            System.out.println("tblUserHistory22222");
            tblUserHistory.setItems(oblist);
            tblUserHistory.refresh();
        }
    }

    public void bookTableRefresh(MouseEvent mouseEvent) {
        setCellValueFactoryBook();
        loadBooks();
    }
}

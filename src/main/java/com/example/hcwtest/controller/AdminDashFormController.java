package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.BookBo;
import com.example.hcwtest.bo.custom.BranchBo;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.dto.Tm.BookTm;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdminDashFormController {

    public AnchorPane root;
    public Label lblPasswordLoader;
    public Label lblUsernameLoader;
    public TextField txtTitle;
    public Label lblBookId;
    public TextField txtAuthor;
    public TextField txtGenre;
    public ComboBox comboStatus;
    public TableColumn colBookId;
    public TableColumn colTitle;
    public TableColumn colAuthor;
    public TableColumn colGenre;
    public TableColumn colStatus;
    public TableColumn colBranch;
    public TableView tblBooks;
    public JFXButton idBtnAdd;
    public JFXButton idBtnUpdate;
    public JFXButton idBtnRemove;
    public TableView tblBranch;
    public TableColumn colBranchId;
    public TableColumn colBranchName;
    public TableColumn colBranchManager;
    public TableColumn colBranchBookTotal;
    public Label lblBranchId;
    public TextField txtName;
    public TextField txtManager;
    public TextField txtBookTotal;
    public JFXButton idBtnAddBranch;
    public JFXButton idBtnUpdateBranch;
    public JFXButton idBtnRemoveBranch;

    private String id;
    private String username;

    private String password;

    BookBo bookBo = (BookBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOK);

    BranchBo branchBo = (BranchBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BRANCH);

    public AdminDashFormController() {

    }

    public AdminDashFormController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AdminDashFormController(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "AdminDashFormController{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void initialize(){

        comboLoader();
        setCellValueFactory();
        loadAllBooks();
        generateNextID();
        tableListener();
        btnDisabler();


        //branchInitialize();
    }



    public void comboLoader(){
        ObservableList<String> list = FXCollections.observableArrayList("Yes","No");
        comboStatus.setItems(list);
    }

    private void setCellValueFactory(){
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));

    }

    private void loadAllBooks(){

        ObservableList<BookTm> obList = FXCollections.observableArrayList();

            List<BookDto> dtoList = bookBo.getAllBooks();
            //List<CoachDto> dtoList =model.getAllUsers();
            for (BookDto dto :dtoList) {
                obList.add(
                        new BookTm(
                                dto.getBookId(),
                                dto.getTitle(),
                                dto.getAuthor(),
                                dto.getGenre(),
                                dto.getStatus(),
                                dto.getBranch()
                        )

                );
            }
            tblBooks.setItems(obList);

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

    public void btnRefreshOnAction(ActionEvent actionEvent) {

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

    public void btnAddOnAction(ActionEvent actionEvent) {

        String bookId = lblBookId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String comStatus = (String) comboStatus.getValue();


        if(bookId.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || comStatus==null){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else {
            bookBo.addBook(new BookDto(bookId,title,author,genre,comStatus));

            generateNextID();

            txtTitle.clear();
            txtAuthor.clear();
            txtGenre.clear();
            comboLoader();

            setCellValueFactory();
            loadAllBooks();


        }
    }

    public void generateNextID(){
       String id = bookBo.generateId();
       lblBookId.setText(id);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String bookId = lblBookId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String comStatus = (String) comboStatus.getValue();

        if(bookId.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || comStatus==null){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else {

            boolean flag = bookBo.bookUpdate(new BookDto(bookId,title,author,genre,comStatus));

            generateNextID();

            txtTitle.clear();
            txtAuthor.clear();
            txtGenre.clear();
            comboLoader();

            setCellValueFactory();
            loadAllBooks();

            idBtnAdd.setDisable(false);
            idBtnUpdate.setDisable(true);
            idBtnRemove.setDisable(true);

        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        String bookId = lblBookId.getText();


        if(bookId.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else {
            if (bookBo.deleteBook(bookId)){

                generateNextID();

                txtTitle.clear();
                txtAuthor.clear();
                txtGenre.clear();
                comboLoader();

                setCellValueFactory();
                loadAllBooks();

                idBtnAdd.setDisable(false);
                idBtnUpdate.setDisable(true);
                idBtnRemove.setDisable(true);

            }


        }
    }

    public  void tableListener() {
        tblBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);



            if (newValue != null) {
                idBtnUpdate.setDisable(false);
                idBtnRemove.setDisable(false);
                idBtnAdd.setDisable(true);
                setData((BookTm) newValue);
            } else {
                // Handle the case where newValue is null (optional)

            }
        });
    }
    private void setData(BookTm row) {

        /*System.out.println(row.toString());

        lblBookId.setText(row.getBookId());
        txtTitle.setText(row.getTitle());
        txtAuthor.setText(row.getAuthor());
        txtGenre.setText(row.getGenre());*/

        if (row != null) {
            System.out.println(row.toString());

            lblBookId.setText(row.getBookId());
            txtTitle.setText(row.getTitle());
            txtAuthor.setText(row.getAuthor());
            txtGenre.setText(row.getGenre());
            /*comboStatus.getItems().add(row.getStatus());*/
        }

    }


    public void refreshOnMouseClicked(MouseEvent mouseEvent) {
        generateNextID();

        txtTitle.clear();
        txtAuthor.clear();
        txtGenre.clear();
        comboLoader();

        idBtnUpdate.setDisable(true);
        idBtnRemove.setDisable(true);
        idBtnAdd.setDisable(false);



    }

    public void btnDisabler(){
        idBtnUpdate.setDisable(true);
        idBtnRemove.setDisable(true);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------


    private void branchInitialize() {
        generateNextBranchID();
    }


    public void btnBranchAddOnAction(ActionEvent actionEvent) {

    }

    public void refreshOnMouseBranchClicked(MouseEvent mouseEvent) {

    }

    public void btnBranchUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnBranchRemoveOnAction(ActionEvent actionEvent) {

    }

    public void generateNextBranchID(){
        String id = branchBo.generateId();
        lblBranchId.setText(id);
    }
}

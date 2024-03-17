package com.example.hcwtest.controller;

import com.example.hcwtest.bo.BOFactory;
import com.example.hcwtest.bo.custom.BookBo;
import com.example.hcwtest.bo.custom.BranchBo;
import com.example.hcwtest.bo.custom.TransactionBo;
import com.example.hcwtest.bo.custom.UserBo;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.dto.BranchDto;
import com.example.hcwtest.dto.Tm.BookTm;
import com.example.hcwtest.dto.Tm.BranchTm;
import com.example.hcwtest.dto.Tm.TransactionTm;
import com.example.hcwtest.dto.Tm.UserTm;
import com.example.hcwtest.dto.TransactionDto;
import com.example.hcwtest.dto.UserDto;
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

    public JFXButton idBtnAddBranch;
    public JFXButton idBtnUpdateBranch;
    public JFXButton idBtnRemoveBranch;
    public TextField txtBranchName;
    public TextField txtBranchManager;
    public TextField txtBranchBookTotal;
    public ComboBox comboBranches;
    public TableView tblTransaction;
    public TableColumn colTransactionTrancID;
    public TableColumn colTransactionBookName;
    public TableColumn colTransactionUserName;
    public TableColumn colTransactionBorrowDate;
    public TableColumn colTransactionReturnDate;
    public TableColumn colTransactionStatus;
    public TableView tblUser;
    public TableColumn colUserID;
    public TableColumn colUserName;
    public TableColumn colUserEmail;
    public Label lblPleaseLogout;
    public Label lblAdminId;
    public TextField txtAdminName;
    public TextField txtAdminEmail;

    private String id;
    private String username;

    private String password;

    BookBo bookBo = (BookBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOK);

    BranchBo branchBo = (BranchBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BRANCH);

    UserBo userBo = (UserBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    TransactionBo transactionBo = (TransactionBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSACTION);

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
        lblPasswordLoader.setText("********");
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

        //------------------------------------------------------- Branch -------------------------------------------------------------
        branchInitialize();



        //-------------------------------------------------------- User ---------------------------------------------------------

        userInitializer();

        //----------------------------------------------------  Transaction  ---------------------------------------------------

        setCellValueFactoryTrans();
        loadTrans();
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
        /*colBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));*/

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
                                dto.getStatus()
                                /*dto.getBranch()*/
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

    public void btnRefreshCredentialOnAction(ActionEvent actionEvent) {



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
        /*String comBranch = (String) comboBranches.getValue();*/


        if(bookId.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || comStatus==null /*|| comBranch==null*/){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else {

            if (!author.matches("^[a-zA-Z\\s.'-]{3,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid author").show();
                return;
            }

            if (!title.matches("^[a-zA-Z0-9\\s.'-]{3,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid book title").show();
                return;
            }

            if (!genre.matches("^[a-zA-Z\\s,.'-]{3,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid genre").show();
                return;
            }
            bookBo.addBook(new BookDto(bookId,title,author,genre,comStatus));

            generateNextID();

            txtTitle.clear();
            txtAuthor.clear();
            txtGenre.clear();
            comboLoader();
            comboBranches.getItems().clear();
            comboBranchLoader();

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

    //---------------------------------------------------------  Branch  -----------------------------------------------------------------------


    private void branchInitialize() {

        generateNextBranchID();
        comboBranchLoader();

        setCellValueFactoryBranches();
        loadAllBranches();

        idBtnRemoveBranch.setDisable(true);
        idBtnUpdateBranch.setDisable(true);

        tableBranchListener();
    }


    public void btnBranchAddOnAction(ActionEvent actionEvent) {
        String branchId = lblBranchId.getText();
        String branchName = txtBranchName.getText();
        String branchManager = txtBranchManager.getText();
        String branchBookTotal = txtBranchBookTotal.getText();

        if (branchId.isEmpty()||branchManager.isEmpty()||branchName.isEmpty()||branchBookTotal.isEmpty()){

        }else {
            if (!branchName.matches("^[a-zA-Z0-9\\s,.'-]{3,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid location").show();
                return;
            }

            /*if (!String.valueOf(bookNo).matches("^[0-9]+$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid number of books").show();
                return;
            }*/

            if (!branchManager.matches("^[a-zA-Z\\s]{3,}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid branch manager").show();
                return;
            }
        branchBo.saveBranch(new BranchDto(branchId,branchName,branchManager,branchBookTotal));

        generateNextBranchID();

        txtBranchName.clear();
        txtBranchManager.clear();
        txtBranchBookTotal.clear();

        idBtnAddBranch.setDisable(false);
        idBtnUpdateBranch.setDisable(true);
        idBtnRemoveBranch.setDisable(true);

        setCellValueFactoryBranches();
        loadAllBranches();
        }

    }

    public void refreshOnMouseBranchClicked(MouseEvent mouseEvent) {

    }

    public void btnBranchUpdateOnAction(ActionEvent actionEvent) {
        String branchId = lblBranchId.getText();
        String bName = txtBranchName.getText();
        String manager = txtBranchManager.getText();
        String bookTotal = txtBranchBookTotal.getText();


        if(branchId.isEmpty() || bName.isEmpty() || manager.isEmpty() || bookTotal.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else {

            boolean flag = branchBo.branchUpdate(new BranchDto(branchId, bName, manager, bookTotal));

            generateNextBranchID();

            txtBranchName.clear();
            txtBranchManager.clear();
            txtBranchBookTotal.clear();


            setCellValueFactoryBranches();
            loadAllBranches();

            idBtnAddBranch.setDisable(false);
            idBtnUpdateBranch.setDisable(true);
            idBtnRemoveBranch.setDisable(true);

        }
    }

    public void btnBranchRemoveOnAction(ActionEvent actionEvent) {
        String branchId = lblBranchId.getText();


        if(branchId.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields").show();
        }else {
            if (branchBo.deleteBranch(branchId)){

                generateNextBranchID();

                txtBranchName.clear();
                txtBranchBookTotal.clear();
                txtBranchManager.clear();


                setCellValueFactoryBranches();
                loadAllBranches();

                idBtnAddBranch.setDisable(false);
                idBtnUpdateBranch.setDisable(true);
                idBtnRemoveBranch.setDisable(true);

            }


        }
    }

    public void generateNextBranchID(){
        String id = branchBo.generateId();
        lblBranchId.setText(id);
    }

    public void comboBranchLoader(){

        List<String> list = branchBo.getBranchName();
        comboBranches.getItems().addAll(FXCollections.observableArrayList(list));
        /*ObservableList<String> list = FXCollections.observableArrayList("Yes","No");
        comboStatus.setItems(list);*/
    }

    private void setCellValueFactoryBranches(){
        colBranchId.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        colBranchName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBranchManager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        colBranchBookTotal.setCellValueFactory(new PropertyValueFactory<>("book_total"));


    }

    private void loadAllBranches(){

        ObservableList<BranchTm> obList = FXCollections.observableArrayList();

        List<BranchDto> dtoList = branchBo.getAllBranches();

        for (BranchDto dto :dtoList) {
            obList.add(
                    new BranchTm(
                            dto.getBranchId(),
                            dto.getName(),
                            dto.getManager(),
                            dto.getBook_total()

                    )

            );
        }
        tblBranch.setItems(obList);

    }


    public void filterOverDueOnMouseClicked(MouseEvent mouseEvent) {

    }

    public  void tableBranchListener() {
        tblBranch.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);



            if (newValue != null) {
                idBtnAddBranch.setDisable(true);
                idBtnRemoveBranch.setDisable(false);
                idBtnUpdateBranch.setDisable(false);

                setBranchData((BranchTm) newValue);
            } else {
                // Handle the case where newValue is null (optional)

            }
        });
    }
    private void setBranchData(BranchTm row) {

        /*System.out.println(row.toString());

        lblBookId.setText(row.getBookId());
        txtTitle.setText(row.getTitle());
        txtAuthor.setText(row.getAuthor());
        txtGenre.setText(row.getGenre());*/

        if (row != null) {
            System.out.println(row.toString());

            lblBranchId.setText(row.getBranchId());
            txtBranchName.setText(row.getName());
            txtBranchManager.setText(row.getManager());
            txtBranchBookTotal.setText(row.getBook_total());
            /*comboStatus.getItems().add(row.getStatus());*/
        }

    }







    //------------------------------------------------------------   User  ---------------------------------------------------------------


    public void userInitializer(){
        setCellValueFactoryUsers();
        loadAllUsers();
        generateNextAdminId();
    }

    private void setCellValueFactoryUsers(){
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


    }


    private void loadAllUsers(){
        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        List<UserDto> dtoList = userBo.getAllUsers();

        for (UserDto dto :dtoList) {
            obList.add(
                    new UserTm(
                            dto.getUserId(),
                            dto.getUsername(),
                            dto.getEmail()
                    )

            );
        }
        tblUser.setItems(obList);
    }

    public void generateNextAdminId(){
        String id =userBo.generateAdminId();
        lblAdminId.setText(id);

    }

    public void btnSaveAdminOnAction(ActionEvent actionEvent) {
        String username = txtAdminName.getText();
        String email = txtAdminEmail.getText();
        if (username.isEmpty() | email.isEmpty()) {

            new Alert(Alert.AlertType.ERROR,"please fill all fields").show();
        }else{

            String id = lblAdminId.getText();
            userBo.saveAdmin(new UserDto(id,username,email,"111111"));

            txtAdminName.clear();
            txtAdminEmail.clear();
            userInitializer();
            new Alert(Alert.AlertType.CONFIRMATION,"Save new admin successfully!").show();


        }

    }

    //-------------------------------------------------- Transaction  ----------------------------------------------------

    public void setCellValueFactoryTrans(){

        colTransactionTrancID.setCellValueFactory(new PropertyValueFactory<>("TransactionId"));
        colTransactionBookName.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        colTransactionUserName.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colTransactionBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        colTransactionReturnDate.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        colTransactionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        System.out.println("tblUserHistory1111111");
    }
    public void loadTrans(){
       /* System.out.println("detail "+getUsername()+"    "+getPassword());
        System.out.println("detail "+lblUsernameLoader.getText()+"    "+lblPasswordLoader.getText());
        System.out.println("detail "+array[0]+"    "+array[1]);

        String id = userBo.getUserId(array[0],array[1]);
        System.out.println("userIdHistory  "+id);*/

        ObservableList<TransactionTm> oblist = FXCollections.observableArrayList();
        List<TransactionDto> list = transactionBo.getAllTransactions();
        for (TransactionDto dto : list){

            oblist.add(
                    new TransactionTm(
                            dto.getTransactionId(),
                            dto.getBook_name(),
                            dto.getUserId(),
                            dto.getBorrowed_date(),
                            dto.getReturn_date(),
                            dto.isStatus()
                    )
            );
            System.out.println("tblUserHistory22222");
            tblTransaction.setItems(oblist);
            tblTransaction.refresh();
        }
    }

    public void btnBranchFilterOverdue(ActionEvent actionEvent) {

    ObservableList<TransactionTm> oblist = FXCollections.observableArrayList();
    List<TransactionDto> list = transactionBo.getOverDueUsers();
        for (TransactionDto dto : list) {
        oblist.add(
                new TransactionTm(
                        dto.getTransactionId(),
                        dto.getBook_name(),
                        dto.getUserId(),
                        dto.getBorrowed_date(),
                        dto.getReturn_date(),
                        dto.isStatus()
                )
        );
    }
        tblTransaction.setItems(oblist);
        tblTransaction.refresh();
    }

    public void btnTransRefreshOnMouseClicked(MouseEvent mouseEvent) {
        setCellValueFactoryTrans();
        loadTrans();
    }


}


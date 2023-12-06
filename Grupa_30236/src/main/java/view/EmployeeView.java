package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;
import model.builder.BookBuilder;

import java.time.LocalDate;
import java.util.List;

public class EmployeeView {

    private TextField titleField;
    private TextField authorField;
    private Button addButton;
    private Button searchButton;
    private Button updateButton;
    private Button deleteButton;
    private TableView<Book> bookTableView;
    private TextField idField;
    private DatePicker PublishedDateField;

    private ComboBox<String> searchCriteriaComboBox;


    // Alte câmpuri necesare pentru interacțiunea cu cărțile

    public EmployeeView(Stage primaryStage) {
        primaryStage.setTitle("Employee Interface");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);

        initializeSceneTitle(gridPane);

        initializeFields(gridPane);

        bookTableView = new TableView<>();
        initializeBookTableView(gridPane);


        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane){
        Text sceneTitle = new Text("Employee Dashboard");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }

    private void initializeFields(GridPane gridPane){
        // Implementează inițializarea câmpurilor pentru afișarea datelor din cărți și pentru interacțiunea cu ele
        // Aceasta poate include tabel, câmpuri pentru adăugare/modificare, butoane etc.
        Label idLabel = new Label("Id:");
        gridPane.add(idLabel, 0, 1);
        idField = new TextField();
        gridPane.add(idField, 1, 1);

        Label titleLabel = new Label("Title:");
        gridPane.add(titleLabel, 0, 2);
        titleField = new TextField();
        gridPane.add(titleField, 1, 2);

        Label authorLabel = new Label("Author:");
        gridPane.add(authorLabel, 0, 3);
        authorField = new TextField();
        gridPane.add(authorField, 1, 3);



        Label addPublishedDateLabel = new Label("Published Date:");
        gridPane.add(addPublishedDateLabel, 0, 4);

        PublishedDateField = new DatePicker();
        gridPane.add(PublishedDateField, 1, 4);

        addButton = new Button("Add Book");
        gridPane.add(addButton, 0, 7, 2, 1);

        searchButton = new Button("Search Book");
        gridPane.add(searchButton, 1, 7);

        updateButton = new Button("Update Book");
        gridPane.add(updateButton, 2, 7);

        deleteButton = new Button("Delete Book");
        gridPane.add(deleteButton, 3, 7);

        searchCriteriaComboBox = new ComboBox<>();
        searchCriteriaComboBox.getItems().addAll("Id", "Title", "Author", "Published Date");
        searchCriteriaComboBox.setValue("Id"); // Seteaza implicit criteriul de cautare
        gridPane.add(searchCriteriaComboBox, 2, 1);
    }

    private void initializeBookTableView(GridPane gridPane) {
        TableColumn<Book, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, LocalDate> publishedDateColumn = new TableColumn<>("Published Date");
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

        bookTableView.getColumns().addAll(idColumn, titleColumn, authorColumn, publishedDateColumn);
        gridPane.add(bookTableView, 0, 5, 3, 1);
    }
    public void displayBooks(List<Book> books) {
        bookTableView.getItems().clear(); // Curăță tabelul pentru a preveni dublarea informațiilor
        ObservableList<Book> observableList = FXCollections.observableArrayList(books);
        bookTableView.setItems(observableList);
    }
    public String getSearchTitle() {
        return this.titleField.getText();
    }
    public String getSearchAuthor()
    {
        return this.authorField.getText();
    }
    public Long getSearchId()
    {
        return Long.valueOf(this.idField.getText());
    }
    public LocalDate getSearchDate()
    {
        return this.PublishedDateField.getValue();
    }
    public String getSelectedSearchCriteria() {
        return searchCriteriaComboBox.getValue();
    }
    public void addAddButtonListener(EventHandler<ActionEvent> handler) {
        // Metoda pentru adăugarea unei cărți
        addButton.setOnAction(event -> {
            String title = titleField.getText();
            String author = authorField.getText();
            LocalDate publishedDate = PublishedDateField.getValue();

            // Trimite datele către controller
            handler.handle(event);
        });
    }

    public Book getBookDataFromFields() {
        String title = titleField.getText();
        String author = authorField.getText();
        LocalDate publishedDate = PublishedDateField.getValue();

        // Crează și returnează o nouă carte cu datele din interfață
        Book newBook = new BookBuilder()
                .setTitle(title)
                .setAuthor(author)
                .setPublishedDate(publishedDate)
                .build();
        return newBook;
    }
    public void addSearchButtonListener(EventHandler<ActionEvent> handler) {
        // Metoda pentru căutarea unei cărți
        searchButton.setOnAction(event -> {
            // Apelarea metodei pentru căutare
            handler.handle(event);
        });
    }
    public void addUpdateButtonListener(EventHandler<ActionEvent> handler) {
        // Metoda pentru căutarea unei cărți
        updateButton.setOnAction(event -> {
            // Apelarea metodei pentru căutare
            handler.handle(event);
        });
    }
    public void addDeleteButtonListener(EventHandler<ActionEvent> handler) {
        // Metoda pentru ștergerea unei cărți
        deleteButton.setOnAction(handler);
    }
}

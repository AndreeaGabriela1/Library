package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import javafx.scene.control.SelectionMode; // Import pentru SelectionMode

import java.util.List;

public class CustomerView {
    private Stage stage;
    private VBox root;
    private ListView<String> bookListView;

    private Button buyBooksButton;

    public CustomerView(Stage primaryStage) {
        this.stage = primaryStage;
        initialize();
    }

    private void initialize() {
        root = new VBox(10);
        bookListView = new ListView<>();
        bookListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buyBooksButton = new Button("Buy Selected Books");

        root.getChildren().addAll(new Label("Customer View"), bookListView, buyBooksButton);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void displayBooks(List<Book> books) {
        for (Book book : books) {
            bookListView.getItems().add(book.getTitle()+" "+book.getAuthor());
        }
    }

    public List<String> getSelectedBooks() {
        return bookListView.getSelectionModel().getSelectedItems();
    }

    public void addBuyBooksListener(EventHandler<ActionEvent> handler) {
        buyBooksButton.setOnAction(handler);
    }
}



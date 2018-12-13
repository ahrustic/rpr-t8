package ba.unsa.etf.rpr.tutorijal8;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button traziBtn;
    public Button prekiniBtn;
    public TextField searchBox;
    public ListView list;
    private ObservableList fileList = FXCollections.observableArrayList();
    private Pretraga pretraga;

    public void onTrazi(ActionEvent actionEvent) {
        pretraga = new Pretraga(this);
        fileList.clear();

        Thread pretragaThread = new Thread(pretraga);
        pretragaThread.start();
        prekidacZaPretrazivanje(true);
    }

    public void onPrekini(ActionEvent actionEvent) {
        System.out.println("Prekid!");
        pretraga.stop();
        prekidacZaPretrazivanje(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setItems(fileList);
        list.getItems().addListener((ListChangeListener)(promjena) -> list.scrollTo(list.getItems().size()-1));
    }

    public ObservableList<File> getFileList() {
        return fileList;
    }

    public void prekidacZaPretrazivanje(boolean vrijednost) {
        traziBtn.setDisable(vrijednost);
        prekiniBtn.setDisable(!vrijednost);
    }


    private void otvoriFile() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("formular.fxml"));
            stage.setTitle("Formular");
            stage.setScene(new Scene(root, 317, 200));
            stage.initOwner(list.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void otvori(MouseEvent mouseEvent) {
            ObservableList file = list.getSelectionModel().getSelectedItems();
            if (file == null){
                System.out.println("Nista nije izabrano!");
            }
            else {
                otvoriFile();
            }
    }
}

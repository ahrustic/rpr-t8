package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Formular implements Initializable {
    public TextField postanski;
    public GridPane mainPane;
    public TextField naziv;
    public TextField adresa;
    public TextField grad;
    private boolean nazivValidan;
    private boolean adresaValidan;
    private boolean gradValidan;

    private boolean ispravnost(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z'))
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean ispravnostAdrese(String n) {
        if (n.length() == 0) return false;
        return true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazivValidan = false;
        adresaValidan = false;
        gradValidan = false;

        naziv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    naziv.getStyleClass().removeAll("nijeValidan");
                    naziv.getStyleClass().add("validan");
                    nazivValidan = true;
                }
                else {
                    naziv.getStyleClass().removeAll("validan");
                    naziv.getStyleClass().add("nijeValidan");
                    nazivValidan = false;
                }
            }
        });

        adresa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnostAdrese(n)) {
                    adresa.getStyleClass().removeAll("nijeValidan");
                    adresa.getStyleClass().add("validan");
                    adresaValidan = true;
                }
                else {
                    adresa.getStyleClass().removeAll("validan");
                    adresa.getStyleClass().add("nijeValidan");
                    adresaValidan = false;
                }
            }
        });

        grad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    grad.getStyleClass().removeAll("nijeValidan");
                    grad.getStyleClass().add("validan");
                    gradValidan = true;
                }
                else {
                    grad.getStyleClass().removeAll("validan");
                    grad.getStyleClass().add("nijeValidan");
                    gradValidan = false;
                }
            }
        });
        if (postanski != null){
        postanski.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal)
                validanPostanski();
        });
        }
    }

    private boolean daLiJePostanskiValidan() {
        String apiURL = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=";
        try {
            URL url = new URL(apiURL + postanski.getText().trim());
            BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String res = ulaz.readLine();
            return res.trim().equals("OK");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void validanPostanski() {
        new Thread(() -> {
            System.out.println("Pokrenut program za validnost!");
            if(daLiJePostanskiValidan()) {
                Platform.runLater(() -> postanski.getStyleClass().add("validan"));
                System.out.println("Validan!");
            }else {
                Platform.runLater(() -> postanski.getStyleClass().add("nijeValidan"));
                System.out.println("Nije validan!");
            }
        }).start();
    }

}

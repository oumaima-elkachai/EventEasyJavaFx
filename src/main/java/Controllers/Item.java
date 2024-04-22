package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Lieu;

public class Item {
    @FXML
    private Label Cap;

    @FXML
    private Label DateD;

    @FXML
    private Label DateF;

    @FXML
    private Label Nom;

    @FXML
    private Label Prix;

    @FXML
    private Label Reg;

    @FXML
    private ImageView img;
    @FXML
    void deletelieu(ActionEvent event) {

    }

    @FXML
    void editlieu(ActionEvent event) {

    }

    public void setLieu(Lieu lieu) {
        Nom.setText(lieu.getNom());
        Prix.setText(String.valueOf(lieu.getPrix()));
        Reg.setText(lieu.getRegion());
        Cap.setText(String.valueOf(lieu.getCapacity()));
        DateD.setText(lieu.getDate_d());
        DateF.setText(lieu.getDate_f());
    }
}

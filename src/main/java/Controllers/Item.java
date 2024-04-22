package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Lieu;

public class Item {
    @FXML
    private ImageView img;

    @FXML
    private Label Nom;

    @FXML
    private Label Reg;

    @FXML
    private Label Prix;

    @FXML
    private Label Cap;

    public void setLieu(Lieu lieu) {
        Nom.setText(lieu.getNom());
        Prix.setText(String.valueOf(lieu.getPrix()));
        Reg.setText(lieu.getRegion());
        Cap.setText(String.valueOf(lieu.getCapacity()));
    }
}

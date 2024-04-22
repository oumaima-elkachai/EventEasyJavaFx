package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Lieu;
import services.LieuService;

import java.sql.Date;

public class ModifierLieu {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prixTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField dateDebutTextField;

    @FXML
    private TextField dateFinTextField;

    @FXML
    private TextField capaciteTextField;

    @FXML
    private TextField regionTextField;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private TextField longitudeTextField;

    private Lieu lieu;
    private LieuService lieuService = new LieuService();

    public void initData(Lieu lieu) {
        this.lieu = lieu;
        // Initialisez les champs du formulaire avec les données du lieu sélectionné
        nomTextField.setText(lieu.getNom());
        prixTextField.setText(String.valueOf(lieu.getPrix()));
        imageTextField.setText(lieu.getImage());
        dateDebutTextField.setText(String.valueOf(lieu.getDate_d()));
        dateFinTextField.setText(String.valueOf(lieu.getDate_f()));
        capaciteTextField.setText(String.valueOf(lieu.getCapacity()));
        regionTextField.setText(lieu.getRegion());
        latitudeTextField.setText(String.valueOf(lieu.getLatitude()));
        longitudeTextField.setText(String.valueOf(lieu.getLongitude()));
    }

    @FXML
    void modifierLieu() {
        // Mettez à jour les propriétés du lieu avec les valeurs des champs de texte
        lieu.setNom(nomTextField.getText());
        lieu.setPrix(Double.parseDouble(prixTextField.getText()));
        lieu.setImage(imageTextField.getText());
        lieu.setDate_d(Date.valueOf(dateDebutTextField.getText()));
        lieu.setDate_f(Date.valueOf(dateFinTextField.getText()));
        lieu.setCapacity(Integer.parseInt(capaciteTextField.getText()));
        lieu.setRegion(regionTextField.getText());
        lieu.setLatitude(Double.parseDouble(latitudeTextField.getText()));
        lieu.setLongitude(Double.parseDouble(longitudeTextField.getText()));

        // Appelez la méthode de service pour modifier le lieu dans la base de données
        lieuService.modifier(lieu);

        // Affichez une boîte de dialogue pour informer l'utilisateur que la modification a réussi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText("Le lieu a été modifié avec succès.");
        alert.showAndWait();
    }
}

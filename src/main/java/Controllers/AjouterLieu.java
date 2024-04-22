package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Category_l;
import models.Lieu;
import services.LieuService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AjouterLieu {

    @FXML
    private TextField LName;

    @FXML
    private Button btnImage;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dateD;

    @FXML
    private DatePicker dateF;

    @FXML
    private TextField lCapacity;

    @FXML
    private ChoiceBox<Category_l> lCategory;

    @FXML
    private TextField lPrix;

    @FXML
    private TextField lRegion;

    @FXML
    private ImageView imagL;

    private LieuService lieuService = new LieuService();
    private String imageUrl;



    @FXML
    void initialize() {
        // Charge les catégories depuis la base de données et les ajoute à la ChoiceBox
        lCategory.getItems().addAll(lieuService.getAllCategories());
        // Chargez les catégories pour le ChoiceBox
        loadCategories();
    }
    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        Stage stage = (Stage) btnSave.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                imageUrl = file.toURI().toURL().toString(); // Stocker l'URL de l'image
                Image imag = new Image(imageUrl, 101, 127, false, true);
                imagL.setImage(imag);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while loading the image!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void addLieu(ActionEvent event) {



        try {
            String name = LName.getText();
            int capacity = Integer.parseInt(lCapacity.getText());
            Category_l category = lCategory.getValue();
            float prix = Float.parseFloat(lPrix.getText());
            String region = lRegion.getText();
            LocalDate localDateD = dateD.getValue();
            LocalDate localDateF = dateF.getValue();
            String imag = imageUrl;


            if (name.isEmpty() || category == null || localDateD == null || localDateF == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs obligatoires !");
                alert.showAndWait();
                return;
            }

            // Convertir LocalDate en java.sql.Date
            java.sql.Date sqlDateD = java.sql.Date.valueOf(localDateD);
            java.sql.Date sqlDateF = java.sql.Date.valueOf(localDateF);

            // Créer un objet Lieu avec les données du formulaire
            Lieu newLieu = new Lieu(category, name, prix, imag, sqlDateD, sqlDateF, capacity, region, 0, 0);
            // Ajouter le lieu en base de données
            lieuService.ajouter(newLieu);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lieu ajouté");
            alert.setHeaderText(null);
            alert.setContentText("Le lieu a été ajouté avec succès !");
            alert.showAndWait();

            // Redirection vers la page d'affichage des lieux (AfficherLieu.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLieu.fxml"));
            Parent root = loader.load();
            Stage stage1 = (Stage) btnSave.getScene().getWindow();
            stage1.setScene(new Scene(root));
            stage1.setTitle("Afficher Lieu");
            stage1.show();

        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir des valeurs valides !");
            alert.showAndWait();
        }
    }
    private void loadCategories() {
        // Récupérer la liste des catégories depuis le service
        List<Category_l> categories = LieuService.getAllCategories();

        // Convertir la liste en ObservableList
        ObservableList<Category_l> observableCategories = FXCollections.observableArrayList(categories);

        // Assigner la liste observable au ChoiceBox
        lCategory.setItems(observableCategories);

        // Définir un convertisseur de chaîne pour afficher les noms de catégorie
        lCategory.setConverter(new StringConverter<Category_l>() {
            @Override
            public String toString(Category_l category) {
                return category == null ? "" : category.getNom();
            }

            @Override
            public Category_l fromString(String string) {
                // Si vous souhaitez permettre à l'utilisateur d'ajouter de nouvelles catégories en saisissant du texte,
                // vous pouvez ajouter une logique ici pour créer une nouvelle catégorie à partir de la chaîne de texte.
                // Sinon, vous pouvez simplement retourner null ici.
                return null;
            }
        });
    }





}

package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Lieu;
import services.LieuService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AfficherLieu {
    @FXML
    private Button btnAfficher;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button btnAfficherL;

    @FXML
    private Button btnAfficherCat;

    private LieuService lieuService = new LieuService();

    @FXML
    void initialize() {
        // Récupérer la liste des lieux depuis le service
        List<Lieu> lieux = lieuService.afficher();

        // Créer un conteneur pour les cartes
        VBox cardContainer = new VBox(10); // Espacement vertical entre les cartes

        // Ajouter les cartes au conteneur
        for (Lieu lieu : lieux) {
            AnchorPane card = createCard(lieu);
            cardContainer.getChildren().add(card);
        }

        // Ajouter le conteneur de cartes au ScrollPane
        scroll.setContent(cardContainer);
    }

    private AnchorPane createCard(Lieu lieu) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(300, 200); // Taille de la carte (largeur, hauteur)

        // Créer un ImageView pour afficher l'image du lieu
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100); // Largeur de l'image
        imageView.setFitHeight(100); // Hauteur de l'image
        imageView.setLayoutX(10);
        imageView.setLayoutY(10);

        // Charger l'image à partir de l'URL ou du chemin d'accès spécifié dans le lieu
        try {
            String imagePath = lieu.getImage(); // Méthode hypothétique pour obtenir le chemin de l'image
            Image image = new Image(imagePath);
            imageView.setImage(image);
        } catch (Exception e) {
            // Gérer les erreurs de chargement d'image
            e.printStackTrace();
        }

        // Ajouter l'ImageView à la carte
        card.getChildren().add(imageView);

        // Ajouter du contenu supplémentaire à la carte (nom, région, prix, capacité, date de début, date de fin)
        Label nomLabel = new Label("Nom: " + lieu.getNom());
        nomLabel.setLayoutX(120);
        nomLabel.setLayoutY(10);

        Label regionLabel = new Label("Région: " + lieu.getRegion());
        regionLabel.setLayoutX(120);
        regionLabel.setLayoutY(40);

        Label prixLabel = new Label("Prix: " + lieu.getPrix());
        prixLabel.setLayoutX(120);
        prixLabel.setLayoutY(70);

        Label capaciteLabel = new Label("Capacité: " + lieu.getCapacity());
        capaciteLabel.setLayoutX(120);
        capaciteLabel.setLayoutY(100);

        Label debutLabel = new Label("Date de début: " + lieu.getDate_d());
        debutLabel.setLayoutX(120);
        debutLabel.setLayoutY(130);

        Label finLabel = new Label("Date de fin: " + lieu.getDate_f());
        finLabel.setLayoutX(120);
        finLabel.setLayoutY(160);

        // Ajouter les éléments supplémentaires à la carte
        card.getChildren().addAll(nomLabel, regionLabel, prixLabel, capaciteLabel, debutLabel, finLabel);

        // Créer un bouton de suppression
        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> {
            // Demander confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment supprimer ce lieu et tous ses événements associés ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression
                supprimerLieuEtEvenements(lieu); // Supprimer le lieu et ses événements
                refreshView(); // Rafraîchir la vue
            }
        });

        // Ajouter le bouton de suppression à la carte
        card.getChildren().add(deleteButton);

        // Créer un bouton de mise à jour
        Button updateButton = new Button("Modifier");
        updateButton.setOnAction(event -> modifierLieu(event, lieu));

        // Ajouter le bouton de mise à jour à la carte
        card.getChildren().add(updateButton);
        // Définir la marge entre les boutons Supprimer et Modifier
        AnchorPane.setTopAnchor(updateButton, 50.0); // Espacement de 50 pixels entre le bouton Supprimer et le bouton Modifier


        return card;
    }

    // Méthode pour supprimer un lieu et tous ses événements associés
    private void supprimerLieuEtEvenements(Lieu lieu) {
        // Implémentez la logique de suppression du lieu et de ses événements
    }

    // Méthode pour rafraîchir la vue après la suppression
    private void refreshView() {
        scroll.setContent(null); // Effacer le contenu actuel du ScrollPane
        initialize(); // Recharger les lieux et afficher à nouveau
    }

    // Méthode pour gérer la mise à jour d'un lieu
    private void modifierLieu(ActionEvent event, Lieu lieu) {
        // Ouvrir une nouvelle fenêtre ou boîte de dialogue pour la modification du lieu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierLieu.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierLieu controller = loader.getController();
            controller.initData(lieu); // Passer le lieu sélectionné au contrôleur de la vue de modification

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherLieu(ActionEvent event) {
        // Get the source node of the event
        Node sourceNode = (Node) event.getSource();

        // Get the scene of the source node
        Scene scene = sourceNode.getScene();

        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("AfficherLieu.fxml"));

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the new stage
            stage.show();

            // Close the current stage
            ((Stage) scene.getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherL(ActionEvent event) {
        try {
            // Charger la vue des lieux fournit
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLieu.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle
            Stage stage = (Stage) btnAfficher.getScene().getWindow();

            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void afficherC(ActionEvent event) {
        try {
            // Charger la vue des lieux fournit
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCategoryL.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle
            Stage stage = (Stage) btnAfficherCat.getScene().getWindow();

            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

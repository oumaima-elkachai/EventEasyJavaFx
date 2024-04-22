package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.Lieu;
import services.LieuService;

import java.util.List;

public class AfficherLieu {

    @FXML
    private ScrollPane scroll;
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

        // Ajouter du contenu supplémentaire à la carte (nom, région, prix, capacité)
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

        // Ajouter les éléments supplémentaires à la carte
        card.getChildren().addAll(nomLabel, regionLabel, prixLabel, capaciteLabel);

        return card;
    }

}

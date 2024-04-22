package Controllers;


        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;
        import models.Category_l;
        import services.CategoryService;
        import utils.DataSource;

        import java.io.IOException;
        import java.sql.Connection;
        import java.util.*;


public class AjouterCategoryL {
    private Connection connection = DataSource.getInstance().getConnection();
    @FXML
    private TableColumn<Category_l, Integer> IdN;

    @FXML
    private TableColumn<Category_l, String> NomId;

    @FXML
    private Button btnAfficher;
    @FXML
    private Button btnAfficherCat;



    @FXML
    private Button btnSave;
    @FXML
    private TextField searchCat;

    @FXML
    private TextField cName;

    @FXML
    private TableView<Category_l> tableCat;
    CategoryService ps = new CategoryService();
    @FXML
    void initialize() {
        List<Category_l> Categoryls = ps.afficher();
        this.tableCat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Category_l> observableList= FXCollections.observableList(Categoryls);
        tableCat.setItems(observableList);
        IdN.setCellValueFactory(new PropertyValueFactory<>("Id"));
        NomId.setCellValueFactory(new PropertyValueFactory<>("Nom"));

        this.tableCat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.cName.setText(newValue.getNom());
            }

        });
        IdN.setSortable(true);
        NomId.setSortable(true);

    }

    @FXML
    void createCategory(ActionEvent event) {
        if (!this.validateInput()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Données invalides");
            alert.setHeaderText((String)null);
            alert.setContentText("Veuillez remplir tous les champs requis correctement.");
            alert.showAndWait();
        } else {
            Category_l newCategory = new Category_l(this.cName.getText());
            this.ps.ajouter(newCategory);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Catégorie ajoutée");
            alert.setHeaderText((String)null);
            alert.setContentText("La catégorie a été ajoutée avec succès !");
            alert.showAndWait();
            this.refreshTableView();
            this.clearFields();
        }


    }

    private boolean validateInput() {
        boolean isValid = true;
        String name = this.cName.getText();
        if (!name.isEmpty() && name.matches("[a-zA-Z]+")) {
            this.cName.setStyle((String)null);
        } else {
            this.cName.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        return isValid;
    }
    private void refreshTableView() {
        List<Category_l> categories = this.ps.afficher();
        ObservableList<Category_l> observableList = FXCollections.observableList(categories);
        this.tableCat.setItems(observableList);
        this.tableCat.refresh();
    }
    @FXML
    void supprimerC(ActionEvent event) {
        Category_l selectedCategory = (Category_l) this.tableCat.getSelectionModel().getSelectedItem();
        Alert alert;
        if (selectedCategory != null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette catégorie ?");
            alert.setContentText("Catégorie : " + selectedCategory.getNom() + "\n\nCette action est irréversible. Veuillez confirmer.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                this.ps.supprimer(selectedCategory);
                this.refreshTableView();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Suppression réussie");
                successAlert.setHeaderText("Catégorie supprimée !");
                successAlert.setContentText("La catégorie \"" + selectedCategory.getNom() + "\" a été supprimée avec succès.");
                successAlert.showAndWait();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sélection manquante");
            alert.setHeaderText("Erreur de sélection");
            alert.setContentText(" Il semble que vous n'ayez pas sélectionné de catégorie. Veuillez en choisir une à supprimer.");
            alert.showAndWait();
        }

    }
    @FXML
    void modifierC(ActionEvent event) {
        Category_l selectedCategory = (Category_l) this.tableCat.getSelectionModel().getSelectedItem();
        Alert successAlert;
        if (selectedCategory != null) {
            selectedCategory.setNom(this.cName.getText());
            this.ps.modifier(selectedCategory);
            this.refreshTableView();
            successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Mise à jour réussie");
            successAlert.setHeaderText("Catégorie mise à jour");
            successAlert.setContentText("La catégorie \"" + selectedCategory.getNom() + "\" a été mise à jour avec succès.");
            successAlert.showAndWait();
        } else {
            successAlert = new Alert(Alert.AlertType.ERROR);
            successAlert.setTitle("Erreur de sélection");
            successAlert.setHeaderText("Aucune catégorie sélectionnée");
            successAlert.setContentText("Oups ! Veuillez sélectionner une catégorie à mettre à jour.");
            successAlert.showAndWait();
        }

        this.clearFields();
    }
    @FXML
    void clearFields(ActionEvent event) {
        this.cName.setText("");
    }

    void clearFields() {
        this.cName.setText("");
    }
    @FXML
    void searchC() {
            String searchText = searchCat.getText().toLowerCase();
            List<Category_l> allCategories = this.ps.afficher();
            List<Category_l> filteredCategories = new ArrayList<>();
            Iterator var4 = allCategories.iterator();

            while(true) {
                Category_l category;
                boolean matchesId;
                boolean matchesName;
                do {
                    if (!var4.hasNext()) {
                        ObservableList<Category_l> observableList = FXCollections.observableList(filteredCategories);
                        this.tableCat.setItems(observableList);
                        return;
                    }

                    category = (Category_l) var4.next();
                    matchesId = String.valueOf(category.getId()).contains(searchText);
                    matchesName = category.getNom().toLowerCase().contains(searchText);
                } while(!matchesId && !matchesName);

                filteredCategories.add(category);
            }
        }
    @FXML
    void afficherLieu(ActionEvent event) {
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
    void afficherCat(ActionEvent event) {
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



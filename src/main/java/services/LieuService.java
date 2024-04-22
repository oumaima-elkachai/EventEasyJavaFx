package services;

import models.Category_l;
import models.Lieu;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LieuService implements ILieuService<Lieu> {
    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Lieu lieu) {
        String req = "INSERT INTO `lieu`(`nom`, `prix`,`image`,`date_d`,`date_f`,`capacity`,`region`,`latitude`,`longitude`,`category_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, lieu.getNom());
            pst.setDouble(2, lieu.getPrix());
            pst.setString(3, lieu.getImage());
            pst.setDate(4, lieu.getDate_d());
            pst.setDate(5, lieu.getDate_f());
            pst.setInt(6, lieu.getCapacity());
            pst.setString(7, lieu.getRegion());
            pst.setDouble(8, lieu.getLatitude());
            pst.setDouble(9, lieu.getLongitude());
            pst.setInt(10, lieu.getCategory_id().getId());
            pst.executeUpdate();
            System.out.println("Lieu ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Lieu lieu) {
        String req = "DELETE FROM `lieu` WHERE `id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, lieu.getId());
            pst.executeUpdate();
            System.out.println("Lieu supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Lieu lieu) {
        String req = "UPDATE `lieu` SET `nom` = ?, `prix` = ?,`image`= ?,`date_d`= ?,`date_f`= ?,`capacity`= ?,`region`= ?,`latitude`= ?,`longitude`= ?,`category_id`=? WHERE `id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, lieu.getNom());
            pst.setDouble(2, lieu.getPrix());
            pst.setString(3, lieu.getImage());
            pst.setDate(4, lieu.getDate_d());
            pst.setDate(5, lieu.getDate_f());
            pst.setInt(6, lieu.getCapacity());
            pst.setString(7, lieu.getRegion());
            pst.setDouble(8, lieu.getLatitude());
            pst.setDouble(9, lieu.getLongitude());
            pst.setInt(10, lieu.getCategory_id().getId());
            pst.setInt(11, lieu.getId());

            pst.executeUpdate();
            System.out.println("Lieu modifié avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Lieu> afficher() {
        List<Lieu> lieus = new ArrayList<>();

        String req = "SELECT l.*, c.nom AS nom_categorie " +
                "FROM lieu l " +
                "INNER JOIN category_l c ON l.category_id = c.id";

        try (PreparedStatement pst = connection.prepareStatement(req);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                Date date_d = rs.getDate("date_d");
                Date date_f = rs.getDate("date_f");
                int capacity = rs.getInt("capacity");
                String region = rs.getString("region");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String image = rs.getString("image");
                String categorieNom = rs.getString("nom_categorie");

                // Créer un objet Lieu avec les données récupérées de la base de données
                Lieu lieu = new Lieu(id, new Category_l(categorieNom), nom, prix, date_d, date_f, capacity, region, latitude, longitude, image);
                // Ajouter l'objet Lieu à la liste
                lieus.add(lieu);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lieus;
    }

    public Lieu obtenirLieuParId(int idLieu) {
        Lieu lieu = null;
        String req = "SELECT * FROM lieu WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, idLieu);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    double prix = rs.getDouble("prix");
                    Date date_d = rs.getDate("date_d");
                    Date date_f = rs.getDate("date_f");
                    int capacity = rs.getInt("capacity");
                    String region = rs.getString("region");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
                    String image = rs.getString("image");
                    int categoryId = rs.getInt("category_id");
                    Category_l category = obtenirCategorieParId(categoryId); // Méthode à implémenter
                    lieu = new Lieu(id, category, nom, prix, date_d, date_f, capacity, region, latitude, longitude, image);

                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du lieu par ID : " + e.getMessage());
        }
        return lieu;
    }

    // Méthode pour obtenir une catégorie par son ID
    private Category_l obtenirCategorieParId(int categoryId) {
        Category_l category = null;
        String req = "SELECT * FROM category_l WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, categoryId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    category = new Category_l(id, nom);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la catégorie par ID : " + e.getMessage());
        }
        return category;
    }
    public static List<Category_l> getAllCategories() {
        CategoryService categoryService = new CategoryService(); // Créez une instance de CategoryService
        return categoryService.afficher(); // Utilisez la méthode afficher() pour récupérer les catégories
    }

}

package services;

import models.Category_l;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryLService<Category_l>{
    private Connection connection = DataSource.getInstance().getConnection();

    public CategoryService() {
    }

    public void ajouter(Category_l category_l) {
        // Vérifier si la catégorie existe déjà
        List<Category_l> existingCategories = afficher();
        for (Category_l existingCategory : existingCategories) {
            if (existingCategory.getNom().equals(category_l.getNom())) {
                System.out.println("La catégorie avec le nom '" + category_l.getNom() + "' existe déjà.");
                return; // Sortir de la méthode sans ajouter la catégorie
            }
        }

        // Si la catégorie n'existe pas, l'ajouter
        String req = "INSERT INTO `category_l`(`nom`) VALUES (?);";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, category_l.getNom());
            pst.executeUpdate();
            System.out.println("Category ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(Category_l category_l) {
        String req = "DELETE FROM `category_l` WHERE `category_l`.`id` = ?;" ;

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, category_l.getId());
            pst.executeUpdate();
            System.out.println("Category supprimer avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void modifier(Category_l category_l) {
        String req = "UPDATE `category_l` SET `nom` = ? WHERE `category_l`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, category_l.getNom());
            pst.setInt(2, category_l.getId());
            pst.executeUpdate();
            System.out.println("Category modifiée avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Category_l> afficher() {
        List<Category_l> categorys = new ArrayList();
        String req = "SELECT * FROM `category_l`";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                categorys.add(new Category_l(rs.getInt("id"), rs.getString("nom")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categorys;
    }
    // Méthode pour ajouter la contrainte d'unicité à la table category_l
    private void addUniqueConstraintToCategoryTable() {
        String sql = "ALTER TABLE category_l ADD CONSTRAINT unique_category_name UNIQUE (nom)";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Contrainte d'unicité ajoutée avec succès sur la colonne nom de la table category_l");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}

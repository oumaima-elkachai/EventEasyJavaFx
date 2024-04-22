package services;

import models.Booking_l;
import models.Lieu;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService implements IBooking<Booking_l>{
    private Connection connection = DataSource.getInstance().getConnection();
    @Override
    public void ajouter(Booking_l resvationsr) {
        String req = "INSERT INTO `Booking_l`( `lieub_id`, `prix`,`date_d`, `date_f`) VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, resvationsr.getLieub_id().getId());
            pst.setFloat(2, resvationsr.getPrix());
            pst.setDate(3, resvationsr.getDate_d());
            pst.setDate(4, resvationsr.getDate_f());

            pst.executeUpdate();
            System.out.println("Service ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Booking_l resvationsr) {
        String req = "DELETE FROM `Booking_l` WHERE `Booking_l`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1,resvationsr.getId());
            pst.executeUpdate();
            System.out.println("Reservation supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Booking_l resvationsr) {
        String req = "UPDATE `Booking_l` SET `date_d` = ?, `date_f` = ?, `prix` = ? WHERE `resvationsr`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(1, resvationsr.getDate_f());
            pst.setDate(2, resvationsr.getDate_d());
            pst.setFloat(3, resvationsr.getPrix());
            pst.setInt(4, resvationsr.getId());
            pst.executeUpdate();
            System.out.println("Reservation modifié avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Booking_l> afficher() {
        List<Booking_l> reservationServices = new ArrayList<>();

        String req = "SELECT rs.*, l.nom AS lieu_nom " +
                "FROM booking_l rs " +
                "INNER JOIN lieu l ON rs.lieub_id = s.id"; // Requête SQL avec la jointure

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int lieub_id = rs.getInt("lieub_id");
                float prix = rs.getFloat("Prix");
                Date date_d = rs.getDate("date_d");
                Date date_f = rs.getDate("date_f");
                // Create a Booking_l object
                Booking_l booking = new Booking_l(id, date_d, date_f, prix, new Lieu(lieub_id)); // Assuming you have a constructor in Lieu class that accepts an id
                // Add the Booking_l object to the list
                reservationServices.add(booking);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reservationServices;
    }


}

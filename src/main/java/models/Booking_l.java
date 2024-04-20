package models;

import java.sql.Date;

public class Booking_l {
    private int id;
    private Date date_d, date_f;
    private Float prix;
    private Lieu lieub_id;

    public Booking_l(int id, Date date_d, Date date_f, Float prix, Lieu lieub_id) {
        this.id = id;
        this.date_d = date_d;
        this.date_f = date_f;
        this.prix = prix;
        this.lieub_id = lieub_id;
    }

    public Booking_l(Date date_d, Date date_f, Float prix, Lieu lieub_id) {
        this.date_d = date_d;
        this.date_f = date_f;
        this.prix = prix;
        this.lieub_id = lieub_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_d() {
        return date_d;
    }

    public void setDate_d(Date date_d) {
        this.date_d = date_d;
    }

    public Date getDate_f() {
        return date_f;
    }

    public void setDate_f(Date date_f) {
        this.date_f = date_f;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Lieu getLieub_id() {
        return lieub_id;
    }

    public void setLieub_id(Lieu lieub_id) {
        this.lieub_id = lieub_id;
    }

    @Override
    public String toString() {
        return "Booking_l{" +
                "id=" + id +
                ", date_d=" + date_d +
                ", date_f=" + date_f +
                ", prix=" + prix +
                ", lieub_id=" + lieub_id +
                '}';
    }
}

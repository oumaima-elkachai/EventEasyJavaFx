package models;

import java.util.Date;

public class Lieu {
    private int id;
    private Category_l category_id;
    private String nom;
    private double prix;
    private String image;
    private Date date_d;
    private Date date_f;
    private int capacity;
    private String region;
    private double latitude;
    private double longitude;



    public Lieu(int id,Category_l category_id, String nom, double prix, Date date_d, Date date_f, int capacity, String region, double latitude, double longitude, String image) {
        this.id = id;
        this.category_id = category_id;
        this.nom = nom;
        this.prix = prix;
        this.date_d = date_d;
        this.date_f = date_f;
        this.capacity = capacity;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public Lieu(Category_l category_id,String nom, double prix, String image, Date date_d, Date date_f, int capacity, String region, double latitude, double longitude) {
        this.category_id = category_id;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.date_d = date_d;
        this.date_f = date_f;
        this.capacity = capacity;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Lieu(int lieubId) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {

        if (nom != null && !nom.isEmpty()) {
            this.nom = nom;
        } else {
            // Gérer le cas où le nom est vide ou nul
            System.out.println("Le nom du service ne peut pas être vide.");
        }
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        if (prix >= 0) {
            this.prix = prix;
        } else {
            // Gérer le cas où le prix est négatif
            System.out.println("Le prix ne peut pas être négatif.");
        }
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            // Gérer le cas où la capacité est nulle ou négative
            System.out.println("La capacité doit être supérieure à zéro.");
        }
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (latitude >= -90 && latitude <= 90) {
            this.latitude = latitude;
        } else {
            // Gérer le cas où la latitude est en dehors de la plage valide
            System.out.println("La latitude doit être comprise entre -90 et 90 degrés.");
        }
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude >= -180 && longitude <= 180) {
            this.longitude = longitude;
        } else {
            // Gérer le cas où la longitude est en dehors de la plage valide
            System.out.println("La longitude doit être comprise entre -180 et 180 degrés.");
        }
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate_d() {
        return (java.sql.Date) date_d;
    }

    public void setDate_d(Date date_d) {
        this.date_d = date_d;
    }

    public String getDate_f() {
        return (java.sql.Date) date_f;
    }

    public void setDate_f(Date date_f) {
        this.date_f = date_f;
    }

    public Category_l getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Category_l category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "id=" + id +
                ", Category_nom='" + category_id.getNom() + '\'' +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", date_d=" + date_d +
                ", date_f=" + date_f +
                ", capacity=" + capacity +
                ", region='" + region + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", image=" + image +
                "}\n";
    }
}

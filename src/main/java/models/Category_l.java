package models;

public class Category_l {

    private int id;
    private  String nom;



    public Category_l(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Category_l(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return "category_l{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}

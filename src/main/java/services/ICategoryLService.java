package services;

import java.util.List;

public interface ICategoryLService <T>{
    void ajouter(T var1);

    void supprimer(T var1);

    void modifier(T var1);

    List<T> afficher();
}

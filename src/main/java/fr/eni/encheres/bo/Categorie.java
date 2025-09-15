package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
private int id;
private String libelle;

public Categorie() {

}

public Categorie(int id, String libelle) {
    this.id = id;
    this.libelle = libelle;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id && Objects.equals(libelle, categorie.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}


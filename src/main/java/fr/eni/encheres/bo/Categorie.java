package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long no_categorie;
    private String libelle;


    public Categorie() {

    }

    public Categorie(Long no_categorie, String libelle) {
        this.no_categorie = no_categorie;
        this.libelle = libelle;
    }


    public Long getNo_categorie() {
        return no_categorie;
    }

    public void setNo_categorie(Long no_categorie) {
        this.no_categorie = no_categorie;
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
        return no_categorie == categorie.no_categorie && Objects.equals(libelle, categorie.libelle);
    }


    @Override
    public int hashCode() {
        return Objects.hash(no_categorie, libelle);
    }


    @Override
    public String toString() {
        return "Categorie{" +
                "no_categorie=" + no_categorie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}


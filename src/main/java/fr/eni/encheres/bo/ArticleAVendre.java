package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ArticleAVendre implements Serializable {

    private static final long serialVersionUID = 1L;
private int id;
private String nom;
private String description;
private LocalDate dateDebutEncheres;
private LocalDate dateFinEncheres;
private int statut;
private int prixInitial;
private int prixVente;


public ArticleAVendre() {

}
public ArticleAVendre(int id,String nom,String description,LocalDate dateDebutEncheres, LocalDate dateFinEncheres,int statut,int prixInitial,int prixVente) {
  this.id = id;
  this.nom = nom;
  this.description = description;
  this.dateDebutEncheres = dateDebutEncheres;
  this.dateFinEncheres = dateFinEncheres;
  this.statut=statut;
  this.prixInitial=prixInitial;
  this.prixVente=prixVente;
}

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        this.nom = nom;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArticleAVendre that = (ArticleAVendre) o;
        return id == that.id && statut == that.statut && prixInitial == that.prixInitial && prixVente == that.prixVente && Objects.equals(nom, that.nom) && Objects.equals(description, that.description) && Objects.equals(dateDebutEncheres, that.dateDebutEncheres) && Objects.equals(dateFinEncheres, that.dateFinEncheres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, dateDebutEncheres, dateFinEncheres, statut, prixInitial, prixVente);
    }

    @Override
    public String toString() {
        return "ArticleAVendre{" +
                "dateDebutEncheres=" + dateDebutEncheres +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", dateFinEncheres=" + dateFinEncheres +
                ", statut=" + statut +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                '}';
    }
}

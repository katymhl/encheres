package fr.eni.encheres.bo;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class ArticleAVendre implements Serializable {

    private static final long serialVersionUID = 1L;
private int no_article;
private String nom_article;
private String description;
private int photo;
private Date date_debut_encheres;
private Date date_fin_encheres;
private int statut_enchere;
private int prix_initial;
private int prix_vente;
private String id_utilisateur;
private int  no_categorie;
private int no_adresse_retrait;

public ArticleAVendre() {

}
public ArticleAVendre(int no_article,String nom_article,String description,int photo,Date date_debut_encheres, Date date_fin_encheres,int statut_enchere,
                      int prix_initial, int prix_vente,String id_utilisateur,int no_categorie,int no_adresse_retrait) {
    this.no_article=no_article;
    this.nom_article=nom_article;
    this.description=description;
    this.photo=photo;
    this.date_debut_encheres = date_debut_encheres;
    this.date_fin_encheres = date_fin_encheres;
    this.statut_enchere = statut_enchere;
    this.prix_initial = prix_initial;
    this.prix_vente = prix_vente;
    this.id_utilisateur=id_utilisateur;
    this.no_categorie=no_categorie;
    this.no_adresse_retrait=no_adresse_retrait;

}

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public Date getDate_debut_encheres() {
        return date_debut_encheres;
    }

    public void setDate_debut_encheres(Date date_debut_encheres) {
        this.date_debut_encheres = date_debut_encheres;
    }

    public Date getDate_fin_encheres() {
        return date_fin_encheres;
    }

    public void setDate_fin_encheres(Date date_fin_encheres) {
        this.date_fin_encheres = date_fin_encheres;
    }

    public int getStatut_enchere() {
        return statut_enchere;
    }

    public void setStatut_enchere(int statut_enchere) {
        this.statut_enchere = statut_enchere;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(int prix_vente) {
        this.prix_vente = prix_vente;
    }

    public String getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(String id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getNo_categorie() {
        return no_categorie;
    }

    public void setNo_categorie(int no_categorie) {
        this.no_categorie = no_categorie;
    }

    public int getNo_adresse_retrait() {
        return no_adresse_retrait;
    }

    public void setNo_adresse_retrait(int no_adresse_retrait) {
        this.no_adresse_retrait = no_adresse_retrait;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArticleAVendre that = (ArticleAVendre) o;
        return no_article == that.no_article && photo == that.photo && statut_enchere == that.statut_enchere && prix_initial == that.prix_initial && prix_vente == that.prix_vente && no_categorie == that.no_categorie && no_adresse_retrait == that.no_adresse_retrait && Objects.equals(nom_article, that.nom_article) && Objects.equals(description, that.description) && Objects.equals(date_debut_encheres, that.date_debut_encheres) && Objects.equals(date_fin_encheres, that.date_fin_encheres) && Objects.equals(id_utilisateur, that.id_utilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no_article, nom_article, description, photo, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait);
    }


    @Override
    public String toString() {
        return "ArticleAVendre{" +
                "no_article=" + no_article +
                ", nom_article='" + nom_article + '\'' +
                ", description='" + description + '\'' +
                ", photo=" + photo +
                ", date_debut_encheres=" + date_debut_encheres +
                ", date_fin_encheres=" + date_fin_encheres +
                ", statut_enchere=" + statut_enchere +
                ", prix_initial=" + prix_initial +
                ", prix_vente=" + prix_vente +
                ", id_utilisateur='" + id_utilisateur + '\'' +
                ", no_categorie=" + no_categorie +
                ", no_adresse_retrait=" + no_adresse_retrait +
                '}';
    }
}

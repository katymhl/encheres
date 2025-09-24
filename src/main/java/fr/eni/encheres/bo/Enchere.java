package fr.eni.encheres.bo;

import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Enchere implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id_utilisateur;
    private int no_article;
    private Date date_enchere;
    private int montant_enchere;


    public Enchere() {

    }

    public Enchere(String id_utilisateur ,int no_article, Date date_enchere, int montant_enchere) {
            this.id_utilisateur = id_utilisateur;
            this.no_article = no_article;
            this.date_enchere = date_enchere;
            this.montant_enchere = montant_enchere;

    }

    public String getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(String id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public Date getDate_enchere() {
        return date_enchere;
    }

    public void setDate_enchere(Date date_enchere) {
        this.date_enchere = date_enchere;
    }

    public int getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(int montant_enchere) {
        this.montant_enchere = montant_enchere;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return no_article == enchere.no_article && montant_enchere == enchere.montant_enchere && Objects.equals(id_utilisateur, enchere.id_utilisateur) && Objects.equals(date_enchere, enchere.date_enchere);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id_utilisateur, no_article, date_enchere, montant_enchere);
    }


    @Override
    public String toString() {
        return "Enchere{" +
                "id_utilisateur='" + id_utilisateur + '\'' +
                ", no_article=" + no_article +
                ", date_enchere=" + date_enchere +
                ", montant_enchere=" + montant_enchere +
                '}';
    }
}

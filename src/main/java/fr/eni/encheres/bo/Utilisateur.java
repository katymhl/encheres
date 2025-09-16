package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Utilisateur implements Serializable {
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private int no_adresse;
    private String telephone;
    private String mot_de_passe ;
    private int credit;
    private boolean administrateur;
    private static final long serialVersionUID = 1L;

    public Utilisateur() {
    }

    public Utilisateur(String pseudo,String nom,String prenom,String email,int no_adresse,String telephone,String mot_de_passe,int credit,boolean administrateur) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.no_adresse = no_adresse;
        this.telephone = telephone;
        this.mot_de_passe = mot_de_passe;
        this.credit = credit;
        this.administrateur = administrateur;

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }



    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;

    }
    public String getEmail() {
        return email;

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdresse() {
        return no_adresse;
    }

    public void setAdresse(int no_adresse) {

        this.no_adresse = no_adresse;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return mot_de_passe;
    }
    public void setMotDePasse(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return administrateur;
    }
    public void setAdmin(boolean administrateur) {
        this.administrateur = administrateur;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", no_adresse=" + no_adresse +
                ", telephone='" + telephone + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", credit=" + credit +
                ", administrateur=" + administrateur +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return credit == that.credit && administrateur == that.administrateur && Objects.equals(pseudo, that.pseudo) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email) && Objects.equals(no_adresse, that.no_adresse) && Objects.equals(telephone, that.telephone) && Objects.equals(mot_de_passe, that.mot_de_passe);
    }
}

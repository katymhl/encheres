package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Utilisateur implements Serializable {
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private Adresse adresse;
    private String telephone;
    private String motDePasse;
    private int credit;
    private boolean admin;
    private static final long serialVersionUID = 1L;

    public Utilisateur() {
    }

    public Utilisateur(String pseudo,String nom,String prenom,String email,Adresse adresse,String telephone,String motDePasse,int credit,boolean admin) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.admin = admin;

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
    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {

        this.adresse = adresse;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adresse=" + adresse +
                ", telephone='" + telephone + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
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
        return credit == that.credit && admin == that.admin && Objects.equals(pseudo, that.pseudo) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email) && Objects.equals(adresse, that.adresse) && Objects.equals(telephone, that.telephone) && Objects.equals(motDePasse, that.motDePasse);
    }
}

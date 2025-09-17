package fr.eni.encheres.bo;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

public class Utilisateur implements Serializable {

    @NotBlank(message = "Le pseudo est obligatoire")
    @Size(min = 3, max = 30, message = "Le pseudo doit contenir entre 3 et 30 caractères")
    private String pseudo;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;


    //@Min(value = 1, message = "L'adresse doit être renseignée")
    private int no_adresse;

    @Pattern(regexp = "^\\d{10}$", message = "Le numéro doit contenir exactement 10 chiffres")
    private String telephone;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, max = 20, message = "Le mot de passe doit contenir entre 8 et 20 caractères")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,20}$",
            message = "Doit contenir 1 majuscule, 1 chiffre et 1 caractère spécial")
    private String mot_de_passe;

    // Le crédit peut être initialisé à 0
    @Min(value = 0, message = "Le crédit ne peut pas être négatif")
    private int credit;

    private boolean administrateur;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private transient String confirmPassword;
    private static final long serialVersionUID = 1L;

    public Utilisateur() {
        this.credit = 10;
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

    public int getNo_adresse() {
        return no_adresse;
    }

    public void setNo_adresse(int no_adresse) {
        this.no_adresse = no_adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return no_adresse == that.no_adresse && credit == that.credit && administrateur == that.administrateur && Objects.equals(pseudo, that.pseudo) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email) && Objects.equals(telephone, that.telephone) && Objects.equals(mot_de_passe, that.mot_de_passe) && Objects.equals(confirmPassword, that.confirmPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, nom, prenom, email, no_adresse, telephone, mot_de_passe, credit, administrateur, confirmPassword);
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
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

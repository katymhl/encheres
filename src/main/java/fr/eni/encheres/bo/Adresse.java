package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

public class Adresse implements Serializable {

private int   no_adresse;
    @NotBlank(message = "La rue est obligatoire")
    private String rue;

    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^\\d{5}$", message = "Le code postal doit contenir exactement 5 chiffres")
    private String code_postal;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;
private Byte adresse_eni;

    private static final long serialVersionUID = 1L;

    public Adresse() {
        this.adresse_eni =0;
    }
    public Adresse(int no_adresse,String rue ,String code_postal,String ville,byte adresse_eni) {
        this.no_adresse = no_adresse;
        this.rue =rue ;
        this.code_postal = code_postal;
        this.ville = ville;
        this.adresse_eni = adresse_eni;
    }





    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }


    public int getNo_adresse() {
        return no_adresse;
    }

    public void setNo_adresse(int no_adresse) {
        this.no_adresse = no_adresse;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public Byte getAdresse_eni() {
        return adresse_eni;
    }

    public void setAdresse_eni(Byte adresse_eni) {
        this.adresse_eni = adresse_eni;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return no_adresse == adresse.no_adresse && Objects.equals(rue, adresse.rue) && Objects.equals(code_postal, adresse.code_postal) && Objects.equals(ville, adresse.ville) && Objects.equals(adresse_eni, adresse.adresse_eni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no_adresse, rue, code_postal, ville, adresse_eni);
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "no_adresse=" + no_adresse +
                ", rue='" + rue + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", ville='" + ville + '\'' +
                ", adresse_eni=" + adresse_eni +
                '}';
    }
}

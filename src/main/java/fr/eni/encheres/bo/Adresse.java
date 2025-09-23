package fr.eni.encheres.bo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

public class Adresse implements Serializable {

    @Min(value = 1, message = "{Min.adresse.no_adresse}")
    private Integer no_adresse;

    @NotBlank(message = "{NotBlank.adresse.rue}")
    private String rue;

    @NotBlank(message = "{NotBlank.adresse.code_postal}")
    @Pattern(regexp = "^\\d{5}$", message = "{Pattern.adresse.code_postal}")
    private String code_postal;

    @NotBlank(message = "{NotBlank.adresse.ville}")
    private String ville;

private Byte adresse_eni;

    private static final long serialVersionUID = 1L;

    public Adresse() {
        this.adresse_eni =0;
    }
    public Adresse(Integer no_adresse,String rue ,String code_postal,String ville,byte adresse_eni) {
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


    public Integer getNo_adresse() {
        return no_adresse;
    }

    public void setNo_adresse(Integer no_adresse) {
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

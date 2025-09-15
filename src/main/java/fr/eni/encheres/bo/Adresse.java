package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Adresse implements Serializable {

private long   id;
private String rue;
private String CodePostal;
private String ville ;

    private static final long serialVersionUID = 1L;

    public Adresse() {

    }
    public Adresse(long id,String rue ,String codePostal,String ville) {
        this.id = id;
        this.rue =rue ;
        this.CodePostal = codePostal;
        this.ville = ville;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodePostal() {
        return CodePostal;
    }

    public void setCodePostal(String codePostal) {
        CodePostal = codePostal;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return id == adresse.id && Objects.equals(rue, adresse.rue) && Objects.equals(CodePostal, adresse.CodePostal) && Objects.equals(ville, adresse.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rue, CodePostal, ville);
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "CodePostal='" + CodePostal + '\'' +
                ", id=" + id +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }


}

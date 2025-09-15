package fr.eni.encheres.bo;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Enchere implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date date;
    private int montant;

    public Enchere() {

    }

public Enchere(Date date, int montant) {
    this.date = date;
    this.montant = montant;
}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return montant == enchere.montant && Objects.equals(date, enchere.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, montant);
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "date=" + date +
                ", montant=" + montant +
                '}';
    }
}

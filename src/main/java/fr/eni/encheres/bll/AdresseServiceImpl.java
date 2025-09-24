package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.AdresseDAOImpl;
import fr.eni.encheres.dal.UtilisateurDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseServiceImpl implements AdresseService {

    private AdresseDAO adresseDAO;
    private UtilisateurDAO utilisateurDAO;

    public AdresseServiceImpl(AdresseDAO adresseDAO, UtilisateurDAO utilisateurDAO) {
        this.adresseDAO = adresseDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public List<Adresse> findByall() {
        return adresseDAO.findAll();
    }

    @Override
    public Adresse findById(int id_adresse) {
        return adresseDAO.read(id_adresse);
    }

    @Override
    public Adresse getAdresseByPseudo(String pseudo) {
        return adresseDAO.findByUtilisateurPseudo(pseudo);
    }

    @Override
    public int getOrCreateAdresse(String rue, String codePostal, String ville) {
        int no_adress=0;
        String rueNorm = rue.trim().toLowerCase();
        String codePostalNorm = codePostal.trim();
        String villeNorm = ville.trim().toLowerCase();

        // Recherche dans la base
        List<Adresse> toutes = adresseDAO.findAll();
        for (Adresse a : toutes) {
            if (a.getRue().trim().toLowerCase().equals(rueNorm) &&
                    a.getCode_postal().trim().equals(codePostalNorm) &&
                    a.getVille().trim().toLowerCase().equals(villeNorm)) {
                return a.getNo_adresse(); // Adresse déjà existante
            }
        }

        // Sinon, on insère
        Adresse nouvelle = new Adresse();
        nouvelle.setRue(rue);
        nouvelle.setCode_postal(codePostal);
        nouvelle.setVille(ville);
        nouvelle.setAdresse_eni((byte) 0); // ou autre valeur par défaut

        adresseDAO.create(nouvelle);

        List<Adresse> toutapresajout = adresseDAO.findAll();
        for (Adresse a : toutapresajout) {
            if (a.getRue().trim().toLowerCase().equals(rueNorm) &&
                    a.getCode_postal().trim().equals(codePostalNorm) &&
                    a.getVille().trim().toLowerCase().equals(villeNorm)) {
                no_adress= a.getNo_adresse();
            }
        }
        return no_adress; // ID généré après insertion
    }
}

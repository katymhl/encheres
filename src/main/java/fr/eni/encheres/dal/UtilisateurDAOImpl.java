package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public  class UtilisateurDAOImpl implements UtilisateurDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Utilisateur read(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM ENCHERES_DB WHERE pseudo = :pseudo", namedParameters,
                new BeanPropertyRowMapper<>(Utilisateur.class));

    }

    @Override
    public List<Utilisateur> findAll() {
        return List.of();
    }

    @Override
    public List<Utilisateur> findByemail(String emailUtilisateur) {
        return List.of();
    }

    @Override
    public void insertUtilisateur(String pseudo, String nomUtilisateur, String prenomUtilisateur, String email, Adresse adresse, String telephone, String passwordUtilisateur, int credit, boolean admin) {

    }

    @Override
    public boolean validateListOfUtilisateurds(List<Utilisateur> lstUlisateur) {
        return false;
    }
}

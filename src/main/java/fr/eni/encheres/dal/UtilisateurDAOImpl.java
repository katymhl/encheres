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
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM UTILISATEURS  WHERE pseudo = :pseudo", namedParameters,
                new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public void create(Utilisateur utilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("pseudo", utilisateur.getPseudo());
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
        namedParameters.addValue("no_adresse", utilisateur.getAdresse());

        namedParameterJdbcTemplate.update("INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, mot_de_passe,no_adresse) ", namedParameters);
    }


//    @Override
//    public List<Utilisateur> findAll() {
//        return namedParameterJdbcTemplate.query("SELECT *  FROM UTILISATEURS", new BeanPropertyRowMapper<>(Utilisateur.class));
//
//    }

    @Override
    public Utilisateur findByemail(String emailUtilisateur) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", emailUtilisateur);
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM UTILISATEURS  WHERE email = :emailUtilisateur", namedParameters,
                new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public void update(Utilisateur utilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", utilisateur.getPseudo());
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
        namedParameters.addValue("no_adresse", utilisateur.getAdresse());

        namedParameterJdbcTemplate.update("UPDATE Utilisateur SET nom = :nom, prenom = :prenom, email= :email, mot_de_passe= :mot_de_passe,no_adresse= :no_adresse WHERE pseudo = :pseudo", namedParameters);
    }


    @Override
    public void delete(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        namedParameterJdbcTemplate.update(
                "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo", namedParameters);
    }

}

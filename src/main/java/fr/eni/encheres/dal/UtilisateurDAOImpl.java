package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public  class UtilisateurDAOImpl implements UtilisateurDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Override
    public Utilisateur read(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        List<Utilisateur> result = namedParameterJdbcTemplate.query(
                "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo",
                namedParameters,
                new BeanPropertyRowMapper<>(Utilisateur.class)
        );

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Optional<Utilisateur> readPseudo(String pseudoUtilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudoUtilisateur);
        Utilisateur utilisateur = null;
        try{
            utilisateur = namedParameterJdbcTemplate.queryForObject("SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo", namedParameters,
                    new BeanPropertyRowMapper<>(Utilisateur.class));
            System.out.println("utilisateur = " + utilisateur);
        } catch (EmptyResultDataAccessException e){
            System.out.println("Empty user");
        }
        return Optional.ofNullable(utilisateur);
    }


    @Override
    public void create(Utilisateur utilisateur) {

//        String motDePasseEncode = passwordEncoder.encode(utilisateur.getMot_de_passe());
//        utilisateur.setMot_de_passe(motDePasseEncode);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("pseudo", utilisateur.getPseudo());
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("telephone", utilisateur.getTelephone());
        namedParameters.addValue("mot_de_passe", utilisateur.getMot_de_passe());
        namedParameters.addValue("no_adresse", utilisateur.getNo_adresse());

        namedParameterJdbcTemplate.update(
                "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email,telephone, mot_de_passe, no_adresse) " +
                        "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :no_adresse)",
                namedParameters
        );
    }



//    @Override
//    public List<Utilisateur> findAll() {
//        return namedParameterJdbcTemplate.query("SELECT *  FROM UTILISATEURS", new BeanPropertyRowMapper<>(Utilisateur.class));
//
//    }

    @Override
    public Optional<Utilisateur> findByEmail(String emailUtilisateur) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", emailUtilisateur);
        Utilisateur utilisateur = null;
        try{
            utilisateur = namedParameterJdbcTemplate.queryForObject("SELECT * FROM UTILISATEURS WHERE email = :email", namedParameters,
                    new BeanPropertyRowMapper<>(Utilisateur.class));
            System.out.println("utilisateur = " + utilisateur);
        } catch (EmptyResultDataAccessException e){
            System.out.println("Empty user");
        }
        return Optional.ofNullable(utilisateur);
    }



    @Override
    public Utilisateur findByemail(String emailUtilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", emailUtilisateur);

        List<Utilisateur> utilisateurs = namedParameterJdbcTemplate.query(
                "SELECT * FROM UTILISATEURS WHERE email = :email",
                namedParameters,
                new BeanPropertyRowMapper<>(Utilisateur.class)
        );

        // S’il n’y a pas de résultat → retourne null
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }

    @Override
    public void update(Utilisateur utilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", utilisateur.getPseudo());
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("credit", utilisateur.getCredit());
        namedParameters.addValue("mot_de_passe", utilisateur.getMot_de_passe());
        namedParameters.addValue("no_adresse", utilisateur.getNo_adresse());

        namedParameterJdbcTemplate.update("UPDATE UTILISATEURS SET nom = :nom, prenom = :prenom, email= :email, mot_de_passe= :mot_de_passe,credit= :credit,no_adresse= :no_adresse WHERE pseudo = :pseudo", namedParameters);
    }

    @Override
    public void updatePWD(String pseudo, String mot_de_passe) {
        String sql = "UPDATE UTILISATEURS SET mot_de_passe = :mot_de_passe WHERE pseudo = :pseudo";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mot_de_passe", mot_de_passe);
        params.addValue("pseudo", pseudo);

        namedParameterJdbcTemplate.update(sql, params);

    }


    @Override
    public void delete(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        namedParameterJdbcTemplate.update(
                "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo", namedParameters);
    }

}

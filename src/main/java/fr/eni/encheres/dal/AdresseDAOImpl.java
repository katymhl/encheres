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
public class AdresseDAOImpl implements AdresseDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;





    @Override
    public Adresse read(int no_adresse) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_adresse", no_adresse);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM ADRESSES  WHERE no_adresse = :no_adresse", namedParameters,
                new BeanPropertyRowMapper<>(Adresse.class));
    }

    @Override
    public List<Adresse> findAll() {
        return namedParameterJdbcTemplate.query("SELECT *  FROM ADRESSES", new BeanPropertyRowMapper<>(Adresse.class));
    }

    @Override
    public Adresse findByUtilisateurPseudo(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        return namedParameterJdbcTemplate.queryForObject(
                "SELECT a.* FROM ADRESSES a JOIN UTILISATEURS u ON u.no_adresse = a.no_adresse WHERE u.pseudo = :pseudo;",
                namedParameters,
                new BeanPropertyRowMapper<>(Adresse.class));

    }

    public int getOrCreateAdresse(String rue, String code_postal, String ville) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rue", rue);
        params.addValue("cpo", code_postal);
        params.addValue("ville", ville);

        // Vérifie si l'adresse existe déjà
        List<Integer> ids = namedParameterJdbcTemplate.queryForList(
                "SELECT no_adresse FROM ADRESSES WHERE rue = :rue AND code_postal = :code_postal AND ville = :ville",
                params,
                Integer.class
        );

        if (!ids.isEmpty()) {
            return ids.get(0);
        }

        // Sinon, on l'insère
        namedParameterJdbcTemplate.update(
                "INSERT INTO ADRESSES (rue, code_postal, ville) VALUES (:rue, :code_postal, :ville)",
                params
        );

        // Récupère l'id de la dernière insertion
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                new MapSqlParameterSource(),
                Integer.class
        );
    }


    @Override
    public void create(Adresse adresse) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("rue", adresse.getRue());
        namedParameters.addValue("code_postal", adresse.getCode_postal());
        namedParameters.addValue("ville", adresse.getVille());
        namedParameters.addValue("adresse_eni", adresse.getAdresse_eni());

        String sql = "INSERT INTO ADRESSES (rue, code_postal, ville, adresse_eni) " +
                "VALUES (:rue, :code_postal, :ville, :adresse_eni)";

        namedParameterJdbcTemplate.update(sql, namedParameters);
    }


    @Override
    public void update(Adresse adresse) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("no_adresse", adresse.getNo_adresse());
        namedParameters.addValue("rue", adresse.getRue());
        namedParameters.addValue("code_postal", adresse.getCode_postal());
        namedParameters.addValue("ville", adresse.getVille());
        namedParameters.addValue("adresse_eni", adresse.getAdresse_eni());


        namedParameterJdbcTemplate.update("UPDATE ADRESSES SET rue = :rue, code_postal = :code_postal, ville= :ville, adresse_eni= :adresse_eni", namedParameters);
    }

    @Override
    public void delete(int no_adresse) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_adresse", no_adresse);

        namedParameterJdbcTemplate.update(
                "DELETE FROM ADRESSES WHERE no_adresse = :no_adresse", namedParameters);

    }
}

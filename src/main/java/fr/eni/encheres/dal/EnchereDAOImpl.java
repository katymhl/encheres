package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Enchere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EnchereDAOImpl implements EnchereDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Enchere read(String id_utilisateur, int no_article, int montant_enchere) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        namedParameters.addValue("no_article", no_article);
        namedParameters.addValue("montant_enchere", montant_enchere);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM ENCHERES  WHERE id_utilisateur = :id_utilisateur " +
                        "AND no_article=:no_article " +
                        "AND montant_enchere=:montant_enchere ", namedParameters,
                new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> findAll() {
        return namedParameterJdbcTemplate.query("SELECT *  FROM ENCHERES", new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public void create(Enchere enchere) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", enchere.getId_utilisateur());
        namedParameters.addValue("no_article", enchere.getNo_article());
        namedParameters.addValue("montant_enchere", enchere.getMontant_enchere());
        namedParameters.addValue("date_enchere", enchere.getDate_enchere());

        namedParameterJdbcTemplate.update("INSERT INTO ENCHERES (id_utilisateur,no_article,montant_enchere, date_enchere)", namedParameters);
    }

    @Override
    public void update(Enchere enchere) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", enchere.getId_utilisateur());
        namedParameters.addValue("no_article", enchere.getNo_article());
        namedParameters.addValue("montant_enchere", enchere.getMontant_enchere());
        namedParameters.addValue("date_enchere", enchere.getDate_enchere());

        namedParameterJdbcTemplate.update("UPDATE ENCHERES SET id_utilisateur = :id_utilisateur, no_article = :no_article, montant_enchere= :montant_enchere, date_enchere= :date_enchere" +
                        " WHERE id_utilisateur = :id_utilisateur AND no_article=:no_article AND montant_enchere=:montant_enchere", namedParameters);
    }

    @Override
    public void delete(String id_utilisateur, int no_article, int montant_enchere) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        namedParameters.addValue("no_article", no_article);
        namedParameters.addValue("montant_enchere", montant_enchere);

        namedParameterJdbcTemplate.update(
                "DELETE FROM ENCHERES WHERE id_utilisateur = :id_utilisateur AND no_article=:no_article AND montant_enchere=:montant_enchere", namedParameters);
    }
}

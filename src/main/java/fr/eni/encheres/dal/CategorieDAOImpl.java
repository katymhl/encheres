package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Categorie read(Long no_categorie) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", no_categorie);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM CATEGORIES  WHERE no_categorie = :no_categorie", namedParameters,
                new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public List<Categorie> findAll() {
        return namedParameterJdbcTemplate.query("SELECT *  FROM CATEGORIES", new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public void create(Categorie categorie) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", categorie.getNo_categorie());
        namedParameters.addValue("libelle", categorie.getLibelle());

        namedParameterJdbcTemplate.update("INSERT INTO CATEGORIES (no_categorie,libelle)", namedParameters);
    }

    @Override
    public void update(Categorie categorie) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("no_categorie", categorie.getNo_categorie());
        namedParameters.addValue("libelle", categorie.getLibelle());

        namedParameterJdbcTemplate.update("UPDATE CATEGORIES SET no_categorie = :no_categorie, libelle = :libelle", namedParameters);

    }

    @Override
    public void delete(int no_categorie) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", no_categorie);

        namedParameterJdbcTemplate.update(
                "DELETE FROM CATEGORIES WHERE no_categorie = :no_categorie", namedParameters);


    }
}

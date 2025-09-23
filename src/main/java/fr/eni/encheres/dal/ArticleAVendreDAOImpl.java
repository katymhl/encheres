package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public ArticleAVendre read(int no_article) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_article", no_article);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM ARTICLES_A_VENDRE  WHERE no_article = :no_article", namedParameters,
                new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public List<ArticleAVendre> findAll() {
        return namedParameterJdbcTemplate.query("SELECT *  FROM ARTICLES_A_VENDRE", new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public void create(ArticleAVendre articleAVendre) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        //namedParameters.addValue("no_article", articleAVendre.getNo_article());
        namedParameters.addValue("nom_article", articleAVendre.getNom_article());
        namedParameters.addValue("description", articleAVendre.getDescription());
        namedParameters.addValue("photo", articleAVendre.getPhoto());
        namedParameters.addValue("date_debut_encheres", articleAVendre.getDate_debut_encheres());
        namedParameters.addValue("date_fin_encheres", articleAVendre.getDate_fin_encheres());
        namedParameters.addValue("statut_enchere", articleAVendre.getStatut_enchere());
        namedParameters.addValue("prix_initial", articleAVendre.getPrix_initial());
        namedParameters.addValue("prix_vente", articleAVendre.getPrix_vente());
        namedParameters.addValue("id_utilisateur", articleAVendre.getId_utilisateur());
        namedParameters.addValue("no_categorie", articleAVendre.getNo_categorie());
        namedParameters.addValue("no_adresse_retrait", articleAVendre.getNo_adresse_retrait());
        namedParameterJdbcTemplate.update("INSERT INTO ARTICLES_A_VENDRE (nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente,  id_utilisateur , no_categorie , no_adresse_retrait) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :statut_enchere, :prix_initial, :prix_vente, :id_utilisateur, :no_categorie, :no_adresse_retrait)", namedParameters);

    }

    @Override
    public void update(ArticleAVendre articleAVendre) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_article", articleAVendre.getNo_article());
        namedParameters.addValue("nom_article", articleAVendre.getNom_article());
        namedParameters.addValue("description", articleAVendre.getDescription());
        namedParameters.addValue("photo", articleAVendre.getPhoto());
        namedParameters.addValue("date_debut_encheres", articleAVendre.getDate_debut_encheres());
        namedParameters.addValue("date_fin_encheres", articleAVendre.getDate_fin_encheres());
        namedParameters.addValue("statut_enchere", articleAVendre.getStatut_enchere());
        namedParameters.addValue("prix_initial", articleAVendre.getPrix_initial());
        namedParameters.addValue("prix_vente", articleAVendre.getPrix_vente());
        namedParameters.addValue("id_utilisateur", articleAVendre.getId_utilisateur());
        namedParameters.addValue("no_categorie", articleAVendre.getNo_categorie());
        namedParameters.addValue("no_adresse_retrait", articleAVendre.getNo_adresse_retrait());

        namedParameterJdbcTemplate.update("UPDATE ARTICLES_A_VENDRE SET nom_article = :nom_article, description = :description, " +
                "photo = :photo, date_debut_encheres = :date_debut_encheres, date_fin_encheres = :date_fin_encheres, statut_enchere = :statut_enchere, " +
                "prix_initial = :prix_initial,prix_vente = :prix_vente,id_utilisateur = :id_utilisateur,no_categorie = :no_categorie, no_adresse_retrait = :no_adresse_retrait " +
                "WHERE no_article = :no_article", namedParameters);

    }

    @Override
    public void delete(int no_article) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_article", no_article);

        namedParameterJdbcTemplate.update(
                "DELETE FROM ARTICLES_A_VENDRE WHERE no_article = :no_article", namedParameters);


    }

    public void cloturerEncheresExpirees() {
        String sql = "UPDATE ARTICLES_A_VENDRE SET statut_enchere = 2 " +
                "WHERE date_fin_encheres < GETDATE() AND statut_enchere = 1";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
    }

    public void updateEtat(int no_article, int statut_enchere) {
        String sql = "UPDATE ARTICLES_A_VENDRE SET statut_enchere = :statut_enchere WHERE no_article = :no_article";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("statut_enchere", statut_enchere);
        params.addValue("no_article", no_article);

        namedParameterJdbcTemplate.update(sql, params);
    }


    @Override
    public List<ArticleAVendre> findActiveEnchere() {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE statut_enchere = :statut";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("statut", 1);

        return namedParameterJdbcTemplate.query(
                sql,
                namedParameters,
                new BeanPropertyRowMapper<>(ArticleAVendre.class)
        );
    }
    @Override
    public List<ArticleAVendre> filtrerArticles(String search, Integer categorie) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ARTICLES_A_VENDRE WHERE statut_enchere = 1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (categorie != null) {
            sql.append(" AND no_categorie = :categorie");
            params.addValue("categorie", categorie);
        }

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND nom_article LIKE :search");
            params.addValue("search", "%" + search.trim() + "%");
        }

        return namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                new BeanPropertyRowMapper<>(ArticleAVendre.class)
        );
    }


    @Override
    public List<ArticleAVendre> findMesVentesEnCours(String pseudo, String search, Integer categorie) {
        StringBuilder sql = new StringBuilder("""
        SELECT *
        FROM ARTICLES_A_VENDRE
        WHERE statut_enchere = 1
          AND id_utilisateur = :pseudo
    """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);

        if (categorie != null) {
            sql.append(" AND no_categorie = :categorie");
            params.addValue("categorie", categorie);
        }

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND nom_article LIKE :search");
            params.addValue("search", "%" + search.trim() + "%");
        }

        return namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                new BeanPropertyRowMapper<>(ArticleAVendre.class)
        );
    }


    @Override
    public List<ArticleAVendre> findMesVentesNonDebutees(String pseudo, String search, Integer categorie) {
        StringBuilder sql = new StringBuilder("""
        SELECT *
        FROM ARTICLES_A_VENDRE
        WHERE statut_enchere = 0
          AND id_utilisateur = :pseudo
    """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);

        if (categorie != null) {
            sql.append(" AND no_categorie = :categorie");
            params.addValue("categorie", categorie);
        }

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND nom_article LIKE :search");
            params.addValue("search", "%" + search.trim() + "%");
        }

        return namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                new BeanPropertyRowMapper<>(ArticleAVendre.class)
        );
    }

    @Override
    public List<ArticleAVendre> findMesVentesTerminees(String pseudo, String search, Integer categorie) {
        StringBuilder sql = new StringBuilder("""
        SELECT *
        FROM ARTICLES_A_VENDRE
        WHERE statut_enchere IN (2, 3, 100)
          AND id_utilisateur = :pseudo
    """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);

        if (categorie != null) {
            sql.append(" AND no_categorie = :categorie");
            params.addValue("categorie", categorie);
        }

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND nom_article LIKE :search");
            params.addValue("search", "%" + search.trim() + "%");
        }

        return namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                new BeanPropertyRowMapper<>(ArticleAVendre.class)
        );
    }


}

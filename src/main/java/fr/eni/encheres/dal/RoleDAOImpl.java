package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl  implements RoleDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Role read(String ROLE) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("ROLE", ROLE);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM ROLES  WHERE ROLE = :ROLE", namedParameters,
                new BeanPropertyRowMapper<>(Role.class));
    }

    @Override
    public List<Role> findAll() {
        return namedParameterJdbcTemplate.query("SELECT *  FROM ROLES", new BeanPropertyRowMapper<>(Role.class));
    }
}

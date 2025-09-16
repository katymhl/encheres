package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Role;

import java.util.List;

public interface RoleDAO
{


    Role read(String ROLE);
    List<Role> findAll();

}

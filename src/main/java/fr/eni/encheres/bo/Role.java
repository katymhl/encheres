package fr.eni.encheres.bo;

import java.util.Objects;

public class Role {

    private String ROLE;
    private int IS_ADMIN;

    public Role() {};

    public Role(String ROLE, int IS_ADMIN) {
        this.ROLE = ROLE;
        this.IS_ADMIN = IS_ADMIN;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public int getIS_ADMIN() {
        return IS_ADMIN;
    }

    public void setIS_ADMIN(int IS_ADMIN) {
        this.IS_ADMIN = IS_ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return IS_ADMIN == role.IS_ADMIN && Objects.equals(ROLE, role.ROLE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ROLE, IS_ADMIN);
    }


    @Override
    public String toString() {
        return "Role{" +
                "ROLE='" + ROLE + '\'' +
                ", IS_ADMIN=" + IS_ADMIN +
                '}';
    }
}

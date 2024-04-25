package fr.btssio.ksav_admin.models.entities;

import fr.btssio.ksav_admin.models.entities.annotations.PrimaryKey;

public class RoleEntity implements Entity {
    
    @PrimaryKey
    private int IDROLE;
    private String LIBELLE;
    
    public RoleEntity(int idRole, String libelle) {
        this.IDROLE = idRole;
        this.LIBELLE = libelle;
    }
    
    public RoleEntity(String libelle) {
        this(0, libelle);
    }
    
    public RoleEntity() {}
    
    public int getIdRole() {
        return this.IDROLE;
    }
    
    public void setIdRole(int idRole) {
        this.IDROLE = idRole;
    }
    
    public String getLibelle() {
        return this.LIBELLE;
    }
    
    public void setLibelle(String libelle) {
        this.LIBELLE = libelle;
    }
    
    @Override
    public String toString() {
        return this.LIBELLE.toUpperCase();
    }
    
}

package fr.btssio.ksav_admin.models.DAO;

import fr.btssio.ksav_admin.models.entities.UtilisateurEntity;
import fr.btssio.ksav_admin.models.querybuilder.QueryBuilder;
import java.util.List;

public class UtilisateurDAO extends DAO {

    /**
     * Permet de retourner une liste d'entité utilisateur
     *
     * @return Liste d'entité utilisateur
     */
    public List<UtilisateurEntity> getAll() {
        QueryBuilder builder = new QueryBuilder();
        builder.select("*").from("utilisateur");
        return this.request(builder.build(), UtilisateurEntity.class);
    }

    /**
     * Permet de retourner une entité utilisateur a partir de son ID
     *
     * @param id
     * @return Entité utilisateur
     */
    public UtilisateurEntity get(int id) {
        QueryBuilder builder = new QueryBuilder();
        builder.select("*").from("utilisateur").where("IDUTILISATEUR = ?", id);
        List<UtilisateurEntity> entities = this.request(builder.build(), builder.getParams(), UtilisateurEntity.class);
        return (entities.isEmpty() ? null : entities.get(0));
    }

    /**
     * Permet d'inserer un utilisateur en BDD.
     *
     * @param entity Utilisateur
     */
    public void insert(UtilisateurEntity entity) {
        QueryBuilder builder = new QueryBuilder();
        builder.insert("utilisateur", entity);
        this.request(builder.build(), builder.getParams());
    }

    /**
     * Permet de supprimer un utilisateur de la BDD.
     *
     * @param id Identifiant utilisateur
     */
    public void delete(int id) {
        QueryBuilder builder = new QueryBuilder();
        builder.delete("utilisateur").where("IDUTILISATEUR = ?", id);
        this.request(builder.build(), builder.getParams());
    }
    
    /**
     * Permet de modifier des informations dans la BDD
     * 
     * @param entity 
     */
    public void update(UtilisateurEntity entity) {
        QueryBuilder builder = new QueryBuilder();
        builder.update("utilisateur", entity).where("IDUTILISATEUR = ?", entity.getIdUtilisateur());
        this.request(builder.build(), builder.getParams());
    }

}

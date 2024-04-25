package fr.btssio.ksav_admin.models.DAO;

import fr.btssio.ksav_admin.models.entities.RoleEntity;
import fr.btssio.ksav_admin.models.querybuilder.QueryBuilder;
import java.util.List;

public class RoleDAO extends DAO {

    /**
     * Permet de retourner la liste des rôles
     *
     * @return Liste de rôle
     */
    public List<RoleEntity> getAll() {
        QueryBuilder builder = new QueryBuilder();
        builder.select("*").from("role");
        return this.request(builder.build(), RoleEntity.class);
    }

    /**
     * Permet de retourner un role a partir de sous ID
     *
     * @param id
     * @return Entité role
     */

    public RoleEntity get(int id) {
        QueryBuilder builder = new QueryBuilder();
        builder.select("*").from("role").where("IDROLE = ?", id);
        List<RoleEntity> entities = this.request(builder.build(), builder.getParams(), RoleEntity.class);
        return (entities.isEmpty() ? null : entities.get(0));
    }

    /**
     * Permet d'inserer un role en BDD
     *
     * @param entity
     */
    public void insert(RoleEntity entity) {
        QueryBuilder builder = new QueryBuilder();
        builder.insert("role", entity);
        this.request(builder.build(), builder.getParams());
    }

    /**
     * Permet de supprimer un role de la BDD
     *
     * @param id
     */
    public void delete(int id) {
        QueryBuilder builder = new QueryBuilder();
        builder.delete("role").where("IDROLE", id);
        this.request(builder.build(), builder.getParams());
    }

    /**
     * Permet de modifier un role de la BDD
     *
     * @param entity
     */
    public void update(RoleEntity entity) {
        QueryBuilder builder = new QueryBuilder();
        builder.update("ROLE", entity).where("IDROLE", entity.getIdRole());
        this.request(builder.build(), builder.getParams());
    }
}

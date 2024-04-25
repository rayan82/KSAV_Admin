package fr.btssio.ksav_admin.models.DAO;

import fr.btssio.ksav_admin.models.entities.UtilisateurRoleEntity;
import fr.btssio.ksav_admin.models.querybuilder.QueryBuilder;
import java.util.List;

public class UtilisateurRoleDAO extends DAO {
    
    /**
     * Permet de retourner une liste d'entité utilisateur avec une jointure sur les roles
     * 
     * @return Objet utilisateur role entitie
     */
    public List<UtilisateurRoleEntity> getAll() {
        QueryBuilder builder = new QueryBuilder();
        builder.select("u.IDUTILISATEUR", "r.IDROLE", "r.LIBELLE", "u.PSEUDO", "u.NOM", "u.PRENOM", "u.DATENAISSANCE", "u.SEXE", "u.DATECREATION");
        builder.from("utilisateur u").join("role r", "u.IDROLE = r.IDROLE").groupBy("IDUTILISATEUR");
        return this.request(builder.build(), UtilisateurRoleEntity.class);
    }
    
}

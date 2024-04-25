package fr.btssio.ksav_admin.models.DAO;

import fr.btssio.ksav_admin.models.Database;
import fr.btssio.ksav_admin.models.entities.Entity;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {

    protected Connection connection;

    protected DAO() {
        this.connection = Database.getConnection();
    }

    /**
     * Permet d'executer un requête et retourne une liste d'objet
     *
     * @param <T>
     * @param sql
     * @param params
     * @param clazz
     * @return Liste d'entité
     */
    protected <T extends Entity> List<T> request(String sql, List<Object> params, Class<? extends Entity> clazz) {
        ResultSet resultSet = null;
        try {
            PreparedStatement stmt = this.connection.prepareCall(sql);
            stmt = this.bindParams(stmt, params);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
        return this.toEntities(resultSet, clazz);
    }

    /**
     * Permet d'executer un requête sans paramètre et retourne une liste
     * d'entité
     *
     * @param <T>
     * @param sql
     * @param clazz
     * @return Liste d'entité
     */
    protected <T extends Entity> List<T> request(String sql, Class<? extends Entity> clazz) {
        return this.request(sql, new ArrayList<>(), clazz);
    }

    /**
     * Permet d'executer une requête sans retourner de valeur
     *
     * @param sql
     * @param params
     */
    protected void request(String sql, List<Object> params) {
        try {
            PreparedStatement stmt = this.connection.prepareCall(sql);
            stmt = this.bindParams(stmt, params);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(e.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Permet d'executer une requête sans retourner de valeur et sans paramètre
     *
     * @param sql
     */
    protected void request(String sql) {
        this.request(sql, new ArrayList<>());
    }

    /**
     * Permet de définir les paramètres dans la requête
     *
     * @param stmt
     * @param params
     * @return Requête préparer
     * @throws SQLException
     */
    private PreparedStatement bindParams(PreparedStatement stmt, List<Object> params) throws SQLException {
        if (!params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject((i + 1), params.get(i));
            }
        }
        return stmt;
    }

    /**
     * Permet de convertir un resultset en liste d'entité
     *
     * @param <T>
     * @param resultSet
     * @param clazz
     * @return Liste d'entité
     */
    private <T extends Entity> List<T> toEntities(ResultSet resultSet, Class<? extends Entity> clazz) {
        List<T> entities = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Object entity = this.toEntity(resultSet, clazz);
                entities.add((T) entity);
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
        return entities;
    }

    /**
     * Permet de convertir un ligne d'un resultset en entité
     *
     * @param resultSet
     * @param clazz
     * @return Entité
     */
    private Object toEntity(ResultSet resultSet, Class<? extends Entity> clazz) {
        Object entity = this.createInstance(clazz);
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(entity, resultSet.getObject(field.getName()));
            }
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
        return entity;
    }

    /**
     * Permet de créer une instance d'une classe
     *
     * @param clazz
     * @return Objet
     */
    private Object createInstance(Class<? extends Entity> clazz) {
        Object instance = null;
        try {
            Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
        return instance;
    }

}

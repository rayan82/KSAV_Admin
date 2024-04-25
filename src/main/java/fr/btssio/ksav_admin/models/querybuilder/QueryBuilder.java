package fr.btssio.ksav_admin.models.querybuilder;

import fr.btssio.ksav_admin.models.entities.Entity;
import fr.btssio.ksav_admin.models.entities.annotations.PrimaryKey;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryBuilder {

    private StringBuilder query;
    private List<Object> params;

    public QueryBuilder() {
        this.query = new StringBuilder();
        this.params = new ArrayList<>();
    }

    public QueryBuilder select(String... columns) {
        this.query.append("SELECT ");
        if (columns.length == 0) {
            this.query.append("*");
        } else {
            for (int i = 0; i < columns.length; i++) {
                query.append(columns[i]).append(i < (columns.length - 1) ? ", " : "");
            }
        }
        return this;
    }

    public QueryBuilder delete(String table) {
        this.query.append("DELETE FROM ").append(table);
        return this;
    }

    public QueryBuilder insert(String table, Entity entity) {
        try {
            this.query.append("INSERT INTO ").append(table);
            
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            columns.append("(");
            values.append("(");
            
            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(entity);
                if (!field.isAnnotationPresent(PrimaryKey.class)) {
                    if (value != null) {
                        this.params.add(value);
                        columns.append(field.getName()).append(i < (fields.length - 1) ? ", " : "");
                        values.append("?").append(i < (fields.length - 1) ? ", " : "");
                    }
                }
            }
            columns.append(")");
            values.append(")");
            this.query.append(" ").append(columns.toString()).append(" VALUES ").append(values.toString());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
        return this;
    }
    
    public QueryBuilder update(String table, Entity entity) {
        try {
            this.query.append("UPDATE ").append(table).append(" SET ");
            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(entity);
                if (!field.isAnnotationPresent(PrimaryKey.class)) {
                    if (value != null) {
                        this.params.add(value);
                        this.query.append(field.getName()).append(" = ?").append(i < (fields.length - 1) ? ", " : "");
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
        return this;
    }

    public QueryBuilder from(String table) {
        this.query.append(" FROM ").append(table);
        return this;
    }

    public QueryBuilder where(String condition, Object value) {
        this.query.append(this.params.isEmpty() || !this.query.toString().contains("WHERE") ? (" WHERE " + condition) : (" AND " + condition));
        this.params.add(value);
        return this;
    }

    public QueryBuilder join(String table, String on) {
        this.query.append(" JOIN ").append(table).append(" ON ").append(on);
        return this;
    }

    public QueryBuilder leftJoin(String table, String on) {
        this.query.append(" LEFT JOIN ").append(table).append(" ON ").append(on);
        return this;
    }

    public QueryBuilder rightJoin(String table, String on) {
        this.query.append(" RIGHT JOIN ").append(table).append(" ON ").append(on);
        return this;
    }

    public QueryBuilder groupBy(String... columns) {
        if (columns.length > 0) {
            this.query.append(" GROUP BY ");
            for (int i = 0; i < columns.length; i++) {
                query.append(columns[i]).append(i < (columns.length - 1) ? ", " : "");
            }
        }
        return this;
    }
    
    public QueryBuilder orderBy(String type, String... columns) {
        if (type.equals("asc") || type.equals("desc")) {
            if (columns.length > 0) {
                this.query.append(" GROUP BY ");
                for (int i = 0; i < columns.length; i++) {
                    query.append(columns[i]).append(i < (columns.length - 1) ? ", " : "");
                }
                query.append(" ").append(type);
            }
        }
        return this;
    }
    
    public QueryBuilder orderBy(String... columns) {
        this.orderBy("asc", columns);
        return this;
    }

    public String build() {
        return this.query.toString();
    }

    public List<Object> getParams() {
        return this.params;
    }

}

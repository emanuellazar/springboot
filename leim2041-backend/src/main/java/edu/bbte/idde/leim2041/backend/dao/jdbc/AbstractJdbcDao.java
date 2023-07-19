package edu.bbte.idde.leim2041.backend.dao.jdbc;

import edu.bbte.idde.leim2041.backend.dao.Dao;
import edu.bbte.idde.leim2041.backend.model.BaseEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AbstractJdbcDao<T extends BaseEntity>  implements Dao<T> {
    private final DataSource dataSource;
    protected final Class<T> modelClass;
    protected final Field[] modelFields;
    private static final Map<Class<?>, String> TYPE_TO_SQL_TYPE = new ConcurrentHashMap<>();

    static {
        TYPE_TO_SQL_TYPE.put(Integer.class, "INT");
        TYPE_TO_SQL_TYPE.put(Float.class, "FLOAT");
        TYPE_TO_SQL_TYPE.put(Long.class, "BIGINT");
        TYPE_TO_SQL_TYPE.put(Boolean.class, "BOOLEAN");
        TYPE_TO_SQL_TYPE.put(String.class, "VARCHAR(1000)");
    }

    @SneakyThrows
    protected T buildObjectFromDbData(ResultSet set) {
        T entity = modelClass.getDeclaredConstructor().newInstance();
        for (Field field: modelFields) {
            Object attribute = set.getObject(field.getName(), field.getType());
            if (attribute != null) {
                Method setter = modelClass.getDeclaredMethod("set"
                        + field.getName().substring(0, 1).toUpperCase(Locale.getDefault())
                        + field.getName().substring(1), attribute.getClass());
                setter.invoke(entity, attribute);
            }
        }
        entity.setId(set.getLong("id"));
        return entity;
    }

    @SneakyThrows
    public AbstractJdbcDao(Class<T> modelClass) {
        dataSource = DataSourceFactory.getDataSource();
        this.modelClass = modelClass;
        modelFields = modelClass.getDeclaredFields();
        StringBuilder createTable = new StringBuilder("create table ")
                .append(modelClass.getSimpleName())
                .append(" (");
        for (Field field: modelFields) {
            createTable.append(field.getName())
                    .append(' ')
                    .append(TYPE_TO_SQL_TYPE.get(field.getType()))
                    .append(", ");
        }
        createTable.append("id bigint primary key auto_increment)");
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection
                    .prepareStatement(createTable.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.info(e.toString());
        }
        log.info(createTable.toString());
    }

    @SneakyThrows
    @Override
    public T create(T entity)  {
        StringBuilder insertIntoTable = new StringBuilder("insert into ")
                .append(modelClass.getSimpleName()).append(" (");
        for (Field field: modelFields) {
            insertIntoTable.append(field.getName()).append(", ");
        }
        insertIntoTable.delete(insertIntoTable.length() - 2, insertIntoTable.length());
        insertIntoTable.append(") values (")
                .append(new String(new char[modelFields.length - 1]).replace("\0", "?, ")).append("?)");
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(insertIntoTable.toString());
            int i = 1;
            for (Field field : modelFields) {
                Method getter = modelClass.getDeclaredMethod("get"
                        + field.getName().substring(0, 1).toUpperCase(Locale.US)
                        + field.getName().substring(1));
                Object getterResult = getter.invoke(entity);
                insertStatement.setObject(i++, getterResult);
            }
            insertStatement.executeUpdate();
        }
        log.info("Object created");
        return entity;
    }

    @SneakyThrows
    @Override
    public Collection<T> findAll() {
        String findAllStatement = "SELECT * FROM "
                + modelClass.getSimpleName();
        Collection<T> allObjects = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement(findAllStatement);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                T entity = buildObjectFromDbData(set);
                allObjects.add(entity);
            }
        }
        log.info("All objects returned");
        return allObjects;
    }
    
    @SneakyThrows
    @Override
    public T findById(Long id) {
        String findById = "SELECT * FROM "
                + modelClass.getSimpleName()
                + " WHERE id = " + id;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement(findById);
            ResultSet set = prep.executeQuery();
            log.info("Object with the id " + id.toString() + " returned");
            if (set.next()) {
                return buildObjectFromDbData(set);
            } else {
                return null;
            }
        }
    }

    @SneakyThrows
    @Override
    public T update(Long id, T entity) {
        StringBuilder updateStatement = new StringBuilder("UPDATE ")
                .append(modelClass.getSimpleName())
                .append(" SET ");

        for (Field field : modelFields) {
            updateStatement.append(field.getName()).append(" =  ?, ");
        }
        updateStatement.delete(updateStatement.length() - 2, updateStatement.length());
        updateStatement.append(" WHERE id = ").append(id).append(';');
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement updatePrep = connection.prepareStatement(updateStatement.toString());
            int i = 1;
            for (Field field : modelFields) {
                Method getter = modelClass.getDeclaredMethod("get"
                        + field.getName().substring(0, 1).toUpperCase(Locale.getDefault())
                        + field.getName().substring(1));
                Object getterResult = getter.invoke(entity);
                updatePrep.setObject(i++, getterResult);
            }
            updatePrep.executeUpdate();
        }
        log.info("Object updated");
        return entity;
    }

    @SneakyThrows
    @Override
    public void delete(Long id) {
        String delete = "DELETE FROM "
                + modelClass.getSimpleName() + " WHERE id = " + id;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement(delete);
            prep.executeUpdate();
        }
    }
}

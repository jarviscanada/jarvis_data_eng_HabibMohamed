package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  /**
   * Gets JdbcTemplate
   *
   * @return JdbcTemplate jdbcTemplate
   */
  abstract public JdbcTemplate getJdbcTemplate();

  /**
   * Gets SimpleJdbcInsert
   *
   * @return SimpleJdbcInsert simpleJdbcInsert
   */
  abstract public SimpleJdbcInsert getSimpleJdbcInsert();

  /**
   * Gets table name
   *
   * @return String tableName
   */
  abstract public String getTableName();

  /**
   * Gets id column name
   *
   * @return String idColumnName
   */
  abstract public String getIdColumnName();

  /**
   * Gets RowMapper
   *
   * @return RowMapper rowMapper
   */
  abstract public RowMapper getRowMapper();

  /**
   * Gets entity class
   *
   * @return Class Entity.class
   */
  abstract Class<T> getEntityClass();

  /**
   * Saves entry into table, and handles creation/update
   * cases appropriately.
   *
   * @param entity
   * @param <S>
   * @return <S>
   */
  @Override
  public <S extends T> S save(S entity) {

    if (existsById(entity.getId())) {
      if (updateOne(entity) != entity.getId()) {
        throw new DataRetrievalFailureException("Unable to update");
      }
    } else {
      addOne(entity);
    }
    return entity;

  }

  /**
   * Retrieves entity from database using provided id.
   * The entity is encased within an Optional.
   *
   * @param id
   * @return Optional containing entity T
   */
  @Override
  public Optional<T> findById(Integer id) {

    Optional<T> entity;
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";

    try {
      entity = Optional.ofNullable((T) getJdbcTemplate()
          .queryForObject(selectSql,
              BeanPropertyRowMapper.newInstance(getEntityClass()), id));

    } catch (IncorrectResultSizeDataAccessException e) {
      throw new DataRetrievalFailureException("Can't find id: " + id, e);
    }

    return entity;

  }

  /**
   * Checks if entity exists in the table using
   * the given id.
   *
   * @param id
   * @return true if exists, false if otherwise
   */
  @Override
  public boolean existsById(Integer id) {

    String sql = "SELECT COUNT(*) AS cnt FROM " + getTableName() + " WHERE " + getIdColumnName()
        + " = ?";

    int count = getJdbcTemplate().queryForObject(sql, new Object[]{id}, Integer.class);
    return count > 0 ? true : false;

  }

  /**
   * Gets all the entities currently within the table
   *
   * @return List containing entities of type T
   */
  @Override
  public List<T> findAll() {

    String sql = "SELECT * FROM " + getTableName();

    List<T> objects = getJdbcTemplate().query(sql, getRowMapper());
    return objects;

  }

  /**
   * Grabs all the entities from the table
   * corresponding to the List of ids provided
   *
   * @param ids
   * @return List of type T entities
   */
  @Override
  public List<T> findAllById(Iterable<Integer> ids) {

    List<T> objects = new ArrayList<T>();

    ids.forEach(
        id -> {
          T object = findById(id).get();
          objects.add(object);
        }
    );

    return objects;

  }

  /**
   * Deletes an entity from the table using the
   * given id.
   *
   * @param id
   */
  @Override
  public void deleteById(Integer id) {

    String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName()
        + " = ?";

    getJdbcTemplate().update(sql, id);

  }

  /**
   * Supplies a count of all the entities
   * in the table.
   *
   * @return long count
   */
  @Override
  public long count() {

    String sql = "SELECT COUNT(*) AS count FROM " + getTableName();

    long count = getJdbcTemplate().queryForObject(sql, Long.class);
    return count;

  }

  /**
   * Deletes everything from the table.
   *
   */
  @Override
  public void deleteAll() {

    String sql = "DELETE FROM " + getTableName();
    getJdbcTemplate().execute(sql);

  }

  /**
   * Saves all the given entities within
   * the provided iterable
   *
   * @param iterable
   * @param <S>
   * @return List of type S entities
   */
  @Override
  public <S extends T> List<S> saveAll(Iterable<S> iterable) {

    iterable.forEach(i -> save(i));
    return (List) iterable;

  }

  /**
   * Adds one entity to the table.
   *
   * @param entity
   * @param <S>
   */
  private <S extends T> void addOne(S entity) {

    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

    Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(newId.intValue());

  }

  /**
   * Updates a given entity in the table.
   *
   * @param entity
   * @return int id
   */
  abstract public int updateOne(T entity);


}

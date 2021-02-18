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

  abstract public JdbcTemplate getJdbcTemplate();

  abstract public SimpleJdbcInsert getSimpleJdbcInsert();

  abstract public String getTableName();

  abstract public String getIdColumnName();

  abstract public RowMapper getRowMapper();

  abstract Class<T> getEntityClass();

  @Override
  public <S extends T> S save(S entity){
    if (existsById(entity.getId())) {
      if (updateOne(entity) != 1){
        throw new DataRetrievalFailureException("Unable to update");
      }
    }else{
      addOne(entity);
    }
    return entity;
  }

  @Override
  public Optional<T> findById(Integer id){

    Optional<T> entity = Optional.empty();
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";

    try{
      entity = Optional.ofNullable((T) getJdbcTemplate()
        .queryForObject(selectSql,
            BeanPropertyRowMapper.newInstance(getEntityClass()), id));

    }catch (IncorrectResultSizeDataAccessException e){
      throw new DataRetrievalFailureException("Can't find id: " + id, e);
    }
    return entity;
  }

  @Override
  public boolean existsById(Integer id){

    String sql = "SELECT COUNT(*) AS cnt FROM "  + getTableName() + " WHERE " + getIdColumnName()
        + " = ?";

    int count = getJdbcTemplate().queryForObject(sql, new Object[]{id}, Integer.class);

    return count > 0 ? true : false;

  }

  @Override
  public List<T> findAll(){

    String sql = "SELECT * FROM " + getTableName();

    List<T> objects = getJdbcTemplate().query(sql, getRowMapper());

    return objects;

  }

  @Override
  public List<T> findAllById(Iterable<Integer> ids){

    List<T> objects = new ArrayList<T>();

    ids.forEach(
        id -> {
          T object = findById(id).get();
          objects.add(object);
        }
    );

    return objects;

  }

  @Override
  public void deleteById(Integer id){

    String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName()
        + " = ?";

    getJdbcTemplate().update(sql, id);

  }

  @Override
  public long count(){

    String sql = "SELECT COUNT(*) AS count FROM " + getTableName();

    long count = getJdbcTemplate().queryForObject(sql, Long.class);

    return count;

  }


  @Override
  public void deleteAll(){

    String sql = "DELETE FROM " + getTableName();

    getJdbcTemplate().execute(sql);

  }

  @Override
  public <S extends T> List<S> saveAll(Iterable<S> iterable) {

    iterable.forEach(i -> save(i));

    return (List) iterable;
  }

  private <S extends T> void addOne(S entity){

    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

    Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(newId.intValue());

  }

  abstract public int updateOne(T entity);


}

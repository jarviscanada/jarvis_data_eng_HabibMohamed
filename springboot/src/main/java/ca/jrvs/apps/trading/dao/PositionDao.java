package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao extends JdbcCrudDao<Position>{

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";

  private final RowMapper<Position> rowMapper = (resultSet, rowNum) -> {

    Position position = new Position();

    position.setId(resultSet.getInt("account_id"));
    position.setTicker(resultSet.getString("ticker"));
    position.setPosition(resultSet.getInt("position"));

    return position;
  };

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public PositionDao(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }


  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  public RowMapper getRowMapper() {
    return rowMapper;
  }

  @Override
  Class<Position> getEntityClass() {
    return Position.class;
  }

  @Override
  public <S extends Position> S save(S entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends Position> List<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(Integer id) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public int updateOne(Position entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(Position securityOrder) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Position> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

}

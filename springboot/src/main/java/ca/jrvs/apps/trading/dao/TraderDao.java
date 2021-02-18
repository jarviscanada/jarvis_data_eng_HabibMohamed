package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader>{

  private final String TABLE_NAME = "trader";
  private final String ID_COLUMN = "id";

  private final RowMapper<Trader> rowMapper = (resultSet, rowNum) -> {
    Trader aTrader = new Trader();
    aTrader.setId(resultSet.getInt("id"));
    aTrader.setFirst_name(resultSet.getString("first_name"));
    aTrader.setLast_name(resultSet.getString("last_name"));
    aTrader.setDob(resultSet.getDate("dob"));
    aTrader.setCountry(resultSet.getString("country"));
    aTrader.setEmail(resultSet.getString("email"));

    return aTrader;
  };


  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public TraderDao(DataSource dataSource){
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
  Class<Trader> getEntityClass() {
    return Trader.class;
  }

  @Override
  public int updateOne(Trader entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(Trader trader) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Trader> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}

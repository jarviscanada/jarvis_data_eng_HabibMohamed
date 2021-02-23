package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

  private final String TABLE_NAME = "account";
  private final String ID_COLUMN = "id";

  private final RowMapper<Account> rowMapper = (resultSet, rowNum) -> {
    Account anAccount = new Account();

    anAccount.setId(resultSet.getInt("id"));
    anAccount.setTraderId(resultSet.getInt("trader_id"));
    anAccount.setAmount(resultSet.getDouble("amount"));

    return anAccount;
  };

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
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
  Class<Account> getEntityClass() {
    return Account.class;
  }

  /**
   * Builds an sql query that updates the
   * specified entity within the table.
   *
   * @param a
   * @return String sqlQuery
   */
  private String buildUpdateSQLQuery(Account a) {

    return "update " + getTableName() + " set " +
        "amount = " + a.getAmount().toString() + " where " +
        ID_COLUMN + " = " + a.getId().toString();

  }

  @Override
  public int updateOne(Account entity) {
    getJdbcTemplate().execute(buildUpdateSQLQuery(entity));
    return entity.getId();
  }

  @Override
  public void delete(Account account) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Account> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}

package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";
  private static final String ALL_COLUMNS = "ticker, last_price, bid_price, bid_size, ask_price, ask_size";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  /**
   * Saves Quote into table.
   *
   * @param quote
   * @return Quote savedQuote
   */
  @Override
  public Quote save(Quote quote) {

    if (existsById(quote.getId())) {
      String sql_query = buildUpdateSQLQuery(quote);
      jdbcTemplate.execute(sql_query);
    } else {
      Map<String, Object> rowMap = buildCreateSQLQuery(quote);
      simpleJdbcInsert.execute(rowMap);
    }

    return quote;
  }

  /**
   * Save all quotes within given iterable to table.
   *
   * @param quotes
   * @param <S>
   * @return List of Quote
   */
  @Override
  public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {

    quotes.forEach(
        q -> save(q)
    );

    return (List) quotes;
  }

  /**
   * Finds Quote within table using
   * provided ticker/id.
   *
   * @param s
   * @return Quote encased in Optional
   */
  @Override
  public Optional<Quote> findById(String s) {

    Quote quote = jdbcTemplate.queryForObject(
        "select " + ALL_COLUMNS + " from " + TABLE_NAME +
            " where " + ID_COLUMN_NAME + " = ?",
        rowMapper,
        s
    );

    return Optional.of(quote);

  }

  /**
   * Checks if Quote exists in table
   * using the provided ticker/id.
   *
   * @param s
   * @return true if exists, false otherwise
   */
  @Override
  public boolean existsById(String s) {

    String sql = "select count(*) as cnt from " + TABLE_NAME + " where " + ID_COLUMN_NAME
        + " = ?";

    int count = jdbcTemplate.queryForObject(sql, Integer.class, s);
    return count > 0 ? true : false;

  }

  /**
   * Retrieves all Quotes in the table.
   *
   * @return List of Quote
   */
  @Override
  public List<Quote> findAll() {

    List<Quote> quotes = jdbcTemplate.query(
        "select " + ALL_COLUMNS + " from " + TABLE_NAME,
        rowMapper
    );

    return quotes;

  }

  /**
   * Counts how many Quote are in the table.
   *
   * @return long count
   */
  @Override
  public long count() {

    long count = jdbcTemplate.queryForObject(
        "select COUNT(*) as count from " + TABLE_NAME,
        countMapper
    );

    return count;

  }

  /**
   * Deletes the given Quote from
   * the table using the ticker/id provided.
   *
   * @param s
   */
  @Override
  public void deleteById(String s) {

    jdbcTemplate.update(
        "delete from " + TABLE_NAME + " where "
            + ID_COLUMN_NAME + " = ?",
        s
    );

  }

  /**
   * Deletes all Quote from the table.
   *
   */
  @Override
  public void deleteAll() {

    jdbcTemplate.update(
        "delete from " + TABLE_NAME
    );

  }

  /**
   * Builds an update query for the
   * given Quote.
   *
   * @param q
   * @return String id/ticker
   */
  private String buildUpdateSQLQuery(Quote q) {

    return "update quote set " +
        "last_price = " + q.getLastPrice().toString() + ", " +
        "ask_price = " + q.getAskPrice().toString() + ", " +
        "ask_size = " + q.getAskSize().toString() + ", " +
        "bid_price = " + q.getBidPrice().toString() + ", " +
        "bid_size = " + q.getBidSize().toString() + " where " +
        ID_COLUMN_NAME + " = '" + q.getId() + "'";

  }

  /**
   * Builds a create query by means
   * of a HashMap.
   *
   * @param q
   * @return HashMap<String,Quote> map
   */
  private Map<String, Object> buildCreateSQLQuery(Quote q) {

    Map<String, Object> map = new HashMap<String, Object>(6);
    map.put(ID_COLUMN_NAME, q.getId());
    map.put("last_price", q.getLastPrice());
    map.put("bid_price", q.getBidPrice());
    map.put("bid_size", q.getBidSize());
    map.put("ask_price", q.getAskPrice());
    map.put("ask_size", q.getAskSize());

    return map;

  }

  /**
   * Returns a RowMapper that builds and
   * returns a Quote object from the ResultSet.
   *
   */
  private final RowMapper<Quote> rowMapper = (resultSet, rowNum) -> {

    Quote theQuote = new Quote();
    theQuote.setId(resultSet.getString("ticker"));
    theQuote.setAskPrice(resultSet.getDouble("ask_price"));
    theQuote.setAskSize(resultSet.getInt("ask_size"));
    theQuote.setBidPrice(resultSet.getDouble("bid_price"));
    theQuote.setBidSize(resultSet.getInt("bid_size"));
    theQuote.setLastPrice(resultSet.getDouble("last_price"));

    return theQuote;

  };

  /**
   * Returns a RowMapper that return a long value
   * that represents the count of rows within the table.
   *
   */
  private final RowMapper<Long> countMapper = (resultSet, rowNum) -> {
    return resultSet.getLong("count");
  };

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

}

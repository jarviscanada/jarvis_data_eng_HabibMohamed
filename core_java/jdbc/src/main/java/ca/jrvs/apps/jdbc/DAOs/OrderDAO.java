package ca.jrvs.apps.jdbc.DAOs;

import ca.jrvs.apps.jdbc.DTOs.Order;
import ca.jrvs.apps.jdbc.DTOs.OrderItem;
import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO extends DataAccessObject<Order> {

  final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

  private final static String GET_ORDER = "SELECT "
      + "c.first_name as cFN, c.last_name as cLN, c.email as cE, o.order_id, "
      + "o.creation_date, o.total_due, o.status, "
      + "s.first_name as sFN, s.last_name as sLN, s.email as sE, "
      + "ol.quantity, p.code, p.name, p.size, p.variety, p.price "
      + "FROM orders o "
      + "JOIN customer c ON o.customer_id = c.customer_id "
      + "JOIN salesperson s ON o.salesperson_id = s.salesperson_id "
      + "JOIN order_item ol ON ol.order_id = o.order_id "
      + "JOIN product p ON ol.product_id = p.product_id "
      + "WHERE o.order_id = ?";


  public OrderDAO(Connection connection) {
    super(connection);
  }


  @Override
  public Order findById(long id) {

    Order order = new Order();

    try (PreparedStatement statement = this.connection.prepareStatement(GET_ORDER);) {

      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();

      List<OrderItem> orderSpecs = new ArrayList<OrderItem>();

      //A flag to check if the information excluding orderSpecifics has been filled in
      boolean isFilledIn = false;

      while (rs.next()) {

        if(!isFilledIn) {

          order.setCustomerFirstName(rs.getString("cFN"));
          order.setCustomerLastName(rs.getString("cLN"));
          order.setCustomerEmail(rs.getString("cE"));
          order.setId(rs.getLong("order_id"));
          order.setCreationDate(rs.getTimestamp("creation_date"));
          order.setTotalDue(rs.getFloat("total_due"));
          order.setStatus(rs.getString("status"));
          order.setSalespersonFirstName(rs.getString("sFN"));
          order.setSalespersonLastName(rs.getString("sLN"));
          order.setSalespersonEmail(rs.getString("sE"));

          isFilledIn = true;

        }

        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setCode(rs.getString("code"));
        orderItem.setName(rs.getString("name"));
        orderItem.setSize(rs.getInt("size"));
        orderItem.setVariety(rs.getString("variety"));
        orderItem.setPrice(rs.getFloat("price"));

        orderSpecs.add(orderItem);

      }

      order.setOrderSpecifics(orderSpecs);

      return order;

    } catch (SQLException e) {
      throw new RuntimeException("Either the PreparedStatement had trouble preparing or there was"
          + " an incorrect interaction with the ResultSet", e);
    }

  }

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public void delete(long id) {

  }

  @Override
  protected int getLastVal(String sequence) {
    return super.getLastVal(sequence);
  }
}

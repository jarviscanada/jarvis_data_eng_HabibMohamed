package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  public static void main(String[] args) {

    final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

    BasicConfigurator.configure();

    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
        "hplussport", "postgres", "password");

    try{
      Connection connection = dcm.getConnection();

      //Use connection here

    }catch (SQLException e){
      logger.error(e.getMessage(), e);
    }

  }

}

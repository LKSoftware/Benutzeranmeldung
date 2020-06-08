package de.szut.benutzeranmeldung;

import java.sql.SQLException;


/**
 * TestHelper for database connection
 * INFO: Usage of Mockito for database tests were not useful at the point of development.
 *
 * @author LK, NM
 */
public class TestHelper
{

  public static MySQLHelper getSqlHelper() throws SQLException
  {
    MySQLHelper helper = new MySQLHelper("localhost", 3306, "root", "root", "schule");
    helper.openConnection();
    return helper;
  }

}

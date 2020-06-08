package de.szut.benutzeranmeldung;

import java.sql.*;


/**
 * Mysql helper class
 *
 * @author LK, NM
 */
public class MySQLHelper
{

    private String host;

    private int port;

    private String user;

    private String pw;

    private String database;

    private Connection con;

    /**
     * Constructor
     *
     * @param host
     * @param port
     * @param user
     * @param password
     * @param database
     */
    public MySQLHelper(String host, int port, String user, String password, String database)
    {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pw = password;
        this.database = database;
    }

    /**
     * Returns a SQLConnection if connection is successful.
     *
     * @throws SQLException
     */
    public Connection openConnection() throws SQLException
    {
        con = DriverManager.getConnection(
          "jdbc:mariadb://" + this.host + ":" + this.port + "/" + this.database, this.user, this.pw);
        return con;
    }

    /**
     * Returns the current SQLConnection.
     */
    public Connection getConnection()
    {
        return con;
    }

    /**
     * Query update wrapped inside a PreparedStatement.
     *
     * @param query
     * @throws SQLException
     */
    public void queryUpdate(String query) throws SQLException
    {
        PreparedStatement st = null;
        try
        {
            st = con.prepareStatement(query);
            st.executeUpdate();
        }
        finally
        {
            closeResources(null, st);
        }
    }

    /**
     * Returns true if current connection is valid.
     */
    public boolean hasConnection()
    {
        try
        {
            return con != null && con.isValid(5);
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    /**
     * Close given resources.
     *
     * @param rs
     * @param st
     * @throws SQLException
     */
    public void closeResources(ResultSet rs, PreparedStatement st) throws SQLException
    {
        if (rs != null)
        {
            rs.close();
        }
        if (st != null)
        {
            st.close();
        }
    }

    /**
     * Close current connection.
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException
    {
        con.close();
        con = null;
    }
}

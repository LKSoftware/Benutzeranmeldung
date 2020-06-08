package de.szut.benutzeranmeldung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * UserRepository containing database commands.
 *
 * @author LK, NM
 */
public class UserRepository
{

    MySQLHelper sql;

    /**
     * Constructor
     * @param mySQLHelper
     * @throws SQLException
     */
    public UserRepository(MySQLHelper mySQLHelper) throws SQLException
    {
        this.sql = mySQLHelper;
        createTable();
    }

    private void createTable() throws SQLException
    {
        sql.queryUpdate(
          "CREATE TABLE IF NOT EXISTS User (id INT NOT NULL AUTO_INCREMENT, email varchar(256) NOT NULL, nickname varchar(32), password varchar(64), registration_date DATETIME, last_login DATETIME, last_refresh DATETIME, confirmed BOOLEAN, registration_number INT(8) NOT NULL, PRIMARY KEY (id))");
    }

    /**
     * Create a user
     * @param email
     * @param password
     * @param nickname
     * @param registrationNumber
     * @param confirmed
     * @return
     * @throws SQLException
     */
    public int createUser(String email,
                          String password,
                          String nickname,
                          int registrationNumber,
                          boolean confirmed) throws SQLException
    {
        PreparedStatement st = null;
        PreparedStatement stWrite = null;
        ResultSet rs = null;
        try
        {
            // We don't allow to have any other nickname as email and vice versa, because you can login with nickname and email
            st = sql.getConnection()
                    .prepareStatement(
                      "SELECT * FROM User WHERE email=? OR nickname=? OR email=? OR nickname=?");
            st.setString(1, email);
            st.setString(2, nickname);
            st.setString(3, nickname);
            st.setString(4, email);
            rs = st.executeQuery();
            if (!rs.next()) {
                stWrite = sql.getConnection().prepareStatement("INSERT INTO User (`email`, `password`, `nickname`, `registration_date`, `confirmed`, `registration_number`) VALUES (?, ?, ?, ?, ?, ?)");
                stWrite.setString(1, email);
                stWrite.setString(2, password);
                stWrite.setString(3, nickname);
                stWrite.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stWrite.setBoolean(5, confirmed);
                stWrite.setInt(6, registrationNumber);
                return stWrite.executeUpdate();
            }
        }
        finally
        {
            sql.closeResources(rs, st);
            sql.closeResources(null, stWrite);
        }
        return 0;
    }

    /**
     * Return a user by given nickname or mail
     *
     * @param nicknameOrEmail
     * @return
     * @throws SQLException
     */
    public User readUser(String nicknameOrEmail) throws SQLException
    {
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st = sql.getConnection().prepareStatement("SELECT * FROM User WHERE email=? OR nickname=?");
            st.setString(1, nicknameOrEmail);
            st.setString(2, nicknameOrEmail);
            rs = st.executeQuery();
            if (rs.next())
            {
                return getUser(rs);
            }
        }
        finally
        {
            sql.closeResources(rs, st);
        }
        return null;
    }

    /**
     * Return user by id.
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public User readUser(int id) throws SQLException
    {
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st = sql.getConnection().prepareStatement("SELECT * FROM User WHERE id=?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next())
            {
                return getUser(rs);
            }
        }
        finally
        {
            sql.closeResources(rs, st);
        }
        return null;
    }

    private User getUser(ResultSet rs) throws SQLException
    {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setNickname(rs.getString("nickname"));
        user.setPassword(rs.getString("password"));
        user.setRegistrationDate(rs.getTimestamp("registration_date") == null ? null :
                                   rs.getTimestamp("registration_date").toLocalDateTime());
        user.setLastLogin(
          rs.getTimestamp("last_login") == null ? null : rs.getTimestamp("last_login").toLocalDateTime());
        user.setLastRefresh(
          rs.getTimestamp("last_refresh") == null ? null : rs.getTimestamp("last_refresh").toLocalDateTime());
        user.setConfirmed(rs.getBoolean("confirmed"));
        user.setRegistrationNumber(rs.getInt("registration_number"));
        return user;
    }

    /**
     * Update nickname of user.
     *
     * @param email
     * @param nickname
     * @return
     * @throws SQLException
     */
    public int updateNickname(String email, String nickname) throws SQLException
    {
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        PreparedStatement stWrite = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try
        {
            st = sql.getConnection().prepareStatement("SELECT * FROM User WHERE email=?");
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                // We don't allow to have any other nickname as email and vice versa, because you can login with nickname and email
                st2 = sql.getConnection().prepareStatement("SELECT * FROM User WHERE nickname=? OR email=?");
                st2.setString(1, nickname);
                st2.setString(2, nickname);
                rs2 = st2.executeQuery();
                if (!rs2.next()) {
                    stWrite = sql.getConnection().prepareStatement("UPDATE User SET nickname=?, last_refresh=? WHERE email=?");
                    stWrite.setString(1, nickname);
                    stWrite.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    stWrite.setString(3, email);
                    return stWrite.executeUpdate();
                } else {
                    return 0;
                }
            }
        }
        finally
        {
            sql.closeResources(rs, st);
            sql.closeResources(rs2, st2);
            sql.closeResources(null, stWrite);
        }
        return 0;
    }

    /**
     * Update user password.
     *
     * @param id
     * @param password
     * @return
     * @throws SQLException
     */
    public int updatePassword(int id, String password) throws SQLException
    {
        PreparedStatement st = null;
        PreparedStatement stWrite = null;
        ResultSet rs = null;
        try
        {
            st = sql.getConnection().prepareStatement("SELECT * FROM User WHERE id=?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next())
            {
                stWrite = sql.getConnection()
                             .prepareStatement("UPDATE User SET password=?, last_refresh=? WHERE id=?");
                stWrite.setString(1, password);
                stWrite.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                stWrite.setInt(3, id);
                return stWrite.executeUpdate();
            }
        }
        finally
        {
            sql.closeResources(rs, st);
            sql.closeResources(null, stWrite);
        }
        return 0;
    }

    /**
     * Update lastLogin
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public int updateLastLogin(String email) throws SQLException
    {
        PreparedStatement st = null;
        PreparedStatement stWrite = null;
        ResultSet rs = null;
        try
        {
            st = sql.getConnection().prepareStatement("SELECT * FROM User WHERE email=?");
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next())
            {
                stWrite = sql.getConnection().prepareStatement("UPDATE User SET last_login=? WHERE email=?");
                stWrite.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                stWrite.setString(2, email);
                return stWrite.executeUpdate();
            }
        }
        finally
        {
            sql.closeResources(rs, st);
            sql.closeResources(null, stWrite);
        }
        return 0;
    }

    /**
     * Update email confirmation.
     *
     * @param registrationNumber
     * @return
     * @throws SQLException
     */
    public int updateConfirmed(String registrationNumber) throws SQLException
    {
        PreparedStatement st = null;
        PreparedStatement stWrite = null;
        ResultSet rs = null;
        try
        {
            st = sql.getConnection().prepareStatement("SELECT * FROM User WHERE registration_number=?");
            st.setString(1, registrationNumber);
            rs = st.executeQuery();
            if (rs.next())
            {
                stWrite = sql.getConnection()
                             .prepareStatement("UPDATE User SET confirmed=? WHERE registration_number=?");
                stWrite.setBoolean(1, true);
                stWrite.setString(2, registrationNumber);
                return stWrite.executeUpdate();
            }
        }
        finally
        {
            sql.closeResources(rs, st);
            sql.closeResources(null, stWrite);
        }
        return 0;
    }

    /**
     * Delete user by id.
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteUser(int id) throws SQLException
    {
        PreparedStatement st = null;
        try
        {
            st = sql.getConnection().prepareStatement("DELETE FROM User WHERE id=?");
            st.setInt(1, id);
            return st.executeUpdate();
        }
        finally
        {
            sql.closeResources(null, st);
        }
    }
}

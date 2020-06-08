package de.szut.benutzeranmeldung;

import java.sql.SQLException;


/**
 * Interface for user management
 *
 * @author LK, NM
 */
public interface ManagementInterface
{

  /**
   * Returns the user by a given token.
   *
   * @param token
   * @throws SQLException
   */
  User getUser(String token) throws SQLException;

  /**
   * Renames the users nickname.
   *
   * @param userId
   * @param email
   * @param nickname
   * @throws SQLException
   */
  void rename(int userId, String email, String nickname) throws SQLException;

  /**
   * Changes the users password.
   *
   * @param userId
   * @param password
   * @throws SQLException
   */
  void changePassword(int userId, String password) throws SQLException;

  /**
   * Deletes a user when a correct password is given.
   *
   * @param userId
   * @param password
   * @throws SQLException
   */
  void delete(int userId, String password) throws SQLException;
}

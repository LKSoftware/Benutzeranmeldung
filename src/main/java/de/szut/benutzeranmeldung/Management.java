package de.szut.benutzeranmeldung;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;


/**
 * Managment class for user management.
 *
 * @author LK, NM
 */
public class Management implements ManagementInterface
{

  private static final Logger LOGGER = LogManager.getLogger(Management.class.getName());

  private UserRepository userRepository;

  /**
   * Constructor
   * @param userRepository
   */
  public Management(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  @Override public User getUser(String token) throws SQLException
  {
    if (Login.authenticatedUser.get(token) != null)
    {
      return userRepository.readUser(Login.authenticatedUser.get(token));
    }
    return null;
  }

  @Override public void rename(int userId, String email, String nickname) throws SQLException {
    userRepository.updateNickname(email, nickname);
    LOGGER.info("User {} nickname updated: {}", email, nickname);
  }

  @Override public void changePassword(int userId, String password) throws SQLException {
    password = PasswordHelper.hashPassword(password);
    userRepository.updatePassword(userId, password);
    LOGGER.info("User {} password updated: {}", userId, password);
  }

  @Override public void delete(int userId, String password) throws SQLException {
    password = PasswordHelper.hashPassword(password);
    if (password.equals(userRepository.readUser(userId).getPassword())) {
      userRepository.deleteUser(userId);
      LOGGER.info("User {} deleted", userId);
    }
    LOGGER.info("User {} not deleted, because password was incorrect", userId);
  }
}

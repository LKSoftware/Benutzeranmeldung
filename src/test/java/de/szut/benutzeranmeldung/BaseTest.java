package de.szut.benutzeranmeldung;

/**
 * Base class for the test values and some utility methods.
 *
 * @author LK, NM
 */
public class BaseTest
{

  static final String EMAIL = "test@example.com";

  static final String PASSWORD = "123456";

  static final String NICKNAME = "Max";

  static final int REGISTRATIONID = 0;

  UserRepository userRepository;

  void init() throws Exception
  {
    userRepository = new UserRepository(TestHelper.getSqlHelper());
  }

  public void createUser() throws Exception
  {
    userRepository.createUser(EMAIL, PasswordHelper.hashPassword(PASSWORD), NICKNAME, REGISTRATIONID, true);
  }

}

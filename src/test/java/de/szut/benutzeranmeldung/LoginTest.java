package de.szut.benutzeranmeldung;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collections;


/**
 * Unit test for {@link LoginTest}
 *
 * @author LK, NM
 */
public class LoginTest extends BaseTest
{

    Login systemUnderTest;

    @BeforeEach void setUp() throws Exception
    {
        Configurator.setLevel(Collections.singletonMap(Login.class.getName(), Level.INFO));
        super.init();
        systemUnderTest = new Login(userRepository);
        createUser();
    }

    @AfterEach void deleteUp() throws SQLException
    {
        User user = userRepository.readUser(NICKNAME);
        userRepository.deleteUser(user.getId());
    }

    @Test public void canLoginWithNickname() throws Exception
    {
        String token = systemUnderTest.login(NICKNAME, PASSWORD);
        int userid = Login.authenticatedUser.get(token);
        Assertions.assertEquals(EMAIL, userRepository.readUser(userid).getEmail());
        Assertions.assertEquals(NICKNAME, userRepository.readUser(userid).getNickname());
    }


    @Test public void canLoginWithEmail() throws Exception
    {
        String token = systemUnderTest.login(EMAIL, PASSWORD);
        int userid = Login.authenticatedUser.get(token);
        Assertions.assertEquals(EMAIL, userRepository.readUser(userid).getEmail());
        Assertions.assertEquals(NICKNAME, userRepository.readUser(userid).getNickname());
    }

    @Test void canLoginWithWrongPassword() throws Exception
    {
        String token = systemUnderTest.login(EMAIL, "WRONG");
        Assertions.assertNull(token);
    }

    @Test public void validateLogin() throws Exception
    {
        String token = systemUnderTest.login(EMAIL, PASSWORD);
        Assertions.assertTrue(Login.authenticatedUser.containsKey(token));
        Assertions.assertTrue(systemUnderTest.isLoginValid(token));
    }

    @Test public void canResetPasswort() throws SQLException
    {
        systemUnderTest.resetPassword(EMAIL);
        String token = systemUnderTest.login(EMAIL, PASSWORD);
        Assertions.assertNull(token);
    }

}

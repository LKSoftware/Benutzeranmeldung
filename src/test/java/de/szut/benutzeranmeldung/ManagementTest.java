package de.szut.benutzeranmeldung;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collections;


/**
 * Unit test for {@link Management}
 *
 * @author LK, NM
 */
public class ManagementTest extends BaseTest
{

    Management systemUnderTest;

    @BeforeEach void setUp() throws Exception
    {
        Configurator.setLevel(Collections.singletonMap(Management.class.getName(), Level.INFO));
        super.init();
        systemUnderTest = new Management(userRepository);
        createUser();
    }

    /**
     * Smoke test
     *
     * @throws Exception
     */
    @Test public void canGetUser() throws Exception
    {
        User user = getUser();
        Assertions.assertEquals(EMAIL, user.getEmail());
        Assertions.assertEquals(NICKNAME, user.getNickname());
        Assertions.assertEquals(PasswordHelper.hashPassword(PASSWORD), user.getPassword());
        Assertions.assertEquals(REGISTRATIONID, user.getRegistrationNumber());
    }

    @Test public void canGetNoUser() throws Exception
    {
        User user = systemUnderTest.getUser("GIBTS NICHT");
        Assertions.assertNull(user);
    }

    private User getUser() throws SQLException
    {
        return getUser(NICKNAME, PASSWORD);
    }

    private User getUser(String nickname, String password) throws SQLException
    {
        Login lgn = new Login(userRepository);
        String token = lgn.login(nickname, password);
        return systemUnderTest.getUser(token);
    }

    @Test public void canRename() throws Exception
    {
        User user = getUser();
        systemUnderTest.rename(user.getId(), EMAIL, "changeMe");
        user = getUser("changeMe", PASSWORD);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("changeMe", user.getNickname());
        //Check if old nickname is not usable anymore
        user = getUser();
        Assertions.assertNull(user);
        user = getUser("changeMe", PASSWORD);
        systemUnderTest.rename(user.getId(), EMAIL, NICKNAME);
    }

    @Test public void canChangePassword() throws Exception
    {
        User user = getUser();
        systemUnderTest.changePassword(user.getId(), "new");
        user = getUser(NICKNAME, "new");
        Assertions.assertEquals(PasswordHelper.hashPassword("new"), user.getPassword());
        Assertions.assertNotNull(user);
        systemUnderTest.changePassword(user.getId(), PASSWORD);
        user = getUser();
        Assertions.assertNotNull(user);
    }

    @Test public void canDeleteUser() throws Exception
    {
        User user = getUser();
        Assertions.assertNotNull(user);
        systemUnderTest.delete(user.getId(), PASSWORD);
        user = getUser();
        Assertions.assertNull(user);
    }


}

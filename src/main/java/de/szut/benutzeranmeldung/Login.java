package de.szut.benutzeranmeldung;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Login Controller using {@link LoginInterface}
 *
 * @author LK, NM
 */
public class Login implements LoginInterface
{

    private static final Logger LOGGER = LogManager.getLogger(Login.class.getName());

    static final Map<String, Integer> authenticatedUser = new HashMap<>();

    private UserRepository userRepository;

    /**
     * Constructor
     */
    public Login(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override public String login(String nicknameOrEmail, String password) throws SQLException
    {
        User user = userRepository.readUser(nicknameOrEmail);
        if (user != null && user.isConfirmed())
        {
            password = PasswordHelper.hashPassword(password);
            if (password.equals(user.getPassword()))
            {
                String token = PasswordHelper.generateRandom(24);
                authenticatedUser.put(token, user.getId());
                userRepository.updateLastLogin(user.getEmail());
                return token;
            }
        }
        return null;
    }

    @Override public boolean isLoginValid(String token)
    {
        return authenticatedUser.get(token) != null;
    }

    @Override public void requestPasswordReset(String email)
    {
        // No email stuff, keep in mind that you would need to generate a token and compare it in resetPassword(). This is currently not implemented.
    }

    @Override public void resetPassword(String email) throws SQLException
    {
        String newPassword = PasswordHelper.hashPassword(PasswordHelper.generateRandom(8));
        userRepository.updatePassword(userRepository.readUser(email).getId(), newPassword);
        LOGGER.info("User {} password updated: {}", email, newPassword);
    }
}

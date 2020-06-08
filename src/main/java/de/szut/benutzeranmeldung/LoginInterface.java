package de.szut.benutzeranmeldung;

import java.sql.SQLException;


/**
 * Basic login functions should be implemented when this interface is used.
 *
 * @author LK, NM
 */
public interface LoginInterface
{

    /**
     * Successful  Login should return a token.
     *
     * @param nicknameOrEmail
     * @param password
     * @return a token
     * @throws SQLException
     */
    String login(String nicknameOrEmail, String password) throws SQLException;

    /**
     * Checks whether the given token is valid.
     *
     * @param token
     * @return true if valid
     */
    boolean isLoginValid(String token);

    /**
     * Password reset request should send reset instruction to a given mail via Email server connection.
     *
     * @param email
     */
    void requestPasswordReset(String email);

    /**
     * Resets a password inside the database.
     *
     * @param email
     * @throws SQLException
     */
    void resetPassword(String email) throws SQLException;
}

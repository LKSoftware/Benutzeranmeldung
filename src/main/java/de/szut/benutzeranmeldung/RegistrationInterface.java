package de.szut.benutzeranmeldung;

import java.sql.SQLException;


/**
 * Registration interface to register users.
 *
 * @author LK, NM
 */
public interface RegistrationInterface
{

    /**
     * Register a new user.
     *
     * @param email
     * @param password
     * @param nickname
     * @throws SQLException
     */
    void register(String email, String password, String nickname) throws SQLException;

    /**
     * Confirm a user registration.
     *
     * @param registrationNumber
     * @throws SQLException
     */
    void confirm(String registrationNumber) throws SQLException;
}

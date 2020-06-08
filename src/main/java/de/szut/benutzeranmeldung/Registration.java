package de.szut.benutzeranmeldung;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;


/**
 * Registration to register new users.
 *
 * @author LK, NM
 */
class Registration implements RegistrationInterface
{

    private static final Logger LOGGER = LogManager.getLogger(Registration.class.getName());

    private UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository
     */
    public Registration(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Best practice in a library is to throw a RuntimeException here, see https://www.artima.com/weblogs/viewpost.jsp?thread=142428
     * We don't handle stuff like "already registered" here, because it's not required by the task
     */
    @Override public void register(String email, String password, String nickname) throws SQLException
    {
        if (email == null || email.equals("")) {
            throw new RuntimeException("Register method was called without an email. This is not allowed.");
        }
        if (password == null || password.equals("")) {
            password = PasswordHelper.generateRandom(8);
        }
        password = PasswordHelper.hashPassword(password);
        int registrationNumber = PasswordHelper.generateEightDigitNumber();
        userRepository.createUser(email, password, nickname, registrationNumber, false);
        LOGGER.info("Generated hash password: {}", password);
        LOGGER.info("Generated registration code: {}", registrationNumber);
    }

    @Override public void confirm(String registrationNumber) throws SQLException {
        userRepository.updateConfirmed(registrationNumber);
        LOGGER.info("User confirmed: {}", registrationNumber);
    }
}

package de.szut.benutzeranmeldung;

import java.sql.SQLException;

/**
 * TODO: We could use the builder pattern here, also we could use modules or impl-packages instead of package-private
 * Also we decided to do everything over the main class. We could also open up the design and use configure(), if it
 * was not called before, throw a RuntimeException
 * Also we could not use the MySQLHelper in the Constructor, instead make a getSQL() call to the main class.
 *
 * @author LK, NM
 */
public class Benutzeranmeldung
{

    private Registration registration;

    private Login login;

    private Management management;

    private UserRepository userRepository;

    /**
     * Constructor
     *
     * @param mySQLHelper
     * @throws SQLException
     */
    public Benutzeranmeldung(MySQLHelper mySQLHelper) throws SQLException
    {
        if (!(mySQLHelper.hasConnection()))
        {
            mySQLHelper.openConnection();
        }
        this.userRepository = new UserRepository(mySQLHelper);
        this.registration = new Registration(userRepository);
        this.login = new Login(userRepository);
        this.management = new Management(userRepository);
    }

    /**
     * Returns the {@link RegistrationInterface}
     */
    public RegistrationInterface getRegistration()
    {
        return registration;
    }

    /**
     * Returns the {@link LoginInterface}
     */
    public LoginInterface getLogin()
    {
        return login;
    }

    /**
     * Returns the {@link ManagementInterface}
     */
    public ManagementInterface getManagement()
    {
        return management;
    }
}

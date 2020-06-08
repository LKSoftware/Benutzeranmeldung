package de.szut.benutzeranmeldung;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.verification.Times;

import java.sql.SQLException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;


/**
 * Unit test for {@link Registration}
 *
 * @author LK, NM
 */
class RegistrationTest extends BaseTest
{

    @InjectMocks Registration registration;

    @Mock UserRepository userRepository;

    @BeforeEach void setUp()
    {
        Configurator.setLevel(Collections.singletonMap(Registration.class.getName(), Level.INFO));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterWithoutEmail() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registration.register("", PASSWORD, NICKNAME);
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            registration.register(null, PASSWORD, NICKNAME);
        });
    }

    @Test
    void testRegisterWithoutPassword() throws SQLException {
        registration.register(EMAIL, null, NICKNAME);
        registration.register(EMAIL, "", NICKNAME);
        Mockito.verify(userRepository, new Times(2)).createUser(eq(EMAIL), AdditionalMatchers.and(anyString(), AdditionalMatchers.not(eq(""))), eq(NICKNAME), anyInt(), anyBoolean());
    }
}

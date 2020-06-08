package de.szut.benutzeranmeldung;

import java.sql.SQLException;
import java.util.Collections;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Simple unit test for {@link Benutzeranmeldung}
 *
 * @author LK, NM
 */
public class BenutzeranmeldungTest extends BaseTest
{

  Benutzeranmeldung systemUnderTest;

  @BeforeEach void setUp() throws Exception
  {
    Configurator.setLevel(Collections.singletonMap(Management.class.getName(), Level.INFO));
  }

  @Test public void smoke() throws SQLException
  {
    systemUnderTest = new Benutzeranmeldung(TestHelper.getSqlHelper());
    Assertions.assertNotNull(systemUnderTest.getLogin());
    Assertions.assertNotNull(systemUnderTest.getRegistration());
    Assertions.assertNotNull(systemUnderTest.getManagement());
  }
}

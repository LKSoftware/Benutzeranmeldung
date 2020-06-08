package de.szut.benutzeranmeldung;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


/**
 * Password Helper
 *
 * @author LK, NM
 */
class PasswordHelper
{

  private static final SecureRandom secureRandom = new SecureRandom();

  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

  /**
   * Random String generator.
   *
   * @param length
   * @return
   */
  public static String generateRandom(int length)
  {
    byte[] randomBytes = new byte[length];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }

  /**
   * Generates a random eight digit number.
   */
  public static int generateEightDigitNumber()
  {
    return 10000000 + secureRandom.nextInt(90000000);
  }

  /**
   * Hash the password.
   *
   * @param password
   * @return
   */
  public static String hashPassword(String password)
  {
    MessageDigest digest = null;
    try
    {
      digest = MessageDigest.getInstance("SHA-256");
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException("Password hashing exception. This should not happen.");
    }
    return bytesToHex(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
  }

  /**
   * Convert bytes to hex string.
   *
   * @param hash
   * @return
   */
  public static String bytesToHex(byte[] hash)
  {
    StringBuilder hexString = new StringBuilder();
    for ( byte b : hash )
    {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1)
        hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }
}

package de.szut.benutzeranmeldung;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * User Model
 *
 * @author LK, NM
 */
@Data public class User
{

    int id;

    String email;

    String nickname;

    String password;

    LocalDateTime registrationDate; //TODO: Valid for 2 weeks, after that, user will be deleted

    LocalDateTime lastLogin;

    LocalDateTime lastRefresh;

    boolean confirmed;

    int registrationNumber;
}

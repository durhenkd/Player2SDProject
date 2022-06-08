package com.player2.server.bussiness;

import com.player2.server.exception.InvalidRegisterInputException;
import com.player2.server.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountService implements UserDetailsService {

    public static final String USER_AUTHORITY = "USER";
    public static final String ADMIN_AUTHORITY = "ADMIN";

    public static final int MINIMUM_PASS_LENGTH = 8;
    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[a-zA-z]+\\.[a-z]+$");
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_.-]+$");


    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /***
     * this is a java spring function, avoid
     * @param username the username
     * @return an UserDetails object containing the username, password, and authorities of the user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /**
     * This function checks if the string is an email.</br>
     * <p>
     * It uses the following regex: ^[A-Za-z0-9+_.-]+@[a-zA-z]+\.[a-z]+$
     *
     * @param email the email to be checked
     * @return true if the string has a good for,. False if otherwise.
     */
    public static boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    /**
     * This function validates a username for the purposes of registering an account.</br>
     * <p>
     * The username must contain only alphanumeric characters and/or "_", ".", and "-".</br>
     * No spaces are allowed.</br>
     * <p>
     * it matches the following regex: ^[a-zA-Z0-1_.-]+$</br>
     *
     * @param username username to be validated.
     * @return true if the username has a good form, false if otherwise.
     */
    public static boolean isValidUsername(String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.find();
    }

    /**
     * This functions validates the password.</br>
     * The function doesn't return anything, it just throws InvalidRegisterInputException if
     * one of the following conditions aren't met.</br>
     * <p>
     * - Password should be at least 8 characters long. </br>
     * - Should contain a number.</br>
     * - Should contain a special character.</br>
     *
     * @param password password to be validated
     * @throws InvalidRegisterInputException
     */
    public static void isValidPassword(String password) throws InvalidRegisterInputException {
        if (password.length() < MINIMUM_PASS_LENGTH)
            throw new InvalidRegisterInputException("Password should be at least " +
                    AccountService.MINIMUM_PASS_LENGTH +
                    " characters long. Current length: " + password.length() + ".");

        if (notContainsOneOf(password, "1234567890"))
            throw new InvalidRegisterInputException("Password should contain at least a number.");
        if (notContainsOneOf(password, ".?!@#$%^&*()_+-=<>?:[];'|"))
            throw new InvalidRegisterInputException("Password should contain at least one special character");
    }

    /**
     * @param string           the string in which we search the characters
     * @param listOfCharacters the characters to be searched, as a string
     * @return if at least one of the characters was found in the string
     */
    private static boolean notContainsOneOf(String string, String listOfCharacters) {
        for (char c : listOfCharacters.toCharArray()) {
            if (string.contains(c + ""))
                return false;
        }
        return true;
    }

    /**
     * Checks if a username already exists within a database
     *
     * @param username -
     * @return true if it's present, false otherwise
     */
    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    /**
     * Checks if an email already exists within a database
     *
     * @param email the mail
     * @return true if it's present, false otherwise
     */
    private boolean emailExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    /**
     * Checks if a telephone number already exists within a database
     *
     * @param telephone the telephone number
     * @return true if it's present, false otherwise
     */
    private boolean telephoneExists(String telephone) {
        return accountRepository.findByEmail(telephone).isPresent();
    }

    /***
     * @return the authorities for a regular user
     */
    private Collection<? extends GrantedAuthority> getUserAuthorities() {
        /**
         * Basically authorities are a string
         * GrantedAuthority is just a container for such string
         * SimpleGrantedAuthority is just an implementation of GrantedAuthority
         */
        return new HashSet<>(Set.of(new SimpleGrantedAuthority(USER_AUTHORITY)));
    }

    /***
     *
     * @return the authorities for an admin
     */
    private Collection<? extends GrantedAuthority> getAdminAuthorities() {
        /**
         * Basically authorities are a string
         * GrantedAuthority is just a container for such string
         * SimpleGrantedAuthority is just an implementation of GrantedAuthority
         */
        return new HashSet<>(Set.of(new SimpleGrantedAuthority(ADMIN_AUTHORITY)));
    }
}

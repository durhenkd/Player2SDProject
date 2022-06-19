package com.player2.server.bussiness;

import com.player2.server.exception.AlreadyExistsException;
import com.player2.server.exception.InvalidRegisterInputException;
import com.player2.server.model.Account;
import com.player2.server.model.Category;
import com.player2.server.model.Clique;
import com.player2.server.model.Player;
import com.player2.server.persistence.AccountRepository;
import com.player2.server.persistence.CategoryRepository;
import com.player2.server.persistence.CliqueRepository;
import com.player2.server.persistence.PlayerRepository;
import com.player2.server.web.CliqueRegistrationDTO;
import com.player2.server.web.LoginInformationDTO;
import com.player2.server.web.PlayerRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    public static final String PLAYER_AUTHORITY = "PLAYER";
    public static final String CLIQUE_AUTHORITY = "CLIQUE";

    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[a-zA-z]+\\.[a-z]+$");
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_.-]+$");
    private static final Pattern telephonePattern = Pattern.compile("[0-9]+");
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");

    private final AccountRepository accountRepository;
    private final PlayerRepository playerRepository;
    private final CliqueRepository cliqueRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          PlayerRepository playerRepository,
                          CliqueRepository cliqueRepository,
                          CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.playerRepository = playerRepository;
        this.cliqueRepository = cliqueRepository;
        this.categoryRepository = categoryRepository;
    }


    /***
     * this is a java spring function, avoid
     * @param username the username
     * @return an UserDetails object containing the username, password, and authorities of the user
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> maybeAccount = Optional.empty();
        if (isValidEmail(username)) maybeAccount = accountRepository.findByEmail(username);
        else if (isValidUsername(username)) maybeAccount = accountRepository.findByUsername(username);
        else if (isValidTelephone(username)) maybeAccount = accountRepository.findByTelephone(username);

        if (maybeAccount.isEmpty()) {
            log.error("loadByUsername: Couldn't find account " + username);
            throw new UsernameNotFoundException("Couldn't find account " + username);
        }

        Optional<Player> maybePlayer = playerRepository.findByAccount(maybeAccount.get());
        if (maybePlayer.isPresent()) {
            return new User(maybeAccount.get().getUsername(), maybeAccount.get().getPassword(), getPlayerAuthorities());
        }

        Optional<Clique> maybeClique = cliqueRepository.findByAccount(maybeAccount.get());
        if (maybeClique.isPresent()) {
            return new User(maybeAccount.get().getUsername(), maybeAccount.get().getPassword(), getCliqueAuthorities());
        }

        log.error("loadByUsername: found account, but couldn't find associated user: " + maybeAccount.get());
        throw new UsernameNotFoundException("found account, but couldn't find associated user");
    }

    //TODO javadoc
    public Clique save(CliqueRegistrationDTO cliqueDTO) throws AlreadyExistsException, InvalidRegisterInputException {
        Account account = save(
                new Account(
                        cliqueDTO.getEmail(),
                        cliqueDTO.getTelephone(),
                        cliqueDTO.getUsername(),
                        cliqueDTO.getPassword()
                )
        );
        Optional<Category> maybeCategory = categoryRepository.findByName(cliqueDTO.getCategory());
        Category category = null;
        if (maybeCategory.isEmpty())
            category = categoryRepository.save(new Category(cliqueDTO.getCategory()));
        else
            category = maybeCategory.get();

        Clique clique = new Clique(
                account,
                cliqueDTO.getName(),
                category,
                new ArrayList<>()   //posts
        );

        return cliqueRepository.save(clique);
    }

    //TODO javadoc
    public Player save(PlayerRegistrationDTO playerDTO) throws AlreadyExistsException, InvalidRegisterInputException {
        Account account = save(
                new Account(
                        playerDTO.getEmail(),
                        playerDTO.getTelephone(),
                        playerDTO.getUsername(),
                        playerDTO.getPassword()
                )
        );
        Player player = new Player(
                account,
                playerDTO.getFirstName(),
                playerDTO.getLastName(),
                playerDTO.getGender(),
                playerDTO.getPicPath(),
                new ArrayList<>()   //follows
        );

        return playerRepository.save(player);
    }

    public LoginInformationDTO getLoginInformation(String username){
        Optional<Account> maybeAccount = accountRepository.findByUsername(username);
        if(maybeAccount.isEmpty()) return new LoginInformationDTO(-1, "", false);

        Account account = maybeAccount.get();
        if(playerRepository.existsByAccount(account))
            return new LoginInformationDTO(account.getId().intValue(), account.getUsername(), false);

        if(cliqueRepository.existsByAccount(account))
            return new LoginInformationDTO(account.getId().intValue(), account.getUsername(), true);

        return new LoginInformationDTO(-1, "Internal server error", false);
    }

    /***
     * Validates the input, and persists the user. This function is meant to persist new users.
     * @param account - the user object as sent by the client
     * @return the persisted user, containing the id
     * @throws AlreadyExistsException   when the email, or username already exists
     * @throws InvalidRegisterInputException when the email or username are in an invalid format
     */
    private Account save(Account account)
            throws AlreadyExistsException, InvalidRegisterInputException {
        if (usernameExists(account.getUsername()))
            throw new AlreadyExistsException("Username " + account.getUsername() + " already exists.");
        if (emailExists(account.getEmail()))
            throw new AlreadyExistsException("Email " + account.getEmail() + " already exists.");
        if (telephoneExists(account.getTelephone()))
            throw new AlreadyExistsException("Telephone " + account.getTelephone() + " already exists.");

        if (!isValidUsername(account.getUsername()))
            throw new InvalidRegisterInputException(account.getUsername() + " is not a valid username.");
        if (!isValidEmail(account.getEmail()))
            throw new InvalidRegisterInputException(account.getEmail() + " is not a valid email.");
        if (!isValidTelephone(account.getTelephone()))
            throw new InvalidRegisterInputException(account.getTelephone() + " is not a valid telephone number.");
        if (!isValidPassword(account.getPassword()))
            throw new InvalidRegisterInputException(account.getPassword() + " is not a valid password.");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(account.getPassword()));

        return accountRepository.save(account);
    }

    //TODO javadoc
    private List<Category> getCategoriesFromStringsWithInsert(List<String> categoryNames) {
        if (categoryNames == null)
            return new ArrayList<>();

        return categoryNames.stream()
                .map(catName -> {

                    Optional<Category> maybeCategory = categoryRepository.findByName(catName);
                    return maybeCategory.orElseGet(() -> categoryRepository.save(new Category(catName)));

                }).collect(Collectors.toList());
    }

    /**
     * This function checks if the string is an email.</br>
     * <p>
     * It uses the following regex: ^[A-Za-z0-9+_.-]+@[a-zA-z]+\.[a-z]+$
     *
     * @param email the email to be checked
     * @return true if the string has matched, false if otherwise.
     */
    private boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    /**
     * This function validates that a string is a username.</br>
     * <p>
     * The username must contain only alphanumeric characters and/or "_", ".", and "-".</br>
     * No spaces are allowed.</br>
     * <p>
     * it matches the following regex: ^[a-zA-Z0-1_.-]+$</br>
     *
     * @param username username to be validated.
     * @return true if the username has matched, false if otherwise.
     */
    private boolean isValidUsername(String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.find();
    }

    /**
     * This function validates that a string is a telephone number.</br>
     * <p>
     * it matches the following regex: [0-9]+</br>
     *
     * @param telephone telephone number to be validated.
     * @return true if the number has matched, false if otherwise.
     */
    private boolean isValidTelephone(String telephone) {
        Matcher matcher = telephonePattern.matcher(telephone);
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
     * - Should contain a letter</br>
     *
     * @param password password to be validated
     * @return true if the password has matched, false if otherwise.
     */
    private boolean isValidPassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.find();
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
    private Collection<? extends GrantedAuthority> getPlayerAuthorities() {
        /*
         * Basically authorities are a string
         * GrantedAuthority is just a container for such string
         * SimpleGrantedAuthority is just an implementation of GrantedAuthority
         */
        return List.of(new SimpleGrantedAuthority(PLAYER_AUTHORITY));
    }

    /***
     *
     * @return the authorities for an admin
     */
    private Collection<? extends GrantedAuthority> getCliqueAuthorities() {
        /*
         * Basically authorities are a string
         * GrantedAuthority is just a container for such string
         * SimpleGrantedAuthority is just an implementation of GrantedAuthority
         */
        return List.of(new SimpleGrantedAuthority(CLIQUE_AUTHORITY));
    }
}

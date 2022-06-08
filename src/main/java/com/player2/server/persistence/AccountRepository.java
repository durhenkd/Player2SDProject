package com.player2.server.persistence;

import com.player2.server.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByTelephone(String telephone);
    Optional<Account> findByUsernameOrEmailOrTelephone(String username, String email, String telephone);

}

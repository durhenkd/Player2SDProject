package com.player2.server.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "p2_account")
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Account {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String telephone;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String password;

    public Account(String email, String telephone, String username, String password) {
        this.email = email;
        this.telephone = telephone;
        this.username = username;
        this.password = password;
    }

    // recommended equals and hashcode implementations
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

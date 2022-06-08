package com.player2.server.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    public Account(String email, String telephone, String username) {
        this.email = email;
        this.telephone = telephone;
        this.username = username;
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

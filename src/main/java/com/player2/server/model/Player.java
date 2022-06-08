package com.player2.server.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Player {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    private Account account;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String firstName;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String lastName;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private Gender gender;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String picPath;

    @ManyToMany
    private List<Category> categories;

    public Player(Account account,
                  String firstName,
                  String lastName,
                  Gender gender,
                  String picPath) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Player player = (Player) o;
        return id != null && Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

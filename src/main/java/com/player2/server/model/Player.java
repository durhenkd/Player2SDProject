package com.player2.server.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "p2_player")
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
    @Column(nullable = false)
    private String firstName;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String lastName;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private Gender gender;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String picPath;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String bio;

    @ManyToMany
    private List<Clique> follows;

    public Player(Account account,
                  String firstName,
                  String lastName,
                  Gender gender,
                  String picPath,
                  String bio,
                  List<Clique> follows) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.picPath = picPath;
        this.follows = follows;
        this.bio = bio;
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

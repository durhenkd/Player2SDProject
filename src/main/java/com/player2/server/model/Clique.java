package com.player2.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "p2_clique")
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Clique {

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
    private String name;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<Post> posts;

    public Clique(Account account, String name, Category category, List<Post> posts) {
        this.account = account;
        this.name = name;
        this.category = category;
        this.posts = posts;
    }
}

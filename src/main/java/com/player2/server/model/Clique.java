package com.player2.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @ManyToMany
    private List<Category> categories;

    @OneToMany
    private List<Post> posts;

    public Clique(Account account, String name, List<Category> categories, List<Post> posts) {
        this.account = account;
        this.name = name;
        this.categories = categories;
        this.posts = posts;
    }
}

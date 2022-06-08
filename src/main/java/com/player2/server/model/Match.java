package com.player2.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Match {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Include
    private Player player1;

    @ManyToOne
    @ToString.Include
    private Player player2;

    @ToString.Include
    @Setter
    @Column
    private String postIt1;

    @ToString.Include
    @Setter
    @Column
    private String postIt2;

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private int score;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private boolean accepted1;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private boolean accepted2;

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.postIt1 = null;
        this.postIt2 = null;
        this.score = 0;
        this.accepted1 = false;
        this.accepted2 = false;
    }
}

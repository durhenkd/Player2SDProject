package com.player2.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "p2_match")
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
    @Column(nullable = false)
    private int accepted1;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private int accepted2;

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.postIt1 = null;
        this.postIt2 = null;
        this.accepted1 = 0;
        this.accepted2 = 0;
    }

    public long getScore(){
        List<Clique> follows1 = player1.getFollows();
        List<Clique> follows2 = player2.getFollows();



        return (100 * accepted1 + 100 * accepted2 + follows1.stream().filter(follows2::contains).count() * 5);
    }
}

package com.player2.server.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "p2_post")
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Post {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String datetime;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String title;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String contentPath;

    public Post(String datetime, String contentPath, String title) {
        this.datetime = datetime;
        this.contentPath = contentPath;
        this.title = title;
    }
}

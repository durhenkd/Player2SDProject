package com.player2.server.web;

import com.player2.server.model.Post;
import lombok.Value;

@Value
public class PostResponseDTO {

    public PostResponseDTO(Post post, String clique_name, int clique_id){
        this.title = post.getTitle();
        this.id = post.getId().intValue();
        this.datetime = post.getDatetime();
        this.contentPath = post.getDatetime();
        this.clique_name = clique_name;
        this.clique_id = clique_id;
    }

    public PostResponseDTO(String title, int id, String datetime, String contentPath, String clique_name, int clique_id) {
        this.title = title;
        this.id = id;
        this.datetime = datetime;
        this.contentPath = contentPath;
        this.clique_name = clique_name;
        this.clique_id = clique_id;
    }

    String title;
    int id;
    String datetime;
    String contentPath;
    String clique_name;
    int clique_id;
}

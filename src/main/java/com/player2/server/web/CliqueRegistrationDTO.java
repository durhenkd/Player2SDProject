package com.player2.server.web;

import lombok.Value;

import java.util.List;

@Value
public class CliqueRegistrationDTO {

    String username;
    String email;
    String telephone;
    String password;
    String name;
    List<String> categories;

}

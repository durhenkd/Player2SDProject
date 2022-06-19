package com.player2.server.web;

import lombok.Value;

@Value
public class LoginInformationDTO {

    int id;
    String name;
    boolean isAdmin;

}

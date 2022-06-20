package com.player2.server.web;

import com.player2.server.model.Gender;
import lombok.Value;


@Value
public class PlayerRegistrationDTO {

    String username;
    String email;
    String telephone;
    String password;
    String firstName;
    String lastName;
    Gender gender;
    String picPath;
    String bio;
}

package com.player2.server.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/**")
public class Controller{

    @GetMapping
    public String index(){
        return "index";
    }

}

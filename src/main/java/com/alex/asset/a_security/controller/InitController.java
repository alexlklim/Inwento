package com.alex.asset.a_security.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/init")
public class InitController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String init(){
        try {
            jdbcTemplate.execute("classpath:init.sql");
            return "Database initialized successfully!";
        } catch (Exception e) {
            return "Error initializing database: " + e.getMessage();
        }
    }
}

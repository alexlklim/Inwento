package com.alex.asset.email;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class MailStructure {
    private String email;
    private String subject;
    private String message;
}

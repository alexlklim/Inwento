package com.alex.asset.email;


import com.alex.asset.security.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailStructure {
    private String email;
    private String subject;
    private String message;
}

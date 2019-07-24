package com.seungwoo.books.config;

import com.seungwoo.books.account.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthenticationUser extends User {

    private Long id;

    public AuthenticationUser(Account account) {
        super(account.getUsername(), account.getPassword(), Collections.singleton(() -> "USER"));
        this.id = account.getId();
    }
}

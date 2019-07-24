package com.seungwoo.books.account;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountDto {

    @Data
    public static class AccountCreate {
        @NotNull
        @NotEmpty
        private String username;
        @NotNull
        @NotEmpty
        private String password;
    }
}

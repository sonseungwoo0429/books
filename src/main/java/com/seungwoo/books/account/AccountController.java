package com.seungwoo.books.account;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final ModelMapper modelMapper;

    private final AccountService accountService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }


    @GetMapping(value = "/member_join")
    public String singUp(@ModelAttribute AccountDto.AccountCreate account) {
        return "member_join";
    }

    @PostMapping(value = "/singup/user")
    public String userSingUp(@ModelAttribute @Valid AccountDto.AccountCreate account, BindingResult result) {
        if (result.hasErrors()) {
            return singUp(account);
        }
        accountService.signup(modelMapper.map(account, Account.class));
        return "login";
    }
}

package com.seungwoo.books.config;

import com.seungwoo.books.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return this.accountRepository.findByUsername(userName)
                .map(AuthenticationUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
    }

}

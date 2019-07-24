package com.seungwoo.books.account;

import com.seungwoo.books.exception.ReduplicationUsernameException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {

    @Autowired
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        this.accountService = new AccountService(accountRepository, passwordEncoder);
    }

    @Test
    public void signup_test() {
        Account accountTest = new Account("test@test.com", "0000");
        given(passwordEncoder.encode(anyString())).willReturn(accountTest.getPassword());
        given(accountRepository.findByUsername(anyString())).willReturn(Optional.empty());
        given(accountRepository.save(any(Account.class))).willReturn(accountTest);
        Account account = accountService.signup(accountTest);
        assertThat(account.getUsername()).isEqualTo("test@test.com");
        assertThat(account.getPassword()).isEqualTo("0000");
    }

    @Test
    public void signup_exception_test() {
        exceptionRule.expect(ReduplicationUsernameException.class);
        Account account = new Account("test@test.com", "0000");
        given(accountRepository.findByUsername(any())).willReturn(Optional.of(account));
        accountService.signup(account);

    }
}

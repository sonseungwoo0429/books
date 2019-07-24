package com.seungwoo.books.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findByUsername_test() {
        Account account = new Account("test@test.com", "0000");
        entityManager.persist(account);
        entityManager.flush();
        Optional<Account> found = accountRepository.findByUsername(account.getUsername());
        assertThat(found.get().getUsername())
                .isEqualTo(account.getUsername());
    }
}

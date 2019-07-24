package com.seungwoo.books.history;

import com.seungwoo.books.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HistoryRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HistoryRepository historyRepository;

    @Test
    public void findByAccountIdOrderByDatetimeDesc_test() {
        Account account = new Account("test@test.com", "0000");
        account.setDatetime(LocalDateTime.now());
        History history = new History();
        history.setKeyword("나비");
        history.setAccount(account);
        history.setDatetime(LocalDateTime.now());
        entityManager.persist(account);
        entityManager.persist(history);
        Page<History> found = historyRepository.findByAccountIdOrderByDatetimeDesc(account.getId(), PageRequest.of(0, 10));
        assertThat(found.iterator().next().getKeyword()).isEqualTo("나비");
    }
}

package com.seungwoo.books.keyword;

import com.seungwoo.books.history.History;
import com.seungwoo.books.history.HistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class KeywordRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HistoryRepository historyRepository;

    @Test
    public void findHotKeyword_test() {
        History history = new History();
        history.setKeyword("나비");
        history.setDatetime(LocalDateTime.now());
        entityManager.persist(history);
        Pageable limitOneHundred = PageRequest.of(0, 10);
        List<HotKeyword> found = historyRepository.findHotKeyword(limitOneHundred);
        assertThat(found.iterator().next().getKeyword()).isEqualTo("나비");
        assertThat(found.iterator().next().getCount()).isEqualTo(1);
    }
}

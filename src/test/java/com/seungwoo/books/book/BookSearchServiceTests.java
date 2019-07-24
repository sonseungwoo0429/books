package com.seungwoo.books.book;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seungwoo.books.account.Account;
import com.seungwoo.books.config.AuthenticationUser;
import com.seungwoo.books.history.History;
import com.seungwoo.books.history.HistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookSearchServiceTests {

    @Mock
    private BookTemplate bookTemplate;

    @Mock
    private HistoryRepository historyRepository;

    private BookSearchService bookSearchService;

    @Autowired
    private JacksonTester<BookDto.BookData> jsonBooks;

    @Before
    public void setup() {
        this.bookSearchService = new BookSearchService(bookTemplate, historyRepository);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void searchBooks_empty_test() {
        Page<BookDto.Document> documents = bookSearchService.searchBooks(null, new BookDto.SearchData(), null);
        assertThat(documents).isNotNull();
        assertThat(documents.getContent()).isEmpty();
    }

    @Test
    public void searchBooks_test() throws IOException {
        BookDto.BookData book = jsonBooks.readObject("../kbook.json");
        given(bookTemplate.kBookSearch(any(), any())).willReturn(book);
        Account account = new Account("test@test.com", "0000");
        AuthenticationUser user = new AuthenticationUser(account);
        History history = new History();
        history.setAccount(account);
        history.setKeyword("삼국지");
        history.setDatetime(LocalDateTime.now());
        given(historyRepository.save(any(History.class))).willReturn(history);
        BookDto.SearchData searchData = new BookDto.SearchData();
        searchData.setQuery("삼국지");
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDto.Document> documents = bookSearchService.searchBooks(user, searchData, pageable);
        assertThat(documents.iterator().next().getTitle()).contains("용선생");
        verify(bookTemplate).kBookSearch(any(), any());
    }
}

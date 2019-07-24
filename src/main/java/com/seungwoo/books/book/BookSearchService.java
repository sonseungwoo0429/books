package com.seungwoo.books.book;

import com.seungwoo.books.account.Account;
import com.seungwoo.books.config.AuthenticationUser;
import com.seungwoo.books.history.History;
import com.seungwoo.books.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookSearchService {

    private final BookTemplate bookTemplate;

    private final HistoryRepository historyRepository;

    @Transactional
    public Page<BookDto.Document> searchBooks(AuthenticationUser user, BookDto.SearchData searchData, Pageable pageable) {
        if (!StringUtils.hasLength(searchData.getQuery())) {
            return new PageImpl<>(Collections.emptyList());
        }
        BookDto.BookData book = this.bookTemplate.kBookSearch(searchData, pageable);
        Page<BookDto.Document> books = new PageImpl<>(book.getDocuments(), pageable, book.getMeta().getTotalCount());
        History history = new History();
        history.setKeyword(searchData.getQuery());
        history.setDatetime(LocalDateTime.now());
        Account account = new Account();
        account.setId(user.getId());
        history.setAccount(account);
        historyRepository.save(history);
        return books;
    }
}

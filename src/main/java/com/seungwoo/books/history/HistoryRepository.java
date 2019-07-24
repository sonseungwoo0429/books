package com.seungwoo.books.history;

import com.seungwoo.books.keyword.HotKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Page<History> findByAccountIdOrderByDatetimeDesc(Long id, Pageable pageable);

    @Query(value = "SELECT new com.seungwoo.books.keyword.HotKeyword(h.keyword, COUNT(h.id)) FROM History h GROUP BY h.keyword ORDER BY count(h.id) DESC")
    List<HotKeyword> findHotKeyword(Pageable limitOneHundred);
}

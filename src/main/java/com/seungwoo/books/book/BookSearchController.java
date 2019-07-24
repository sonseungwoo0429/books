package com.seungwoo.books.book;

import com.seungwoo.books.config.AuthenticationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService BookSearchService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal AuthenticationUser user, Model model,
                        @Valid @ModelAttribute BookDto.SearchData searchData, BindingResult result, Pageable pageable) {
        if (result.hasErrors()) {
            model.addAttribute("books", new PageImpl<>(Collections.emptyList()));
            return "index";
        }
        Page<BookDto.Document> books = BookSearchService.searchBooks(user, searchData, pageable);
        model.addAttribute("books", books);
        return "index";
    }
}

package com.seungwoo.books.keyword;

import com.seungwoo.books.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hot_keyword")
public class HotKeywordController {

    private final HistoryRepository historyRepository;

    @GetMapping
    public String histories(Model model) {
        Pageable limitOneHundred = PageRequest.of(0, 10);
        model.addAttribute("hotKeywords", historyRepository.findHotKeyword(limitOneHundred));
        return "hot_keyword";
    }
}

package com.seungwoo.books.history;

import com.seungwoo.books.config.AuthenticationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryRepository historyRepository;

    @GetMapping
    public String histories(Model model, @AuthenticationPrincipal AuthenticationUser user, Pageable pageable) {
        model.addAttribute("histories", this.historyRepository.findByAccountIdOrderByDatetimeDesc(user.getId(), pageable));
        return "history";
    }
}

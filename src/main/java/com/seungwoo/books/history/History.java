package com.seungwoo.books.history;

import com.seungwoo.books.account.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@ToString(exclude = "account")
@EqualsAndHashCode(exclude = "account")
@Table(name = "HISTORY")
public class History {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}

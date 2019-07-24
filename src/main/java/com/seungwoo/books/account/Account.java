package com.seungwoo.books.account;

import com.seungwoo.books.history.History;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"histories"})
@EqualsAndHashCode(exclude = {"histories"})
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    public Account() {

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany(mappedBy = "account")
    private List<History> histories;

}

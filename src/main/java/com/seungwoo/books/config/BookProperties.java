package com.seungwoo.books.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "open.api")
@Data
public class BookProperties {

    private String kakaoAppKey;

    private String kakaoRootUri;

    private String naverClientId;

    private String naverClientSecret;

    private String naverRootUri;
}

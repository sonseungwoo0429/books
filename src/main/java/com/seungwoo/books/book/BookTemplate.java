package com.seungwoo.books.book;

import com.seungwoo.books.config.BookProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BookTemplate {

    private final RestTemplate restTemplate;

    private final BookProperties bookProperties;

    @Autowired
    public BookTemplate(RestTemplateBuilder restTemplateBuilder, BookProperties bookProperties) {
        restTemplate = restTemplateBuilder.build();
        this.bookProperties = bookProperties;
    }

    public BookDto.BookData kBookSearch(BookDto.SearchData searchData, Pageable pageable) {
        BookDto.BookData book;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + bookProperties.getKakaoAppKey());
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(bookProperties.getKakaoRootUri())
                    .queryParam("query", searchData.getQuery())
                    .queryParam("sort", searchData.getSort())
                    .queryParam("page", pageable.next().getPageNumber())
                    .queryParam("size", pageable.getPageSize())
                    .queryParam("target", searchData.getTarget())
                    .build();
            ResponseEntity<BookDto.BookData> response = restTemplate.exchange(
                    builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), new ParameterizedTypeReference<BookDto.BookData>() {
                    });
            book = response.getBody();
        } catch (RestClientException e) {
            book = nBookSearch(searchData, pageable);
            BookDto.Meta meta = new BookDto.Meta();
            meta.setTotalCount(book.getTotalCount());
            meta.setPageableCount(book.getPageableCount());
            book.setMeta(meta);
        }
        return book;
    }

    public BookDto.BookData nBookSearch(BookDto.SearchData searchData, Pageable pageable) {
        String sort = searchData.getSort().getValue().equals("accuracy") ? BookSort.SIM.getValue() : BookSort.DATE.getValue();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", bookProperties.getNaverClientId());
        headers.set("X-Naver-Client-Secret", bookProperties.getNaverClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(bookProperties.getNaverRootUri())
                .queryParam("query", searchData.getQuery())
                .queryParam("sort", sort)
                .queryParam("start", pageable.next().getPageNumber())
                .queryParam("display", pageable.getPageSize())
                .build();
        ResponseEntity<BookDto.BookData> response = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), new ParameterizedTypeReference<BookDto.BookData>() {
                });
        BookDto.BookData nBook = response.getBody();
        return nBook;
    }
}

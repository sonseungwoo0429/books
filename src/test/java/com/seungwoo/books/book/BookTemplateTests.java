package com.seungwoo.books.book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(BookTemplate.class)
public class BookTemplateTests {

    @Autowired
    private BookTemplate bookTemplate;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void kBooks_Test() {
        this.server.expect(
                requestTo("https://dapi.kakao.com/v3/search/book?query&sort&page=1&size=10&target"))
                .andRespond(withSuccess(
                        new ClassPathResource("../kbook.json", getClass()),
                        MediaType.APPLICATION_JSON));
        BookDto.SearchData searchData = new BookDto.SearchData();
        Pageable pageable = PageRequest.of(0, 10);
        BookDto.BookData book = bookTemplate.kBookSearch(searchData, pageable);
        List<BookDto.Document> documents = book.getDocuments();
        Iterator<BookDto.Document> iterator = documents.iterator();
        BookDto.Document document = iterator.next();
        assertThat(document.getTitle()).isEqualTo("용선생 만화 한국사. 3: 삼국 시대(2)");
        this.server.verify();
    }

    @Test
    public void nBooks_Test() {
        this.server.expect(
                requestTo("https://openapi.naver.com/v1/search/book.json?query&sort=sim&start=1&display=10"))
                .andRespond(withSuccess(
                        new ClassPathResource("../nbook.json", getClass()),
                        MediaType.APPLICATION_JSON));
        BookDto.SearchData searchData = new BookDto.SearchData();
        searchData.setSort(BookSort.ACCURACY);
        Pageable pageable = PageRequest.of(0, 10);
        BookDto.BookData book = bookTemplate.nBookSearch(searchData, pageable);
        List<BookDto.Document> documents = book.getDocuments();
        Iterator<BookDto.Document> iterator = documents.iterator();
        BookDto.Document document = iterator.next();
        assertThat(document.getTitle()).isEqualTo("스틸 미");
        this.server.verify();
    }
}

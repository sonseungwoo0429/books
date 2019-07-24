package com.seungwoo.books.book;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.seungwoo.books.utils.ArrayUtils.commaSeparatedListToString;
import static com.seungwoo.books.utils.DateUtils.dateFormatter;
import static com.seungwoo.books.utils.StringUtils.removeTag;

public class BookDto {

    @Data
    public static class BookData {
        @JsonProperty("total")
        private int totalCount;
        @JsonProperty("display")
        private int pageableCount;
        private Meta meta;
        @JsonAlias({"documents", "items"})
        private List<Document> documents;
    }

    @Data
    public static class Meta {
        @JsonProperty("is_end")
        private boolean isEnd;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("total_count")
        private int totalCount;
    }

    @Data
    public static class SearchData {
        @NotEmpty(message = "검색어는 필수 입니다.")
        private String query;
        private Target target;
        private BookSort sort;
    }

    @Data
    public static class Document {
        private String title;
        @JsonAlias({"description", "contents"})
        private String contents;
        @JsonAlias({"link", "url"})
        private String url;
        private String isbn;
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonAlias({"datetime"})
        @JsonDeserialize(using = CustomLocalDatetimeDeserializer.class)
        private LocalDateTime datetime;
        private String pubdate;
        private List<String> authors;
        private String author;
        private String publisher;
        private List<String> translators;
        private Integer price;
        @JsonAlias({"sale_price", "discount"})
        private Integer salePrice;
        @JsonAlias({"image", "thumbnail"})
        private String thumbnail;
        private String status;


        public String getTitle() {
            return removeTag(title);
        }

        public String getContents() {
            return removeTag(contents);
        }

        public LocalDate getDateToString() {
            return dateFormatter(pubdate);
        }

        public String getAuthorsToString() {
            return authors != null ? commaSeparatedListToString(authors) : removeTag(author);
        }

        public String getTranslatorsToString() {
            return commaSeparatedListToString(Optional.ofNullable(translators).orElse(new ArrayList<>()));
        }
    }
}

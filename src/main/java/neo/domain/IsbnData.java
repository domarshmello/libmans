package neo.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class IsbnData {
    private int status;
    private String msg;
    private Result result;

    @Data
    class Result {
        private String title;
        private String subtitle;
        private String pic;
        private String author;
        private String summary;
        private String publisher;
        private String pubplace;
        private String pubdate;
        private int page;
        private BigDecimal price;
        private String binding;
        private String isbn;
        private String isbn10;
        private String keyword;
        private String edition;
        private String impression;
        private String language;
        private String format;
    }
}

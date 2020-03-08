package neo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookRequest {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "AUTHOR")
    private String author;
}

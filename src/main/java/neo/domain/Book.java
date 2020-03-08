package neo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private BookCategory category;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "REMAINING_QUANTITY")
    private int remainingQuantity;
}

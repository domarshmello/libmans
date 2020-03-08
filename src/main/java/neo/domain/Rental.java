package neo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rental {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Column(name = "RENT_DATE")
    private String rentDate;

    @Column(name = "RETURN_DATE")
    private String returnDate;

    @Column(name = "STATUS")
    private String status;
}

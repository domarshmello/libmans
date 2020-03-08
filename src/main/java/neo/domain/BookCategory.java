package neo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookCategory {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;
}

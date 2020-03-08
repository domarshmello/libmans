package neo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "PASSWORD")
    // TODO Should Only Allow Setter
    private String password;

    @Column(name = "ROLE")
    private String role;
}

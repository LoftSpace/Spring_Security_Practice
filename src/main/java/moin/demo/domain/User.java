package moin.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "user_Id", nullable = false)
    private String userId;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "idValue", nullable = false)
    private String idValue;

    @Column(name = "username", nullable = false)
    private String username;

    protected User() {}
}

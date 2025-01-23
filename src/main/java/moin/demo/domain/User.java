package moin.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @Column(name = "user_Id", nullable = false)
    private String userId;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    private String idType;

    @Column(name = "idValue", nullable = false)
    private String idValue;

    @Column(name = "username", nullable = false)
    private String username;

    protected User() {}

    public void updateToEncodedPassword(String encryptedPassword){
        this.password = encryptedPassword;
    }
    public void updateToEncodedIdType(String encryptedIdType){
        this.idType = encryptedIdType;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

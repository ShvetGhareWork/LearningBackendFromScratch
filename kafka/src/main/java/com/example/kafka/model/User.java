package com.example.kafka.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, comment = "Name of the user")

    private String name;

    @Column(name = "email", nullable = false, unique = true, comment = "Email should have @ and a domain name")
    private String email;

    @Column(name = "password", nullable = false, length = 60, comment = "Password must be at least 8 characters long")
    private String password;


    // --- UserDetails Overrides ---
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For a simple app, we can assign a default role. 
        // In a complex app, this would map to a 'roles' table in your DB.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // Spring Security uses 'username' by default, but we are using email for auth.
        return email; 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; 
    }

}

package com.ssg.echodairy.sercurity;

import com.ssg.echodairy.domain.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

//    private final Client client;
//
//    public CustomUserDetails(Client client) {
//        this.client = client;
//    }
//
//    public Client getClient() {
//        return client;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(
//                new SimpleGrantedAuthority("ROLE_" + client.getRole())
//        );
//    }
//
//    @Override
//    public String getUsername() {
//        return client.getLoginId();
//    }
//
//    @Override
//    public String getPassword() {
//        return client.getPassword();
//    }
//
//    @Override public boolean isAccountNonExpired() { return true; }
//    @Override public boolean isAccountNonLocked() { return true; }
//    @Override public boolean isCredentialsNonExpired() { return true; }
//    @Override public boolean isEnabled() { return true; }
private final Client client;

    public CustomUserDetails(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Long getUserId() {
        return client.getUserId();   // ← Client의 PK 컬럼
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 🔥 ROLE_ 접두사 반드시 보장
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + client.getRole())
        );
    }

    @Override
    public String getUsername() {
        return client.getLoginId();
    }

    @Override
    public String getPassword() {
        return client.getPassword();
    }

    // 🔥 전부 true 보장 (중요)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

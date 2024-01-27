package com.example.solidconnection.custom.userdetails;

import com.example.solidconnection.entity.SiteUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final SiteUser siteUser;

    public CustomUserDetails(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public String getEmail(){
        return siteUser.getEmail();
    }

    @Override
    public String getUsername() {
        return siteUser.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

package com.hys.login.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.MoreObjects;

public class LoginUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userCode;

    private String userName;

    private String password;

    private boolean enabled;

    private List<SimpleGrantedAuthority> authorities;

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof LoginUserDetails) {
            return userCode.equals(((LoginUserDetails) rhs).userCode);
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUserCode() {
        return userCode;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public int hashCode() {
        return userCode == null ? 0 : userCode.hashCode();
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
        return enabled;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("userCode", userCode).add("userName", userName).add("password", password).add("enabled", enabled)
            .toString();
    }
}

package com.mrsun.bootserver.security;

import com.mrsun.bootserver.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author sunxiaogang
 */
public class CustomerUserDetails implements UserDetails {

    private User currentUser;

    private List<String> roleCodes;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (roleCodes != null && !roleCodes.isEmpty()) {
            roleCodes.forEach(permission -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
                grantedAuthorities.add(simpleGrantedAuthority);
            });
            return grantedAuthorities;
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.currentUser.getUsername();
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

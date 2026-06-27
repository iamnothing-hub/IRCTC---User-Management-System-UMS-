package com.irctc.userservice.security.principal;

import com.irctc.userservice.entity.User;
import com.irctc.userservice.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    private AccountStatus accountStatus;


    public static CustomUserDetails fromUser(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.getAccountStatus() == AccountStatus.ACTIVE)
                .authorities(user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(
                                role.getRoleName()
                        )).toList()
                ).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
//        return enabled;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//        return enabled;
        return accountStatus == AccountStatus.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return enabled;
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//        return enabled;
        return accountStatus == AccountStatus.ACTIVE;
    }


}

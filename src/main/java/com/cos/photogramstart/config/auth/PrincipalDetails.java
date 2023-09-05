package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails  implements UserDetails {

    private User user;

    public PrincipalDetails(User user){
        this.user=user;
    }

    // 권한 : 권한이 한개가 아닐수 있다.(3개 이상의 권한)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //권한을 가져오는 메서드

        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(()->{return user.getRole();});

        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되었는지 ?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겻니 ?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호가 1년이 지낫는데 변경안한거 아니니 ?
        return true;
    }

    @Override
    public boolean isEnabled() { //활성화 되었는지 ?
        return true;
    }

    // 하나라도 false 면 로그인이 안된다.
}

package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {
    //페이스북서버에서 응답으로 온 유저정보에 대한 로직을 짠다.

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder; -> SecurityConfig 가 늦게 등록되서 못찾는다

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest); // 유저 정보가 담긴다.
        Map<String, Object> userMap = oauth2User.getAttributes();

        String username = "facebook_" + (String) userMap.get("id");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) userMap.get("email");
        String name = (String) userMap.get("name");

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            User user = User.builder()
                    .username(username) //username 은 강제로 만들어준다
                    .password(password) // 로그인은 페이스북을 통해서 하므로 패스워드는 식별하기만 하면 된다
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();
            return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());
        }else{
            return new PrincipalDetails(userEntity,oauth2User.getAttributes());
        }

    }
}

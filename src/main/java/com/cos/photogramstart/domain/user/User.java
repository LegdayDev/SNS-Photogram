package com.cos.photogramstart.domain.user;

// JPA - Java Persistence API(자바로 데이터를 영구적으로 저장할 수 있는 API 를 제공)

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100,unique = true,nullable = false) //유니크 제약조건 설정
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; // 웹사이트
    private String bio; // 자기소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role; // 권한

    //양방향 매핑
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<Image> images;

    @OneToMany(mappedBy = "")
    private List<Subscribe> subscribes;

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }


}

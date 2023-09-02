package com.cos.photogramstart.domain.user;

// JPA - Java Persistence API(자바로 데이터를 영구적으로 저장할 수 있는 API 를 제공)

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true) //유니크 제약조건 설정
    private String username;
    private String password;

    private String name;
    private String website; // 웹사이트
    private String bio; // 자기소개
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role; // 권한

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}

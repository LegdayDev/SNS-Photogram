package com.cos.photogramstart.web.dto.subscribe;

import lombok.*;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SubscribeDto {
    private int id;
    private String username;
    private String profileImageUrl;
    private BigInteger subscribeState;
    private BigInteger equalUserState;
}

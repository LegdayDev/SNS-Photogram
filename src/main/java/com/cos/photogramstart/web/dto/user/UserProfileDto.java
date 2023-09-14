package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserProfileDto {
    private boolean pageOwnerState;
    private User user;
    private int imageCount;
    private int subscribeCount;
    private boolean subscribeState;
}

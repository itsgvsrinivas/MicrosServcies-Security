package com.gv.user_service.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String login;
    private long id;
    private String avatar_url;
    private String url;
    private String followers_url;
    private String following_url;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private String starred_url;
}

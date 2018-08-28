package com.execom.pomodoro.domain;

import lombok.Data;

@Data
public class UserInfo {

    private String id;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String link;
    private String email;
    private String locale;
    private String verified_email;

    public UserInfo() {
    }

    public UserInfo(String id, String name, String given_name, String family_name, String picture, String link,
            String email, String locale, String verified_email) {
        this.id = id;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.picture = picture;
        this.link = link;
        this.email = email;
        this.locale = locale;
        this.verified_email = verified_email;
    }
}
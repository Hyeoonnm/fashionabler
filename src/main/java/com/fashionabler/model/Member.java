package com.fashionabler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String memberId;
    private String memberPasswords;
    private String memberEmail;
    private String memberPhone;

}

package com.metalbook.cms.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtResponse {
    private final String token;
    public JwtResponse(String token){
        this.token = token;
    }
}

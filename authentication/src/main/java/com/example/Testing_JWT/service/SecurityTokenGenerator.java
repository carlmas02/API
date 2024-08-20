package com.example.Testing_JWT.service;

import com.example.Testing_JWT.domain.UserEntity;

import java.util.Map;

/* Ig, You'll be able to figure out about the other files in repository. Lemme know if there are any suggestions */
/* You can even try commenting like these wherever you think i can optimise or can do it in a better way */

public interface SecurityTokenGenerator {
    public Map<String,String>  generateToken(UserEntity userEntity);
}

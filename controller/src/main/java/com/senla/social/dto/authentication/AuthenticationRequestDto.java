package com.senla.social.dto.authentication;

import lombok.Data;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;
}

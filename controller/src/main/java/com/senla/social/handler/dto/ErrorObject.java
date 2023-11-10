package com.senla.social.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject {
    private HttpStatus status;
    private String message;
    private long timestamp;
}

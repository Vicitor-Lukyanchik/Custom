package com.senla.social.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
@AllArgsConstructor
public class ErrorDetails {
    private HttpStatus status;
    private long timestamp;
    private List<String> errors;
}

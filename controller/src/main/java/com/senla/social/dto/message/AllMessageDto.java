package com.senla.social.dto.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class AllMessageDto {

    private String text;

    private String name;

    private String dateTime;
}

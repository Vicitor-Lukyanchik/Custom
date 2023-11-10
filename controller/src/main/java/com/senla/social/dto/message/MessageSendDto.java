package com.senla.social.dto.message;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class MessageSendDto {

    private Long receiverId;

    @Size(max = 255, message = "Message should be less than 255")
    @NotBlank(message = "Message text can't be empty")
    private String text;
}

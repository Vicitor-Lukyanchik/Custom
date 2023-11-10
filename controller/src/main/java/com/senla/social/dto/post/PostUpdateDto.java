package com.senla.social.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class PostUpdateDto {

    private Long id;

    @Size(max = 80, message = "Post title should be less than 80")
    @NotBlank(message = "Post title can't be empty")
    private String title;

    @Size(max = 255, message = "Post text should be less than 255")
    @NotBlank(message = "Post text can't be empty")
    private String text;
}

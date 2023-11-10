package com.senla.social.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class InterestDto {
    @Size(min = 2, max = 50, message = "Interest name should be more than 2 and less than 50")
    @NotBlank(message = "Interest name can't be empty")
    private String name;
}

package com.senla.social.dto;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class GroupDto {

    @Size(max = 50, message = "Group name should be less than 50")
    private String name;
    @Size(max = 50, message = "Interest name should be less than 50")
    private String interest;
}

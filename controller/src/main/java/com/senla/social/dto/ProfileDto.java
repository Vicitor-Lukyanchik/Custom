package com.senla.social.dto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class ProfileDto {

    @Size(min = 2, max = 50, message = "Firstname should be more than 2 and less than 50")
    @NotBlank(message = "Firstname can't be empty")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in firstname should be uppercase")
    private String firstname;

    @Size(min = 2, max = 50, message = "Lastname should be more than 2 and less than 50")
    @NotBlank(message = "Lastname can't be empty")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in lastname should be uppercase")
    private String lastname;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 6, message = "Age should be more than 6")
    @Max(value = 120, message = "Age should less than 120")
    private Integer age;

    @Size(max=15, message = "Sex should be more than 15")
    @Pattern(regexp = "FEMALE|MALE|not specified")
    private String sex;

    @Size(min = 2, max = 50, message = "Town name should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in town should be uppercase")
    private String town;

    @Size(min = 7, max = 15, message = "Phone should be more than 7 and less than 15")
    private String phone;

    @Size(max = 50, message = "Family status should be more than 0 and less than 50")
    private String familyStatus;
}

package com.example.custom.dto;

import com.example.custom.entity.Role;
import com.example.custom.entity.Status;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
public class UserDto {

    private Long id;

    @Size(min = 6, max = 50, message = "Username should be more then 4 and less than 50")
    private String username;

    @Size(min = 8, max = 60, message = "Password should be more then 8 and less than 60")
    private String password;

    private List<Role> roles;

    private Status status;

    private String message;
}

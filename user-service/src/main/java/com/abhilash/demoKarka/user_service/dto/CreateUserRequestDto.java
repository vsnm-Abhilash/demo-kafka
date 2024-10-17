package com.abhilash.demoKarka.user_service.dto;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    private Long id;
    private String email;
    private String name;
}

package com.api.parkingcontrol.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class User {

    @NotBlank
    private String userName;

    @NotBlank
    private Integer userAge;

    @NotBlank
    private String userGender;

    @NotBlank
    private String userProfession;


}

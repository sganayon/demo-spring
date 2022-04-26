package com.example.demo.dto;

import com.example.demo.constants.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {
    private Long id;
    private String name;
    private String surname;
    private Status status;
    private TeamDto team;
}

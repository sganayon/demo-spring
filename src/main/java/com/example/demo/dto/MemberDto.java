package com.example.demo.dto;

import com.example.demo.constants.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {
    private Long id;
    @NotNull @NotBlank
    @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]*$", message = "Only letters, quote and dash are allowed")
    private String name;
    @NotNull @NotBlank @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]*$", message = "Only letters, quote and dash are allowed")
    private String surname;
    private Status status;
    @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9'_ -]*$", message = "Only letter, number, quote, dash, underscore and space are allowed")
    private String teamName;
}

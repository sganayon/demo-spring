package com.example.demo.entity;

import com.example.demo.constants.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "member")
@EqualsAndHashCode(of = "id")
public class Member {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotBlank @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]*$", message = "Only letters, quote and dash are allowed")
    @Column(name="name")
    private String name;

    @NotNull @NotBlank @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]*$", message = "Only letters, quote and dash are allowed")
    @Column(name="surname")
    private String surname;

    @NotNull
    @Column(name="status")
    private Status status;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "team_name")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
}

package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "team")
public class Team {
    @Id
    @Column(name="id")
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]$", message = "Only letters, quote and dash are allowed")
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch= FetchType.LAZY)
    @JsonManagedReference
    private List<Member> members;
}

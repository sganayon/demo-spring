package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Table(name = "team")
@EqualsAndHashCode(of = "name")
public class Team {
    @Id
    @NotNull @NotBlank
    @Size(max = 50, message = "The name should have at most 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9'_ -]*$", message = "Only letter, number, quote, dash, underscore and space are allowed")
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "team", fetch= FetchType.EAGER)
    @JsonManagedReference
    private Set<Member> members;
}

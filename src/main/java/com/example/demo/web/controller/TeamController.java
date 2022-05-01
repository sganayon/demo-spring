package com.example.demo.web.controller;

import com.example.demo.dto.TeamDto;
import com.example.demo.dto.TeamPostDto;
import com.example.demo.entity.Team;
import com.example.demo.facade.TeamFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "TeamController", description = "API that handle CRUD operations on teams")
public class TeamController {
    private final TeamFacade teamFacade;

    // TODO : trouver comment intégrer specification-arg-resolver dans spring-doc
    @GetMapping
    @Operation(description = "Get all teams after applying the filters")
    public List<TeamDto> getTeams(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "sortedElement", required = false) String sortedElement,
            @RequestParam(value = "sortedAsc", required = false) Boolean sortedAsc,
            @Join(path="members", alias="members")
            @And({
                @Spec(path = "name", params = "label", spec = LikeIgnoreCase.class),
                @Spec(path = "team.members.name", params = "memberName", spec = LikeIgnoreCase.class)
            }) Specification<Team> specification){
        return teamFacade.getTeams(pageNumber, pageSize, sortedElement, sortedAsc, specification);
    }

    @GetMapping("/{teamName}")
    @Operation(description = "Get one team")
    public ResponseEntity<TeamDto> getTeam(@PathVariable String teamName){
        TeamDto teamDto = teamFacade.getOneById(teamName);
        if(teamDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teamDto, HttpStatus.OK);
    }

    @DeleteMapping("/{teamName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete one team")
    public void deleteTeam(@PathVariable String teamName){
        teamFacade.deleteOneById(teamName);
    }

    @PostMapping
    @Operation(description = "Add one team")
    public String addTeam(@Valid @RequestBody TeamPostDto teamDto){
        return teamFacade.save(teamDto);
    }

    @PutMapping("/{teamName}")
    @Operation(description = "Update one team")
    public TeamDto updateTeam(@Valid @RequestBody TeamPostDto teamDto, @PathVariable String teamName){
        return teamFacade.update(teamDto, teamName);
    }
}

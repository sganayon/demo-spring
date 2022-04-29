package com.example.demo.facade;

import com.example.demo.dto.TeamDto;
import com.example.demo.dto.TeamPostDto;
import com.example.demo.entity.Team;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TeamFacade {
    List<TeamDto> getTeams(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<Team> specification);
    TeamDto getOneById(String teamName);
    void deleteOneById(String teamName);
    String save(TeamPostDto teamDto);
}

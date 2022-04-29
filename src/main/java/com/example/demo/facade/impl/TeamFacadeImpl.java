package com.example.demo.facade.impl;

import com.example.demo.dto.TeamDto;
import com.example.demo.dto.TeamPostDto;
import com.example.demo.entity.Team;
import com.example.demo.facade.TeamFacade;
import com.example.demo.mapper.TeamMapper;
import com.example.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Facade pour le controller TeamController
 * Mapping DTO - Entité
 * Fait le lien entre les services et un controller pour adapté le retours des services aux besoin des API
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamFacadeImpl implements TeamFacade {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @Override
    public List<TeamDto> getTeams(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<Team> specification) {
        return teamService.getEntities(pageNumber, pageSize, sortedElement, sortedAsc, specification)
                .map(teamMapper::toDto)
                .getContent();
    }

    @Override
    public TeamDto getOneById(String teamName) {
        Team team = teamService.findOneById(teamName);
        if(team == null ) {
            return null;
        }
        return teamMapper.toDto(team);
    }

    @Override
    public void deleteOneById(String teamName) {
        teamService.deleteOneById(teamName);
    }

    @Override
    public String save(TeamPostDto teamDto) {
        return teamService.save(teamMapper.toEntity(teamDto)).getName();
    }
}

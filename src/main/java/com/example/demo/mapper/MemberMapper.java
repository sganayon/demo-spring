package com.example.demo.mapper;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Team;
import com.example.demo.service.TeamService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper de membre compatible avec Spring (componentModel)
 */
@Mapper(componentModel = "spring")
public abstract class MemberMapper {
    @Autowired protected TeamService teamService;

    @Mapping(target = "teamName", source = "team.name")
    public abstract MemberDto toDto(Member member);

    @Mapping(target = "team", source = "teamName")
    public abstract Member toEntity(MemberDto memberDto);

    // Ici l'annotation @Name sur le CrudService ne marche pas, donc on fait le mapping à la main
    protected Team findTeamById(String teamName){
        return teamService.findOneById(teamName);
    }
}


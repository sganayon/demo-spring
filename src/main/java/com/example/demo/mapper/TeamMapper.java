package com.example.demo.mapper;

import com.example.demo.dto.TeamDto;
import com.example.demo.dto.TeamPostDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Team;
import com.example.demo.service.MemberService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapper de team compatible avec Spring (componentModel)
 */
@Mapper(componentModel = "spring")
public abstract class TeamMapper {
    // Les classes sont utilisées dans l'implémentation ne pas les supprimer !
    @Autowired protected MemberMapper memberMapper;
    @Autowired protected MemberService memberService;

    public abstract TeamDto toDto(Team team);
    @Mapping(target = "members", source = "memberIds")
    public abstract Team toEntity(TeamPostDto teamDto);

    /**
     * Mapping à la main des membres car ils sont dans un set
     */
    protected Set<Member> membersDtoToEntities(Set<Long> memberIds){
        return new HashSet<>(memberService.findAllByIds(memberIds));
    }
}

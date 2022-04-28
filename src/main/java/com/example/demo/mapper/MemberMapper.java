package com.example.demo.mapper;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.TeamRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TeamRepository.class})
public interface MemberMapper {

    @Mapping(target = "teamName", source = "member.team.name")
    MemberDto toDto(Member member);

    @Mapping(target = "team", source = "teamName")
    Member toEntity(MemberDto memberDto, @MappingTarget Member member, @Context TeamRepository teamRepository);
}


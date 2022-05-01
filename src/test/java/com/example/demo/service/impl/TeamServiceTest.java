package com.example.demo.service.impl;

import com.example.demo.constants.Status;
import com.example.demo.entity.Member;
import com.example.demo.entity.Team;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @InjectMocks
    TeamServiceImpl teamService;
    @Mock TeamRepository teamRepository;
    @Mock MemberRepository memberRepository;

    @Test
    void saveTest(){
        Member member = generateMember();
        Team team = new Team();
        team.setName("team");
        team.setMembers(Collections.singleton(member));

        when(teamRepository.save(any())).then(answer -> answer.getArgument(0));
        when(memberRepository.saveAll(any())).then(answer -> new ArrayList<>(answer.getArgument(0)));

        teamService.save(team);

        verify(teamRepository).save(team);
        member.setTeam(team);
        verify(memberRepository).saveAll(Collections.singleton(member));
    }

    Member generateMember(){
        Member member = new Member();
        member.setId(1L);
        member.setName("POULAT");
        member.setSurname("AXEL");
        member.setStatus(Status.BACKEND);
        return member;
    }
}

package com.example.demo.web.controller;

import com.example.demo.dto.TeamPostDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Team;
import com.example.demo.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired TeamRepository teamRepository;
    @Autowired ObjectMapper objectMapper;
    @Autowired JdbcTemplate jdbcTemplate;

    static final String TEAM_URL = "/api/team";

    // TODO : trouver pourquoi ce n'est pas executé ...
    @BeforeEach
    void cleanDataBase() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "member", "team");
    }

    @Test
    @Sql("classpath:sql/insert-user.sql")
    void addTeamWithUserTest() throws Exception {
        TeamPostDto content = new TeamPostDto();
        content.setName("TEAM 1");
        content.setMemberIds(Collections.singleton(1L)); // Voir insert-user.sql

        this.mockMvc.perform(post(TEAM_URL)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(content)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        List<Team> teamList = teamRepository.findAll();
        assertEquals(1, teamList.size(), "Une seule team aurait du être créée");
        Set<Member> members = teamList.get(0).getMembers();
        assertNotNull(members, "la team créée aurait du avoir un membre");
        assertEquals(1, members.size(), "la team créée aurait du n'avoir qu'un membre");
        Member member = members.iterator().next();
        assertEquals(1, member.getId(), "La team créée aurait du avoir le bon utilisateur");
        assertNotNull(member.getTeam(), "Le lien membre - team n'est pas correctement fait");
        assertEquals(content.getName(), member.getTeam().getName(), "Le membre n'est pas affecté à la bonne team");
    }

    String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

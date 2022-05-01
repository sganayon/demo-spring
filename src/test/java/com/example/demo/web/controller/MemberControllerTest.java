package com.example.demo.web.controller;

import com.example.demo.configuration.web.WebMvcConfig;
import com.example.demo.constants.Status;
import com.example.demo.dto.MemberDto;
import com.example.demo.dto.MemberPostDto;
import com.example.demo.facade.MemberFacade;
import com.example.demo.web.controller.MemberController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'intégration partiel (on ne test que la couche HTTP : dé/sérialisation, sécurité, validation, code de retour, gestion des exceptions)
 */
@WebMvcTest(value = MemberController.class)
@ContextConfiguration(classes = {MemberController.class, WebMvcConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean MemberFacade memberFacade;
    @Autowired ObjectMapper objectMapper;

    static final String URL_MEMBER = "/api/member";

    @Test
    void getMemberTest() throws Exception {
        when(memberFacade.getOneById(1)).thenReturn(new MemberDto());
        this.mockMvc.perform(get(URL_MEMBER+"/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getMemberNotFoundTest() throws Exception {
        when(memberFacade.getOneById(1)).thenReturn(null);
        this.mockMvc.perform(get(URL_MEMBER+"/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getMemberWithMalFormedNameTest() throws Exception {
        MemberPostDto memberDto = new MemberPostDto();
        memberDto.setName("POULAT 1");
        memberDto.setSurname("axel&");
        memberDto.setStatus(Status.BACKEND);

        this.mockMvc.perform(post(URL_MEMBER)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(memberDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(memberFacade, never()).save(any());
    }

    String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
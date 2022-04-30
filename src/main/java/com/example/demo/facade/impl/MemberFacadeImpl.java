package com.example.demo.facade.impl;

import com.example.demo.dto.MemberDto;
import com.example.demo.dto.MemberPostDto;
import com.example.demo.entity.Member;
import com.example.demo.facade.MemberFacade;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.constants.LogMessages.INF_NO_ENTITY_FOUND;

/**
 * Facade pour le controller MemberController
 * Mapping DTO - Entité
 * Fait le lien entre les services et un controller pour adapté le retours des services aux besoin des API
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberFacadeImpl implements MemberFacade {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @Override
    public List<MemberDto> getMembers(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<Member> specification) {
        return memberService.getEntities(pageNumber, pageSize, sortedElement, sortedAsc, specification)
                .map(memberMapper::toDto)
                .getContent();
    }

    @Override
    public MemberDto getOneById(long id) {
        Member member = memberService.findOneById(id);
        if(member == null){
            log.info(INF_NO_ENTITY_FOUND, "Le membre", id);
            return null;
        }
        return memberMapper.toDto(member);
    }

    @Override
    public void deleteOneById(long id) {
        memberService.deleteOneById(id);
    }

    @Override
    public long save(MemberPostDto memberDto) {
        return memberService.save(memberMapper.toEntity(memberDto)).getId();
    }

    @Override
    public MemberDto update(MemberPostDto memberDto, long id) {
        Member member = memberMapper.toEntity(memberDto);
        member.setId(id);
        return memberMapper.toDto(memberService.save(member));
    }
}

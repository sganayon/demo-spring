package com.example.demo.facade.impl;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import com.example.demo.facade.MemberFacade;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberFacadeImpl implements MemberFacade {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final TeamRepository teamRepository;

    @Override
    public List<MemberDto> getMembers(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<Member> specification) {
        return memberService.getMembers(pageNumber, pageSize, sortedElement, sortedAsc, specification)
                .map(memberMapper::toDto)
                .getContent();
    }

    @Override
    public MemberDto getOneById(long id) {
        return memberMapper.toDto(memberService.findOneById(id));
    }

    @Override
    public void deleteOneById(long id) {
        memberService.deleteOneById(id);
    }

    @Override
    public long save(MemberDto memberDto) {
        return memberService.save(memberMapper.toEntity(memberDto, new Member(), teamRepository)).getId();
    }
}

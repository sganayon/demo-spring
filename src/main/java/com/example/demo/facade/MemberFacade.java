package com.example.demo.facade;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MemberFacade {
    List<MemberDto> getMembers(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<Member> specification);
    MemberDto getOneById(long id);
    void deleteOneById(long id);
    long save(MemberDto memberDto);
}

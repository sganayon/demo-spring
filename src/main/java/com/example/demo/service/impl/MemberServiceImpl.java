package com.example.demo.service.impl;

import com.example.demo.dto.DefaultPagination;
import com.example.demo.entity.Member;
import com.example.demo.repository.CrudRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberServiceImpl extends CrudBaseServiceImpl<Member, Long> implements MemberService {
    private final MemberRepository memberRepository;

    public List<Member> findAllByIds(Collection<Long> ids){
        return memberRepository.findAllById(ids);
    }

    @Override
    protected CrudRepository<Member, Long> getRepository() {
        return memberRepository;
    }

    @Override
    protected DefaultPagination getDefaultPagination() {
        return new DefaultPagination().withSortElement("name");
    }
}

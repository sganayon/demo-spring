package com.example.demo.service.impl;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl extends CrudBaseServiceImpl<Member, Long, MemberRepository> implements MemberService {

    @Autowired
    public MemberServiceImpl(MemberRepository repository) {
        super(repository, "name");
    }

    public List<Member> findAllByIds(Collection<Long> ids){
        return repository.findAllById(ids);
    }
}

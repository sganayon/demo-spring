package com.example.demo.service.impl;

import com.example.demo.entity.Member;
import com.example.demo.repository.CrudRepository;
import com.example.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberServiceImpl extends CrudBaseServiceImpl<Member, Long> implements MemberService {

    @Autowired
    public MemberServiceImpl(CrudRepository<Member, Long> repository) {
        super(repository, "name");
    }
}

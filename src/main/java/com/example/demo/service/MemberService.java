package com.example.demo.service;

import com.example.demo.entity.Member;

import java.util.Collection;
import java.util.List;

public interface MemberService extends CrudBaseService<Member, Long> {
    List<Member> findAllByIds(Collection<Long> ids);
}

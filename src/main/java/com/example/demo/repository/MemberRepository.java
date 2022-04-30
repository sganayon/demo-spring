package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findAllMembersByTeamName(String teamName);
}

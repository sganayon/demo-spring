package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
}

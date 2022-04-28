package com.example.demo.repository;

import com.example.demo.entity.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
}

package com.example.demo.service.impl;

import com.example.demo.entity.Team;
import com.example.demo.repository.CrudRepository;
import com.example.demo.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamServiceImpl extends CrudBaseServiceImpl<Team, String> implements TeamService {

    @Autowired
    public TeamServiceImpl(CrudRepository<Team, String> repository) {
        super(repository, "name");
    }
}

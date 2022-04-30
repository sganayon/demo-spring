package com.example.demo.service;

import com.example.demo.entity.Team;

public interface TeamService extends CrudBaseService<Team, String> {
    public Team update(Team team, String id);
}

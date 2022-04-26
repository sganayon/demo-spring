package com.example.demo.service.impl;

import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
}

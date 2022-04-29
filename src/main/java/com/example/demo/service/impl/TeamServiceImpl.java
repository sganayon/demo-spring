package com.example.demo.service.impl;

import com.example.demo.entity.Member;
import com.example.demo.entity.Team;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class TeamServiceImpl extends CrudBaseServiceImpl<Team, String, TeamRepository> implements TeamService {
    private final MemberRepository memberRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository repository, MemberRepository memberRepository) {
        super(repository, "name");
        this.memberRepository = memberRepository;
    }

    /**
     * La relation 'team' étant porté par les 'member' il faut un peut plus de logique dans ce service
     * @param team la team à sauvegarder avec ses membres
     * @return la team pleinement sauvegardé
     */
    @Override
    public Team save(Team team) {
        Set<Member> members = team.getMembers();
        // On sauvegarde la team (si jamais elle n'existe pas on ne pourra pas save les membres)
        Team savedTeam = super.save(team);
        // On affecte les membres à la team et on les sauvegardes
        members.forEach(member -> member.setTeam(savedTeam));
        members = new HashSet<>(memberRepository.saveAll(members));
        // On affecte les membres à la team et on la retourne (évite un find... pour avoir la liste des membres à jour)
        savedTeam.setMembers(members);
        return savedTeam;
    }

    /**
     * Supprime une team sans supprimer les membres qui la compose
     * @param id de la team à supprimer
     */
    @Override
    public void deleteOneById(String id) {
        Team team = findOneById(id);
        if(team == null){
            return;
        }

        Set<Member> members = team.getMembers();
        members.forEach(member -> member.setTeam(null));
        memberRepository.saveAll(members);
        super.deleteOneById(id);
    }
}
